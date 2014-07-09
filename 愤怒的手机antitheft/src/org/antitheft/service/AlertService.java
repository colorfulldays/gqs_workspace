package org.antitheft.service;

import java.io.File;
import java.io.IOException;

import org.antitheft.Const;
import org.antitheft.R;
import util.SQLiteDBUtil;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
/* 自定义MyService继承Service */
public class AlertService extends Service 
{ 
  private Vibrator mVibrator; 
  private MediaPlayer myPlayer;  
  private boolean isRunning = false;
  private SQLiteDBUtil sqlUtil;
  @Override
  public void onCreate()
  {      
	  if( isRunning){
    		return;
    	} 
    	isRunning = true; 
    	 mVibrator = ( Vibrator )getApplication().getSystemService( Service .VIBRATOR_SERVICE ); 
    	 myPlayer = new MediaPlayer();
    	vibrator();
    	playMusic();
    super.onCreate();
  }
    @Override
    	public void onStart(Intent intent, int startId) {
    		System.out.println("开始启动alertService");
    		super.onStart(intent, startId);
    	}
	private void vibrator() {
		 mVibrator.vibrate(new long[]{50,1000},0);
	}
	private void playMusic() {
		 System.out.println("播放音乐");
			sqlUtil = new SQLiteDBUtil(this); 
	 	   try {
	 		String mp3 = sqlUtil.get(this, Const.DB_FIELD_MUSICFILE); 
	 		System.out.println("音乐路径===="+mp3);
	 		sqlUtil.close(); 
	 		boolean played = false;
	 		if(!mp3.equals("")){ 
	 			File file = new File(mp3);
	 			if(file.exists()){
	 				played = true; 
	 				myPlayer.setDataSource(mp3);
	 			}
	 		}
	 		if(!played)
	 		{//如果预设文件被移除，则使用默认声音
	 			 
	 			myPlayer = MediaPlayer.create(this, R.raw.defaultmusic);
	 		}
	 		 
	 		myPlayer.prepare();
	 		myPlayer.setVolume(8, 8);  
	 		myPlayer.setLooping(true);
	 		myPlayer.start();	 
	 		
	 	} catch (IllegalArgumentException e) { 
	 		//ToastUtil.show(this, e.getMessage());
	 		e.printStackTrace();
	 	} catch (IllegalStateException e) { 
	 		//ToastUtil.show(this, e.getMessage());
	 		e.printStackTrace();
	 	} catch (IOException e) { 
	 		//ToastUtil.show(this, e.getMessage());
	 		e.printStackTrace();
	 	}
	}

@Override
  public void onDestroy()
  { 
	System.out.println("关闭Alert");
	if(isRunning){
		try
	    {  
		  mVibrator.cancel(); 
	      myPlayer.release(); 
	      isRunning = false;
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	}  
    super.onDestroy();
  }
 
@Override
public IBinder onBind(Intent intent) {
	// TODO Auto-generated method stub
	return null;
}
}

