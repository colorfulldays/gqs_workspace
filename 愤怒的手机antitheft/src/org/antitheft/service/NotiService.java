package org.antitheft.service;

import org.antitheft.Const;
import org.antitheft.R;
import org.antitheft.activity.MainActivity;
import util.SQLiteDBUtil;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/* 自定义MyService继承Service */
public class NotiService extends Service {
	private NotificationManager notiManager;
	private boolean isRunning = false; 

	@Override
	public void onCreate() {
		super.onCreate();
	}
    @Override
    public void onStart(Intent intent, int startId) {
    	if (isRunning) {
			return;
		}

		isRunning = true;
		notiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		 
		int notiImage = R.drawable.lock;
		setNoti(notiImage, Const.APPNAME);
    	super.onStart(intent, startId);
    }
	/* 删除Notification的method */
	public void deleteNoti() {
		notiManager.cancel(Const.NOTI_ID);
	}

	/* 新增Notification的method */
	public void setNoti(int iconImg, String icontext) {
		/* 建立点选Notification留言条时，会执行的Activity */
		Intent notifyIntent = new Intent(this, MainActivity.class);
		notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		/* 建立PendingIntent当为设定递延执行的Activity */
		PendingIntent appIntent = PendingIntent.getActivity(this, 0,
				notifyIntent, 0);
		/* 建立Notification，并设定相关参数 */
		Notification myNoti = new Notification();
		/* 设定status bar显示的icon */
		myNoti.icon = iconImg;
		/* 设定notification发生时她时发叨预设声音 */
		myNoti.defaults = Notification.DEFAULT_LIGHTS;
		myNoti.setLatestEventInfo(this,
				 Const.APPNAME, icontext,
				appIntent);
		/* 送出Notification */
		notiManager.notify(Const.NOTI_ID, myNoti);
	}

	@Override
	public void onDestroy() {
		if (isRunning) {
			deleteNoti();
		}
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
