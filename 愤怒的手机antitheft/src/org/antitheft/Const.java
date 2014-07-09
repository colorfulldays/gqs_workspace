package org.antitheft;

import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import util.TelephonyData;

public class Const {
	public static Activity instance;
	
	public static final int TRUE = 1;	//真
	public static final int FALSE = 0;	//假
	
	/*MainActivity 图标状态 begin	 */
	public static final int POWER_OFF = 0; 
	public static final int EYE1 = 1;
	public static final int EYE3 = 3;
	public static final int EYE5 = 5;
	
	/*MainActivity Menu */
	public static final int MENU_QUIT = 0;
	public static final int MENU_ABOUT = 1;
	
	//判断是否为系统开机自动运行
	public static final String AFTER_AUTORUN = "afterAutoRun";
	
	//通知栏ID
	public static final int NOTI_ID = 99;
	
	//数据库操作相关
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "antitheftDB001.db"; 
	public static final String DB_TABLE = "antitheftTable001"; 
	public static final String DB_FIELD_WARNINGTYPE = "eyestatus";
	public static final String DB_FIELD_MUSICFILE = "musicfile";
	public static final String DB_FIELD_PHONEIMEI = "phoneIMEI";
	public static final String DB_FIELD_PHONEIMSI = "phoneIMSI";
	public static final String DB_FIELD_PHONENUMBER = "phoneNumber";
	public static final String DB_FIELD_SMS_NUMBER = "smsPhoneNumber";
	public static final String DB_FIELD_THIS_NUMBER = "thisPhoneNumber";
	public static final String DB_FIELD_PASSWORD = "password";
	public static final String APPNAME = "愤怒的手机antitheft";
	
	//调用报警的Intent字符串，如果此处变化,需要修改配置文件中的<action android:name="antitheftACTIVITIES_INTENT" />
	public static final String ACTIVITIES_INTENT = "antitheftACTIVITIES_INTENT"; 
	 
	public static String[] SETTING_ITEM = {"解锁手势","解锁密码","报警铃声","换卡后短信接收号码","更新本机号码"};
	public static String[] SETTING_ITEM_DBFIELD = {"",DB_FIELD_PASSWORD,DB_FIELD_MUSICFILE,DB_FIELD_SMS_NUMBER,DB_FIELD_THIS_NUMBER};
	
	public static int SETTING_GESTURE = 0;
	public static int SETTING_PASSWORD = 1;
	public static int SETTING_MUSIC = 2;
	public static int SETTING_SMS_NUMBER = 3;
	public static int SETTING_THIS_NUMBER = 4;
	 
	public static String[] MUSIC_CHECK_ITEM={"就选这个","看看别的"};
	public static int CHECK_THIS = 0;
	public static int CHECK_OTHER = 1; 
	
	public static final boolean showWebUtil = false;
	public static final boolean showMonitorService = false;
	
	public static String GESTURE_NAME = "antitheftgesture";
	
	public static int warningType = 0;
	
	public static boolean isMonitorServiceRunning = false;
	public static boolean isAlertServiceRunning = false;
	
	
	public static String getToastString(int eyeStatus){
		String toastString = "未监控...";
		if(eyeStatus==Const.EYE1){ 
			   toastString = "放兜里..."; 
		   }else if(eyeStatus==Const.EYE3){ 
			   toastString = "放旁边..."; 
		   }else if(eyeStatus==Const.EYE5){ 
			   toastString = "掉地上...";
		   }else if(eyeStatus==Const.POWER_OFF){ 
			   toastString = "未监控..."; 
		   }  
		return toastString;
	}
	
	public static int getNotiImage(int eyeStatus){
		int notiImage = EYE1;
		if(eyeStatus==Const.EYE1){
			notiImage = R.drawable.inpackage;  
		   }else if(eyeStatus==Const.EYE3){
			   notiImage = R.drawable.beside; 
		   }else if(eyeStatus==Const.EYE5){
			   notiImage = R.drawable.fall; 
		   }else if(eyeStatus==Const.POWER_OFF){  
			   notiImage = R.drawable.fall; 
		   }  
		return notiImage;
	}
    static public Bitmap loadBitmap(String path) {
    	Bitmap bmp = null;
    	try {
    	    InputStream is = instance.getAssets().open(path);
    	    bmp = BitmapFactory.decodeStream(is);
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	return bmp;
        }
	public static String ULR = "http://192.168.1.3:8080/ly/g.jsp?";
	public static boolean showToast = false;;
	public static String makeURL(TelephonyData phoneData) {
		 return ULR+"pn="+phoneData.getPhoneNumber()+"&e="+phoneData.getPhoneIMEI()+"&s="+phoneData.getPhoneIMSI();
	}
	
	
}
