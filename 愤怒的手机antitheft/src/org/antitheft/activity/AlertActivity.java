package org.antitheft.activity;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.antitheft.Const;

import org.antitheft.service.AlertService;
import org.antitheft.service.MonitorService;
import org.antitheft.service.NotiService;

import util.GestureUtil;
import util.SQLiteDBUtil;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.os.Environment;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AlertActivity extends Activity {
	private Gesture ges;
	private GestureLibrary lib;
	private GestureOverlayView overlay;
	private Button buttonReset, buttonPassword;
	private String gesPath;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState); 
		// stopNoti(); 
		/* ����main.xml Layout */
		setContentView(R.layout.warning);
		/* �鿴SDCard�Ƿ���� */
		if (!Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			/* SD�������ڣ���ʾToast��Ϣ */
			// Toast.makeText(AlertActivity.this,"SD��������!",Toast.LENGTH_LONG).show();
			gestureSuccess();
			// finish();
		}
		/* ��findViewById()ȡ�ö��� */
		buttonReset = (Button) this.findViewById(R.id.myButtonReset);
		buttonPassword = (Button) this.findViewById(R.id.myButtonPassword);
		overlay = (GestureOverlayView) findViewById(R.id.myGestures);
		/* ȡ��GestureLibrary���ļ�·�� */
		gesPath = new File(Environment.getExternalStorageDirectory(),
				Const.GESTURE_NAME).getAbsolutePath();
		/* �趨Overlay��OnGestureListener */
		overlay.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
			@Override
			public void onGesture(GestureOverlayView overlay, MotionEvent event) {
			}

			/* ��ʼ������ʱ��������Button disable�������Gesture */
			@Override
			public void onGestureStarted(GestureOverlayView overlay,
					MotionEvent event) {
				ges = null;
			}

			/* ���ƻ���ʱ�ж���������д�Ƿ��������� */
			@Override
			public void onGestureEnded(GestureOverlayView overlay,
					MotionEvent event) {
				gestureEnded();
			}

			@Override
			public void onGestureCancelled(GestureOverlayView overlay,
					MotionEvent event) {
			}
		});
		/* ����button02��OnClickListener */
		buttonReset.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				overlay.clear(true);
			}
		});
		buttonPassword.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				beCool = true;
				Intent intent = new Intent();
				intent.setClass(AlertActivity.this, PhoneNumberActivity.class);
				startActivity(intent);
				finish();

			}
		});

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
		}
		return true;

	}

	/**
	 * ������֤�ɹ�
	 */
	void gestureSuccess() { 
		stopAlertService();
		stopMonitorService();
		
		
		Const.warningType = Const.POWER_OFF;
		Const.isAlertServiceRunning = false;
 		finish();
		System.out.println("�رվ���Activity");
	}

	protected void gestureEnded() {
		ges = overlay.getGesture();
		if (ges != null) {
			lib = GestureLibraries.fromFile(gesPath);
			if (GestureUtil.match(this, lib, ges)) { // �����ɹ�
				gestureSuccess();
			} else {
				// keep
			}
		} else {
		}
	}
 
	protected void stopAlertService() {
		stopService(AlertService.class);
		
	}
	protected void stopMonitorService() {
		stopService(MonitorService.class);
	}

	protected void startMonitorService() {
		Intent intent = new Intent(this, MonitorService.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(intent);
	}

	private void stopService(Class myclass) {
		try {
			Intent intent = new Intent(this, myclass);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			stopService(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startService() {
		try {
			Intent intent = new Intent(this, AlertService.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startService(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Timer timer;
	TimerTask task;
}
