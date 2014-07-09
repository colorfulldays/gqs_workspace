package util;

import java.io.IOException;

import android.media.MediaPlayer;

public class MediaPlayerUtil {
	public static void pause( MediaPlayer mp){
		if(mp.isPlaying())
        {
	          mp.setLooping(false);
	          mp.pause();
        }
	}
	
	public static void stop( MediaPlayer mp){
		if(mp.isPlaying())
        {
	          mp.setLooping(false);
	          mp.stop();
        }
	}
	
	public static void play( MediaPlayer myPlayer,boolean loop){
		if(!myPlayer.isPlaying())
        {
         try {
			myPlayer.prepare();
		} catch (IllegalStateException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		  myPlayer.seekTo(0); 
          myPlayer.setLooping(loop);
          myPlayer.start();
        }
	}
	
}
