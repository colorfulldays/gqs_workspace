package org.antitheft.activity;

import org.antitheft.Const;
import org.antitheft.R;
import org.antitheft.service.MonitorService;
import org.antitheft.service.NotiService;

import util.SQLiteDBUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.waps.AdView;
import com.waps.AppConnect;

/**
 * @author Administrator 主Activity
 */
public class MainActivity extends Activity {
	private ImageView btnSetting;
	private ImageButton btnControl;
	private TextView tvStatu;

	int eye1_image;
	int eye3_image;
	int eye5_image;
	String toastString = "";
	LinearLayout layout;
	private ImageView imageEye1;
	private ImageView imageEye3;
	private ImageView imageEye5;
	private float screenWidth = 0;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("onCreate");
		 
		if (afterAutoRun()) {
			startNoti();
			finish();
		}
		stopNoti();
		setContentView(R.layout.main);
		//添加广告
		addAD();
		init();
		btnSetting.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),
						SettingActivity.class);
				startActivity(intent);
			}
		});
		btnControl.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Const.warningType == 0) {
					Toast.makeText(getApplicationContext(), "请选择监控模式",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (!Const.isMonitorServiceRunning) {
					startNoti();
					btnControl.setBackgroundResource(R.drawable.stop);
					tvStatu.setText(Const.getToastString(Const.warningType));
					startService();  
					finish();
				} else {
					btnControl.setBackgroundResource(R.drawable.start);
					stopMonitorService();
					Const.warningType = Const.POWER_OFF;
					updateEyeImage();
					tvStatu.setText(Const.getToastString(0));
					updateWarningType(0);
				}
			}
		});
		layout = (LinearLayout) findViewById(R.id.mainLayout);
		layout.setMinimumWidth((int) screenWidth / 2);
		/* 检查mosStatus是否启动状态(1) */
		updateEyeImage();
		imageEye1.setOnTouchListener(new Button.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				clickImage(Const.EYE1);
				return false;
			}
		});
		imageEye3.setOnTouchListener(new Button.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				clickImage(Const.EYE3);
				return false;
			}
		});
		imageEye5.setOnTouchListener(new Button.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				clickImage(Const.EYE5);
				return false;
			}
		});

	}
    public void addAD()
    {
    	
		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout);
		new AdView(this, container).DisplayAd(20);
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		startNoti();
		super.onPause();
	}

	private boolean afterAutoRun() {
		// 开机自动最小化
		Intent intent = getIntent();
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				return (bundle.getInt(Const.AFTER_AUTORUN) == Const.TRUE);
			}
		}
		return false;
	}

	protected void clickImage(int button) {
		if (Const.warningType != button) {
			// 更新数据值
			updateWarningType(button);
			/* 设定启动图案 */
			updateEyeImage();

		}
	}

	private void updateWarningType(int button) {
		Const.warningType = button; 
	}

	public Bitmap getBitmapById(int id) {
		return BitmapFactory.decodeResource(getResources(), id);
	}

	protected void updateEyeImage() {
		eye1_image = R.drawable.inpackage_noselected;
		eye3_image = R.drawable.beside_noselected;
		eye5_image = R.drawable.fall_noselected;

		if (Const.warningType == Const.EYE1) {
			eye1_image = R.drawable.inpackage;
		} else if (Const.warningType == Const.EYE3) {
			eye3_image = R.drawable.beside;
		} else if (Const.warningType == Const.EYE5) {
			eye5_image = R.drawable.fall;
		} else if (Const.warningType == Const.POWER_OFF) {
			
		}
		imageEye1.setImageResource(eye1_image);
		imageEye3.setImageResource(eye3_image);
		imageEye5.setImageResource(eye5_image);
		btnSetting.setImageResource(R.drawable.setting1);
		
		if(Const.isMonitorServiceRunning){
			btnControl.setBackgroundResource(R.drawable.stop);
		}else{
			btnControl.setBackgroundResource(R.drawable.start);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/* 离开的menu */
		menu.add(0, Const.MENU_ABOUT, Menu.FIRST, "").setIcon(
				R.drawable.menu_setting);
		menu.add(0, Const.MENU_QUIT, Menu.FIRST + 1, "").setIcon(
				R.drawable.menu_exit);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Const.MENU_QUIT:
			/* 离开前ALERT提醒 */
			quit();
			break;
		case Const.MENU_ABOUT:
			about();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void about() {
		LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
		LinearLayout layout = (LinearLayout) layoutInflater.inflate(
				R.layout.about, null);
		Builder ad = new AlertDialog.Builder(this);
		ad.setView(layout);
		ad.setTitle("关于");
		ad.show();
	}

	/**
	 * 停止防盗服务
	 */
	private void quit() {
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("")
				.setMessage("确定要离开吗？")
				.setPositiveButton("停止服务",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								stopMonitorService();
//								 
//								AppConnect.getInstance(MainActivity.this)
//										.finalize();
								finish();
								android.os.Process
										.killProcess(android.os.Process.myPid());
							}
						}).show();
	}

	public void startService() {
		try {
			Intent intent = new Intent(MainActivity.this, MonitorService.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startService(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param withToast
	 */
	public void stopMonitorService() {
		try {
			Intent intent = new Intent(MainActivity.this, MonitorService.class);
			stopService(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {
		imageEye1 = (ImageView) findViewById(R.id.imageEye1);
		imageEye3 = (ImageView) findViewById(R.id.imageEye3);
		imageEye5 = (ImageView) findViewById(R.id.imageEye5);
		btnSetting = (ImageView) findViewById(R.id.btnSetting);
		btnControl = (ImageButton) findViewById(R.id.btnControl);
		tvStatu = (TextView) findViewById(R.id.tvStatu);
		tvStatu.setText(Const.getToastString(Const.warningType));
		

	}

	@Override
	protected void onResume() {
		System.out.println("onResume"); 
		Const.warningType = Const.POWER_OFF;
		Const.isAlertServiceRunning = false;
		Const.isMonitorServiceRunning = false;
		stopMonitorService();
		System.out.println("after stopMonitor "+Const.isMonitorServiceRunning);
		stopNoti();
		System.out.println("after stopNoti "+Const.isMonitorServiceRunning);
		updateEyeImage();  
		System.out.println("after updateEyeImage "+Const.isMonitorServiceRunning);
		super.onResume();
	}

	private void startNoti() {
		try {
			Intent intent = new Intent(MainActivity.this, NotiService.class);
			startService(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stopNoti() {
		try {
			Intent intent = new Intent(MainActivity.this, NotiService.class);
			stopService(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 

}
