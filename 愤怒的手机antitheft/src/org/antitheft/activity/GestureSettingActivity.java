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
    /* 必须?setContentView之前调用回屏幕显示 */
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags
    (
      WindowManager.LayoutParams.FLAG_FULLSCREEN,
      WindowManager.LayoutParams.FLAG_FULLSCREEN
    );
    /* 加载main.xml Layout */
    setContentView(R.layout.gesture);  
    /* 查看SDCard是否存在 */ 
    if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) 
    { 
      /* SD卡不存在，显示Toast信息 */
      //Toast.makeText(GestureSettingActivity.this,"SD卡不存在!",Toast.LENGTH_LONG).show(); 
      finish(); 
    }
    /* 以findViewById()取得对象 */ 
    
    myGesImage = (ImageView)this.findViewById(R.id.myGesImage);
    myGesImage.setImageResource(R.drawable.lock); 
    
    
    buttonCancel = (Button)this.findViewById(R.id.myButtonCancel);
    overlay = (GestureOverlayView) findViewById(R.id.myGestures);  
    /* 取得GestureLibrary的文件路径 */
    gesPath = new File(Environment.getExternalStorageDirectory(),Const.GESTURE_NAME).getAbsolutePath();  
    /* 设定Overlay的OnGestureListener */
    file = new File(gesPath);
    lib = GestureLibraries.fromFile(gesPath);   
     
    updateImageView();
     
    overlay.addOnGestureListener(new GestureOverlayView.OnGestureListener()
    {
      @Override
      public void onGesture(GestureOverlayView overlay,MotionEvent event) 
      {
      }
      /* 开始画手势时将新增的Button disable，并清除Gesture */
      @Override
      public void onGestureStarted(GestureOverlayView overlay,MotionEvent event) 
      { 
     
        ges = null; 
      }
      /* 手势画完时判断名称与手写是否完整建立 */
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
    
    /* 设定button01的OnClickListener */
    
  }

private void saveGesture(){
	try 
    { 
      
      if(!file.exists())
      {
        /* 文件不存在就直接写入 */
        lib.addGesture(Const.GESTURE_NAME,ges);
        if(lib.save()) 
        { 
          /* 将设定画面数据清除 */ 
        	updateImageView();
          overlay.clear(true); 
          /* 保存成功，显示Toast信息 */
        }
        else 
        {
          /* 保存失败，显示Toast信息 */
        } 
      }
      else
      {
        /* 文件存在时因读取保存的Gesture */
        if (!lib.load()) 
        { 
          /* Library读取失败，显示Toast讯息 */
         	//Toast.makeText(GestureSettingActivity.this, getString(R.string.load_failed)+":"+gesPath,Toast.LENGTH_LONG).show(); 
        }
        else
        {
          /* 如果Library中存在相同名称，则因将其移除再写入 */
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
            /* 将设定画面数据清除 */ 
        	updateImageView();
           
            overlay.clear(true);
            /* 保存成功，显示Toast信息 */
            Toast.makeText(GestureSettingActivity.this,getString(R.string.save_success),Toast.LENGTH_LONG).show(); 
          }
          else 
          {  
            /* 保存失败，显示Toast信息 */
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
	      /* 读取失败，显示Toast信息 */
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

