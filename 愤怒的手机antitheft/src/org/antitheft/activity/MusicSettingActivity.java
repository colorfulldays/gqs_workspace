package org.antitheft.activity;

/* import相关class */
import java.io.File;
import java.util.ArrayList;
import java.util.List; 

import org.antitheft.Const;
import org.antitheft.R;

import util.SQLiteDBUtil;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface; 
import android.os.Bundle; 
import android.view.View;
import android.widget.Button; 
import android.widget.ListView;
import android.widget.TextView;
import android.content.DialogInterface.OnClickListener;

public class MusicSettingActivity extends ListActivity
{
  /* 对象声明 
     items：存放显示的名称
     paths：存放文件路径
     rootPath：起始目录
  */
  private List<String> items=null;
  private List<String> paths=null;
  private String rootPath="/";
  private TextView mPath;
  private SQLiteDBUtil sqlUtil;
  private Button buttonCancel;
  private  String musicFilePath;
  @Override
  protected void onCreate(Bundle icicle)
  {
    super.onCreate(icicle);
    /* 加载main.xml Layout */
    setContentView(R.layout.music);
    /* 初始化mPath，用以显示目前路径 */
    mPath=(TextView)findViewById(R.id.mPath);
    buttonCancel = (Button)this.findViewById(R.id.myButtonCancel);
     android.view.View.OnClickListener numberListener = new Button.OnClickListener() { 
		@Override
		public void onClick(View v) {
			finish(); 
		} 
	  }; 
	  buttonCancel.setOnClickListener(numberListener);
    sqlUtil = new SQLiteDBUtil(this); 
    musicFilePath = sqlUtil.get(this, Const.DB_FIELD_MUSICFILE);
    
    
    if(musicFilePath.equals("")){
    	getFileDir(rootPath,musicFilePath);
    }else{ 
    	getFileDir(musicFilePath.substring(0,musicFilePath.lastIndexOf("/")),musicFilePath);
    }  
     
  }
  
  /* 取得文件架构的method */
  private void getFileDir(String filePath,String checkFileName)
  {
    /* 设定目前所存路径 */
    mPath.setText(filePath);
    items=new ArrayList<String>();
    paths=new ArrayList<String>();
    
    File f=new File(filePath);  
    File[] files=f.listFiles();
    
    if(!filePath.equals(rootPath))
    {
      /* 第一笔设定为[并到根目录] */
      items.add("b1");
      paths.add(rootPath);
      /* 第二笔设定为[并勺层] */
      items.add("b2");
      paths.add(f.getParent());
    }
    /* 将所有文件放入ArrayList中 */
    for(int i=0;i<files.length;i++)
    {
      File file=files[i];
      if(file.canRead()&&(file.isDirectory()||file.getName().contains(".mp3"))){
    	  items.add(file.getName());
          paths.add(file.getPath());
      } 
    }
    
    /* 使用自定义的MyAdapter来将数据传入ListActivity */
    setListAdapter(new MusicSettingAdapter(this,items,paths,checkFileName));
  }
  
  
  
   
  
  /* 设定ListItem被按下时要做的操作 */
  @Override
  protected void onListItemClick(ListView l,View v,int position,long id)
  {
    File file = new File(paths.get(position));
    if(file.canRead())
    {
      if(file.isDirectory())
      {
        /* 如果是文件夹就运行getFileDir() */
        getFileDir(paths.get(position),musicFilePath);
      }
      else
      {
        /* 如果是文件调用fileHandle() */
        fileHandle(file);
      }
    }
    else
    {
      /* 弹出AlertDialog显示权限不足 */
      new AlertDialog.Builder(this)
          .setTitle("Message")
          .setMessage("权限不足!")
          .setPositiveButton("OK",
            new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface dialog,int which)
              {
              }
            }).show();
    }
  }
  
  /* 处理文件的method */
  private void fileHandle(final File file){
    /* 按下文件时的OnClickListener */
    OnClickListener builderListener=new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface dialog,int which)
      {
        if(which==Const.CHECK_THIS)
        {
        	if(handle(file)){
        		finish();
        	}
        }
        else if(which==Const.CHECK_OTHER)
        {
        	
        }
      }
    };
        
    /* 选择几个文件时，弹出要处理文件的ListDialog */
   
    new AlertDialog.Builder(MusicSettingActivity.this)  
    	.setTitle(file.getName())
        .setItems(Const.MUSIC_CHECK_ITEM,builderListener) 
        .show();
  }

protected boolean handle(File file) {
	return sqlUtil.setString( this,Const.DB_FIELD_MUSICFILE, file.getAbsolutePath());
} 
   
}

