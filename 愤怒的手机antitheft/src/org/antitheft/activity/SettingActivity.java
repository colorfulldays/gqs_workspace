package org.antitheft.activity;


import org.antitheft.Const;
import org.antitheft.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;
public class SettingActivity extends Activity{
	private TextView tvGusture;
	private TextView tvPWD;
	private TextView tvMusic;
	private TextView tvSMSPhoneNum;
	private TextView tvPhoneNum;

 @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.setting);
	init();
	tvGusture.setOnTouchListener(new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Intent intent = new Intent(getApplicationContext(), GestureSettingActivity.class);
			startActivity(intent);
			return false;
		}
	});
//	tvGusture.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			Intent intent = new Intent(getApplicationContext(), GestureSettingActivity.class);
//			startActivity(intent);
//			
//		}
//	});
	tvPWD.setOnTouchListener(new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Intent intent = new Intent(getApplicationContext(), PhoneNumberActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("type", Const.SETTING_PASSWORD);
			intent.putExtras(bundle);
			startActivity(intent);
			return false;
		}
	});
//	tvPWD.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			Intent intent = new Intent(getApplicationContext(), PhoneNumberActivity.class);
//			Bundle bundle = new Bundle();
//			bundle.putInt("type", Const.SETTING_PASSWORD);
//			intent.putExtras(bundle);
//			startActivity(intent);
//			
//		}
//	});
	tvMusic.setOnTouchListener(new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Intent intent = new Intent(getApplicationContext(), MusicSettingActivity.class);
			startActivity(intent);
			return false;
		}
	});
//	tvMusic.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Intent intent = new Intent(getApplicationContext(), MusicSettingActivity.class);
//			startActivity(intent);
//		}
//	});
	tvSMSPhoneNum.setOnTouchListener(new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Intent intent = new Intent(getApplicationContext(), PhoneNumberActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("type", Const.SETTING_SMS_NUMBER);
			intent.putExtras(bundle);
			startActivity(intent);
			return false;
		}
	});
//	tvSMSPhoneNum.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			Intent intent = new Intent(getApplicationContext(), PhoneNumberActivity.class);
//			Bundle bundle = new Bundle();
//			bundle.putInt("type", Const.SETTING_SMS_NUMBER);
//			intent.putExtras(bundle);
//			startActivity(intent);
//			
//		}
//	});
	tvPhoneNum.setOnTouchListener(new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Intent intent = new Intent(getApplicationContext(), PhoneNumberActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("type", Const.SETTING_THIS_NUMBER);
			intent.putExtras(bundle);
			startActivity(intent);
			return false;
		}
	});
//	tvPhoneNum.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			Intent intent = new Intent(getApplicationContext(), PhoneNumberActivity.class);
//			Bundle bundle = new Bundle();
//			bundle.putInt("type", Const.SETTING_THIS_NUMBER);
//			intent.putExtras(bundle);
//			startActivity(intent);
//			
//		}
//	});
}
 public void init()
 {

	 tvGusture=(TextView)findViewById(R.id.tvGesture);

	 tvPWD=(TextView)findViewById(R.id.tvPWD);
	
	 tvMusic=(TextView)findViewById(R.id.tvSound);
	
	 tvSMSPhoneNum=(TextView)findViewById(R.id.tvSMSPhoneNum);
	
	 tvPhoneNum=(TextView)findViewById(R.id.tvPhoneNum);

 }
}
