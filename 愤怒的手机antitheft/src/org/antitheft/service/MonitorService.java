package org.antitheft.service;
 

import org.antitheft.Const;


import util.AccelerometerUtil;
import util.LightUtil;

import util.SQLiteDBUtil;
import util.TelephonyData;
import util.TelephonyManagerUtil;
import util.ToastUtil;
import util.WebUtil;

import android.app.Service;
import android.content.Context;
import android.content.Intent; 
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.IBinder; 

import android.telephony.TelephonyManager;
import android.widget.Toast;
 
/* 自定义MyService继承Service */
public class MonitorService extends Service implements SensorEventListener 
{ 
   private SensorManager mSensorManager;    
  private Sensor lightSensor;
  private Sensor accelSensor; 
  private TelephonyManager telManager;
  private SQLiteDBUtil sqlUtil;
   
  @Override
  public void onCreate()
  {     
    	
	  try {
		Thread.sleep(10000);
	} catch (InterruptedException e) { 
		e.printStackTrace();
	}
	  
	   
    	//更新手机信息
		 sqlUtil = new SQLiteDBUtil(this);
    	
    	   updatePhoneData(); 
   
    	// testDB();  
  
      /* 读取防盗服务状态(1:启动，0:关闭) */
    	 
    	// Toast.makeText(this, "MonitorService create : warningType "+warningType, Toast.LENGTH_SHORT).show();
      mSensorManager =  (SensorManager)getSystemService(Context.SENSOR_SERVICE);  
      lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
      boolean isAvailable = mSensorManager.registerListener
      (
        this,
        lightSensor,
        SensorManager.SENSOR_DELAY_FASTEST
      );
      if (!isAvailable) {
         Toast.makeText(this, "感光器不可用", Toast.LENGTH_SHORT).show();
      } else { 
       // Toast.makeText(this, "感光器可用", Toast.LENGTH_SHORT).show();
      }  
      mSensorManager =  (SensorManager)getSystemService(Context.SENSOR_SERVICE);  
      accelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
      isAvailable = mSensorManager.registerListener
      (
        this,
        accelSensor,
        SensorManager.SENSOR_DELAY_FASTEST
      );
      if (!isAvailable) {
    	  Toast.makeText(this, "加速器不可用", Toast.LENGTH_SHORT).show();
      } else { 
    	  //Toast.makeText(this, "加速器可用", Toast.LENGTH_SHORT).show();
      }  
    
    super.onCreate();
  }
   

@Override
	public void onStart(Intent intent, int startId) {
		Const.isMonitorServiceRunning=true;
		Const.isAlertServiceRunning = false;
		System.out.println("Monitor开始监控....."+Const.isAlertServiceRunning);
		super.onStart(intent, startId);
	}

//检测手机卡变化，如果换卡则获取或者要求必须输入新的手机号码
private void updatePhoneData() {
	telManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
	TelephonyData phoneData = TelephonyManagerUtil.getTelephonyData(telManager); 
	 
	//updatePhoneDataDAO(phoneData);
	 
	String phoneIMEISaved = sqlUtil.getString(this,Const.DB_FIELD_PHONEIMEI ); 
	if(phoneIMEISaved.equals("")){//尚未存储过手机信息
		ToastUtil.show(this, "phoneIMEISaved.equals('')",Const.showMonitorService);
		 updatePhoneDataDAO(phoneData);
	}else{
		ToastUtil.show(this, "!phoneIMEISaved.equals('')",Const.showMonitorService);
		String phoneNumberIMSISaved = sqlUtil.getString(this,Const.DB_FIELD_PHONEIMSI );
		if(!phoneNumberIMSISaved.equals(phoneData.getPhoneIMSI())){//换了手机卡 
			//发送短信
			/*SmsManager smsManager = SmsManager.getDefault(); 
			PendingIntent mPI = PendingIntent.getBroadcast(this, 0, new Intent(), 0); 
	        smsManager.sendTextMessage("13820701232", null, "手机卡已换", mPI, null);*/
			ToastUtil.show(this, "更换了SIM卡",Const.showMonitorService);
			if(notiURL(phoneData)){
				ToastUtil.show(this, "notiUrl true",Const.showMonitorService);
				updatePhoneDataDAO(phoneData);
			}else{
				//给个标识，程序隐藏在后台，直到能上网了才发生效
				Toast.makeText(this, "服务器交互失败", Toast.LENGTH_LONG).show();
			}
		}else{
			//ToastUtil.show(this, "phoneNumberIMSISaved.equals(phoneData.getPhoneIMSI()",Const.showMonitorService);
		}
	}
} 
//更新数据库
private void updatePhoneDataDAO(TelephonyData phoneData){ 
	sqlUtil.setString( this, Const.DB_FIELD_PHONEIMSI,phoneData.getPhoneIMSI());
	sqlUtil.setString(this,Const.DB_FIELD_PHONEIMEI, phoneData.getPhoneIMEI());
	  if(phoneData.getPhoneNumber()==null||phoneData.getPhoneNumber().equals("")){//未获取到号码，让用户输入
		ToastUtil.show(this, "用户输入手机号码");
		Intent phoneNumberIntent = new Intent(Const.ACTIVITIES_INTENT);
		phoneNumberIntent.putExtra("type", "phoneNumberActivity");
		sendBroadcast(phoneNumberIntent);  
	}else{//提取到手机号码，直接存储 
		ToastUtil.show(this, "手机号码"+phoneData.getPhoneNumber());
		sqlUtil.setString(this,Const.DB_FIELD_PHONENUMBER, phoneData.getPhoneNumber()); 
	} 
}

private boolean notiURL(TelephonyData phoneData) {
	 String url = Const.makeURL(phoneData); 
	 return WebUtil.visit(this,url);
}

@Override
  public void onDestroy()
  {  
	System.out.println("Monitor停止监控....."+Const.isMonitorServiceRunning);
	//if(Const.isMonitorServiceRunning){
		try
	    {  
			//Toast.makeText(this, "MonitorService stop : warningType "+warningType, Toast.LENGTH_SHORT).show();
		  sqlUtil.close(); 
	      mSensorManager.unregisterListener(this, lightSensor);
	      mSensorManager.unregisterListener(this, accelSensor);
	      
	    }
	    catch(Exception e)
	    {
	     // e.printStackTrace();
	    }
	//}  
	Const.isMonitorServiceRunning=false;
    super.onDestroy();
  }
 
