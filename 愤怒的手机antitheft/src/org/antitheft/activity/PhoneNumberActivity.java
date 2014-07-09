package org.antitheft.activity; 
 

import org.antitheft.Const;
import org.antitheft.R;
import org.antitheft.service.AlertService;

import util.SQLiteDBUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PhoneNumberActivity extends Activity 
{
  
  private Button buttonNo1,buttonNo2,buttonNo3,buttonNo4,buttonNo5,buttonNo6,buttonNo7,buttonNo8,buttonNo9,buttonNo0,buttonDone,buttonCancel;  
  private TextView textViewPhoneNumber;
  private TextView title;
  private String inputNumber = "";
  private SQLiteDBUtil sqlUtil;
  private int type;
  String password = null; 
  @Override
	protected void onDestroy() { 
		super.onDestroy();
	} 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
	  super.onCreate(savedInstanceState);
	  /* 加载main.xml Layout */
	  setContentView(R.layout.phonenumber);
	  
	  sqlUtil = new SQLiteDBUtil(this );
	  buttonNo0 = (Button)this.findViewById(R.id.no0);
	  buttonNo1 = (Button)this.findViewById(R.id.no1);
	  buttonNo2 = (Button)this.findViewById(R.id.no2);
	  buttonNo3 = (Button)this.findViewById(R.id.no3);
	  buttonNo4 = (Button)this.findViewById(R.id.no4);
	  buttonNo5 = (Button)this.findViewById(R.id.no5);
	  buttonNo6 = (Button)this.findViewById(R.id.no6);
	  buttonNo7 = (Button)this.findViewById(R.id.no7);
	  buttonNo8 = (Button)this.findViewById(R.id.no8);
	  buttonNo9 = (Button)this.findViewById(R.id.no9);
	  buttonCancel = (Button)this.findViewById(R.id.myButtonCancel);
	  buttonDone = (Button)this.findViewById(R.id.myButtonDone);
	  
	  OnClickListener onClickListener = null;  
	  //用于解锁的Listener
	  OnClickListener unlockListener = new Button.OnClickListener() { 
			@Override
			public void onClick(View v) { 
				 inputNumber += getStringById(v.getId()); 
				 textViewPhoneNumber.setText(inputNumber);
				 if (password!=null&&inputNumber.contains(password)) {//解锁
					 stopAlertService(); 
					 Const.warningType = Const.POWER_OFF;
 					 finish();
				 }
			} 
	  };
	  
	  //录入数字的Listener
	  OnClickListener numberListener = new Button.OnClickListener() { 
			@Override
			public void onClick(View v) {
				 if(R.id.myButtonCancel==v.getId()){
					 int index = 0;
					 if(inputNumber.length()-1>0){
						 index = inputNumber.length()-1;
					 }
					 inputNumber = inputNumber.substring(0,index);
				 }else if(R.id.myButtonDone==v.getId()){
					 //更新数据 
						if(updatePhoneNumber(type)){
							finish();
						}  
				 }else{
					 inputNumber += getStringById(v.getId());
				 }
				 textViewPhoneNumber.setText(inputNumber);
			} 
	  }; 
	  
	  Intent intent = this.getIntent();
	  Bundle bundle = intent.getExtras(); 
	  textViewPhoneNumber = (TextView)this.findViewById(R.id.myTextViewPhoneNumber);
	  title = (TextView)findViewById(R.id.myTextViewUp); 
	  
	  if(bundle!=null){//设置
		  onClickListener = numberListener;
		  type = bundle.getInt("type"); 
		  title.setText(Const.SETTING_ITEM[type]);  
		  inputNumber = sqlUtil.getString(this, Const.SETTING_ITEM_DBFIELD[type]);
		  textViewPhoneNumber.setText(inputNumber); 
	  }else{//解锁
		  onClickListener = unlockListener;
		  title.setText("输入解锁密码");
		  password = sqlUtil.getString(this, Const.DB_FIELD_PASSWORD);
		  buttonCancel.setEnabled(true);
		  buttonDone.setEnabled(false);  
	  }
	    
	  buttonNo0.setOnClickListener(onClickListener);
	  buttonNo1.setOnClickListener(onClickListener);
	  buttonNo2.setOnClickListener(onClickListener);
	  buttonNo3.setOnClickListener(onClickListener);
	  buttonNo4.setOnClickListener(onClickListener);
	  buttonNo5.setOnClickListener(onClickListener);
	  buttonNo6.setOnClickListener(onClickListener);
	  buttonNo7.setOnClickListener(onClickListener);
	  buttonNo8.setOnClickListener(onClickListener);
	  buttonNo9.setOnClickListener(onClickListener);
	  buttonCancel.setOnClickListener(onClickListener);
	  buttonDone.setOnClickListener(onClickListener); 
  }
  
  protected void stopAlertService() { 
		try{ 
		  Intent intent = new Intent(  this, AlertService.class); 
		  intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK ); 
		  stopService(intent);     
		}
		catch(Exception e)
		{
		  e.printStackTrace();
		}
	  }
  
  private String getStringById(int id) {
		 if(id==R.id.no0){
			 return "0";
		 }else if  (id==R.id.no1){
			 return "1";
		 }else  if(id==R.id.no2){
			 return "2";
		 }else if(id==R.id.no3){
			 return "3";
		 }else if(id==R.id.no4){
			 return "4";
		 }else if(id==R.id.no5){
			 return "5";
		 }else if(id==R.id.no6){
			 return "6";
		 }else if(id==R.id.no7){
			 return "7";
		 }else if(id==R.id.no8){
			 return "8";
		 }else if(id==R.id.no9){
			 return "9";
		 } 
		 return "";
	}
  
	private boolean updatePhoneNumber(int type) {
		String db_field = "";
		if(type==Const.SETTING_PASSWORD){
			db_field = Const.DB_FIELD_PASSWORD;
		}else if(type==Const.SETTING_SMS_NUMBER){
			db_field = Const.DB_FIELD_SMS_NUMBER;
		}else if(type==Const.SETTING_THIS_NUMBER){
			db_field = Const.DB_FIELD_THIS_NUMBER;
		}  
		
		 if(sqlUtil.setString( this,db_field, inputNumber)){
			 return true; 
		 }else{
			 return false;
		 }
	}
} 

