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

/* �Զ���MyService�̳�Service */
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
	/* ɾ��Notification��method */
	public void deleteNoti() {
		notiManager.cancel(Const.NOTI_ID);
	}

	/* ����Notification��method */
	public void setNoti(int iconImg, String icontext) {
		/* ������ѡNotification������ʱ����ִ�е�Activity */
		Intent notifyIntent = new Intent(this, MainActivity.class);
		notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		/* ����PendingIntent��Ϊ�趨����ִ�е�Activity */
		PendingIntent appIntent = PendingIntent.getActivity(this, 0,
				notifyIntent, 0);
		/* ����Notification�����趨��ز��� */
		Notification myNoti = new Notification();
		/* �趨status bar��ʾ��icon */
		myNoti.icon = iconImg;
		/* �趨notification����ʱ��ʱ��߶Ԥ������ */
		myNoti.defaults = Notification.DEFAULT_LIGHTS;
		myNoti.setLatestEventInfo(this,
				 Const.APPNAME, icontext,
				appIntent);
		/* �ͳ�Notification */
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
