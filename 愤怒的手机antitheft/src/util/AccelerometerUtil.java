package util;

import android.hardware.SensorManager;
import android.util.Log;

public class AccelerometerUtil {

	public static boolean move(float[] values, float[] preAccelerometerValues) {
		float difx = Math.abs(values[SensorManager.DATA_X] );
		float dify = Math.abs(values[SensorManager.DATA_Y] );
		float difz = Math.abs(values[SensorManager.DATA_Z] );
		Log.e("log.e val = ", " "+difx+" "+dify+" "+difz);
		
		float  difx1 = Math.abs( preAccelerometerValues[SensorManager.DATA_X]);
		float  dify1 = Math.abs( preAccelerometerValues[SensorManager.DATA_Y]);
		float  difz1 = Math.abs( preAccelerometerValues[SensorManager.DATA_Z]);
		Log.e("log.e pre = ", " "+difx1+" "+dify1+" "+difz1);
		
		 
		float difx2 = Math.abs(values[SensorManager.DATA_X]-preAccelerometerValues[SensorManager.DATA_X]);
		float dify2 = Math.abs(values[SensorManager.DATA_Y]-preAccelerometerValues[SensorManager.DATA_Y]);
		float difz2 = Math.abs(values[SensorManager.DATA_Z]-preAccelerometerValues[SensorManager.DATA_Z]);
		Log.e("log.e  dif = ", " "+difx2+" "+dify2+" "+difz2);
		
		
		if(
			checkMove(values[SensorManager.DATA_X],preAccelerometerValues[SensorManager.DATA_X])
			||checkMove(values[SensorManager.DATA_Y],preAccelerometerValues[SensorManager.DATA_Y])
			||checkMove(values[SensorManager.DATA_Z],preAccelerometerValues[SensorManager.DATA_Z])
		){ 
			return true;
		}  
		return false;
	}
	
	
	public static double getAppliedAcceleration(float[] values){
		float x =  values[SensorManager.DATA_X]; 
	     float y =  values[SensorManager.DATA_Y]; 
	     float z =  values[SensorManager.DATA_Z]; 
	     Log.e("log.e", " "+x+" "+y+" "+z);
	      /*�ֻ��Ƿ����µ��ٶȱ�׼*/ 
	     double appliedAcceleration = 0.0f;  
	     /* SensorManager.GRAVITY_EARTH = 9.8m/s2 */ 
	     appliedAcceleration +=  
	     Math.pow(x/SensorManager.GRAVITY_EARTH, 2.0);  
	      
	     appliedAcceleration +=  
	     Math.pow(y/SensorManager.GRAVITY_EARTH, 2.0);  
	      
	     appliedAcceleration +=  
	     Math.pow(z/SensorManager.GRAVITY_EARTH, 2.0);   
	     appliedAcceleration = Math.sqrt(appliedAcceleration);    
	     return appliedAcceleration;
	}
	
	 public static final double forceThreshHold = 2d;      
	 
	 public static boolean drop(double appliedAcceleration, double previousAcceleration) {
		  
	
     /*�ж��ֻ��Ƿ����µ��ж�ʽ
      * ��ص�ʱ�ٶ���С��1.5f, ���ǰ�ٶȱ������1.5f*/
     if((appliedAcceleration < forceThreshHold) &&  (previousAcceleration > forceThreshHold)) 
     { 
		return true;
     }
     
     return false;
     
	}
	

	static double dif = 1.5;
	private static boolean checkMove(float f, float g) {
		double thisdif = f-g; 
		if( Math.abs(thisdif)>dif){ 
			return true;
		}
		return false;
		 
	}

}
