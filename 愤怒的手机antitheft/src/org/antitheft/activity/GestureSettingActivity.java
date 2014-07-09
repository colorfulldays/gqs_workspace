package org.antitheft.activity; 
 
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import org.antitheft.Const;
import org.antitheft.R;
import org.antitheft.R.drawable;
import org.antitheft.R.id;
import org.antitheft.R.layout;
import org.antitheft.R.string;

import util.ToastUtil;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class GestureSettingActivity extends Activity 
{
  private Gesture ges;
  private GestureLibrary lib; 
  private GestureOverlayView overlay; 
  private Button  buttonCancel;
  private ImageView myGesImage;
  private String gesPath; 
  private File file;
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState);
    /* ����?setContentView֮ǰ���û���Ļ��ʾ */
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags
    (
      WindowManager.LayoutParams.FLAG_FULLSCREEN,
      WindowManager.LayoutParams.FLAG_FULLSCREEN
    );
    /* ����main.xml Layout */
    setContentView(R.layout.gesture);  
    /* �鿴SDCard�Ƿ���� */ 
    if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) 
    { 
      /* SD�������ڣ���ʾToast��Ϣ */
      //Toast.makeText(GestureSettingActivity.this,"SD��������!",Toast.LENGTH_LONG).show(); 
      finish(); 
    }
    /* ��findViewById()ȡ�ö��� */ 
    
    myGesImage = (ImageView)this.findViewById(R.id.myGesImage);
    myGesImage.setImageResource(R.drawable.lock); 
    
    
    buttonCancel = (Button)this.findViewById(R.id.myButtonCancel);
    overlay = (GestureOverlayView) findViewById(R.id.myGestures);  
    /* ȡ��GestureLibrary���ļ�·�� */
    gesPath = new File(Environment.getExternalStorageDirectory(),Const.GESTURE_NAME).getAbsolutePath();  
    /* �趨Overlay��OnGestureListener */
    file = new File(gesPath);
    lib = GestureLibraries.fromFile(gesPath);   
     
    updateImageView();
     
    overlay.addOnGestureListener(new GestureOverlayView.OnGestureListener()
    {
      @Override
      public void onGesture(GestureOverlayView overlay,MotionEvent event) 
      {
      }
      /* ��ʼ������ʱ��������Button disable�������Gesture */
      @Override
      public void onGestureStarted(GestureOverlayView overlay,MotionEvent event) 
      { 
     
        ges = null; 
      }
      /* ���ƻ���ʱ�ж���������д�Ƿ��������� */
      @Override
      public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) 
      { 
        ges = overlay.getGesture(); 
        if (ges!=null ) 
        { 
        	saveGesture();
        
        }
      } 
      @Override
      public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) 
      { 
      }
    });
     
    buttonCancel.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View v) 
      { 
         finish();
      }
    });
    
    /* �趨button01��OnClickListener */
    
  }

private void saveGesture(){
	try 
    { 
      
      if(!file.exists())
      {
        /* �ļ������ھ�ֱ��д�� */
        lib.addGesture(Const.GESTURE_NAME,ges);
        if(lib.save()) 
        { 
          /* ���趨����������� */ 
        	updateImageView();
          overlay.clear(true); 
          /* ����ɹ�����ʾToast��Ϣ */
        }
        else 
        {
          /* ����ʧ�ܣ���ʾToast��Ϣ */
        } 
      }
      else
      {
        /* �ļ�����ʱ���ȡ�����Gesture */
        if (!lib.load()) 
        { 
          /* Library��ȡʧ�ܣ���ʾToastѶϢ */
         	//Toast.makeText(GestureSettingActivity.this, getString(R.string.load_failed)+":"+gesPath,Toast.LENGTH_LONG).show(); 
        }
        else
        {
          /* ���Library�д�����ͬ���ƣ��������Ƴ���д�� */
          Set<String> en=lib.getGestureEntries();
          if(en.contains(Const.GESTURE_NAME))
          {
            ArrayList<Gesture> al=lib.getGestures(Const.GESTURE_NAME);
            for(int i=0;i<al.size();i++){
              lib.removeGesture(Const.GESTURE_NAME,al.get(i)); 
            }
          } 
          lib.addGesture(Const.GESTURE_NAME,ges);
          if(lib.save()) 
          { 
            /* ���趨����������� */ 
        	updateImageView();
           
            overlay.clear(true);
            /* ����ɹ�����ʾToast��Ϣ */
            Toast.makeText(GestureSettingActivity.this,getString(R.string.save_success),Toast.LENGTH_LONG).show(); 
          }
          else 
          {  
            /* ����ʧ�ܣ���ʾToast��Ϣ */
            Toast.makeText(GestureSettingActivity.this, getString(R.string.save_failed),Toast.LENGTH_LONG).show(); 
          } 
        }
      }
    }
    catch(Exception e) 
    { 
      e.printStackTrace(); 
    } 
}
  
private void updateImageView() {
	 if (!lib.load()) 
	    { 
	      /* ��ȡʧ�ܣ���ʾToast��Ϣ */
	     // showToast("!lib.load()"); 
	     // finish();
	    } else{
	    	 
	    	  Object[] ens = lib.getGestureEntries().toArray(); 
	          for(int i=0;i<ens.length;i++){ 
	          	ArrayList<Gesture> al = lib.getGestures(ens[i].toString()); 
	        	  for(int j=0;j<al.size();j++){
	        		  Gesture gs = (Gesture)al.get(j); 
	        		 myGesImage.setImageBitmap(gs.toBitmap(200, 200, 20, Color.GREEN));
	        	  }
	          }
	    	
	    }
}

protected void showToast(String string) {
	 ToastUtil.show(this,"ens.length"+string, true);
}
} 