  @Override
  public IBinder onBind(Intent arg0)
  {
    return null;
  }
  
  
  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy)
  { 
	  
  }

	  @Override
	  public void onSensorChanged(SensorEvent event)
	  { 
	
	    //放兜里
	    if(event.sensor.getType()==Sensor.TYPE_LIGHT && Const.warningType == Const.EYE1)
	{ 
	   
	   computeLight(event.values[0]);
	}
	    //放一边儿
	if( event.sensor.getType()==Sensor.TYPE_ACCELEROMETER  )
	{ 
		if(Const.warningType == Const.EYE3){ 
			 computeAccelerometer_moved(event.values); 
		} else if(Const.warningType == Const.EYE5){
			computeAccelerometer_dropped(event.values);
		} 
	    }
	  }
	 float[] preAccelerometerValues = new float[]{-111111f,-1f,-1f};
	 private double previousAppliedAcceleration = 111111f; 
	 
  	private void computeAccelerometer_moved(float[] values) {
  		//手机被移动
	    if(preAccelerometerValues[0]!=-111111f&&AccelerometerUtil.move(values,preAccelerometerValues)){
	    	alert();
	    }
	    preAccelerometerValues[0] = values[0];
	    preAccelerometerValues[1] = values[1];
	    preAccelerometerValues[2] = values[2];
	}
  	//掉地上
	private void computeAccelerometer_dropped(float[] values) {
		
		
		
	    double appliedAcceleration = AccelerometerUtil.getAppliedAcceleration(values);
	    if(previousAppliedAcceleration!=111111f&&AccelerometerUtil.drop(appliedAcceleration, previousAppliedAcceleration)){
	    	alert();
	    }
	    previousAppliedAcceleration = appliedAcceleration; 
	}
  	

	float preLightValue = 0;
  
	
	private void alert(){
		if(!Const.isAlertServiceRunning){
			
			Intent warning = new Intent(Const.ACTIVITIES_INTENT);
			sendBroadcast(warning);
			Const.isAlertServiceRunning = true;
		}  
	} 
	private void computeLight(float lightValue) {
		if(LightUtil.inPocket(lightValue,preLightValue)){ 
			  alert();
	      }else if(lightValue<=4){ 
	    	 
	      }
	      preLightValue = lightValue;
	}
  
  
}

