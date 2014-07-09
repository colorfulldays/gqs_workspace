package org.antitheft.activity;

/* import���class */
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
  /* �������� 
     items�������ʾ������
     paths������ļ�·��
     rootPath����ʼĿ¼
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
    /* ����main.xml Layout */
    setContentView(R.layout.music);
    /* ��ʼ��mPath��������ʾĿǰ·�� */
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
  
  /* ȡ���ļ��ܹ���method */
  private void getFileDir(String filePath,String checkFileName)
  {
    /* �趨Ŀǰ����·�� */
    mPath.setText(filePath);
    items=new ArrayList<String>();
    paths=new ArrayList<String>();
    
    File f=new File(filePath);  
    File[] files=f.listFiles();
    
    if(!filePath.equals(rootPath))
    {
      /* ��һ���趨Ϊ[������Ŀ¼] */
      items.add("b1");
      paths.add(rootPath);
      /* �ڶ����趨Ϊ[���ײ�] */
      items.add("b2");
      paths.add(f.getParent());
    }
    /* �������ļ�����ArrayList�� */
    for(int i=0;i<files.length;i++)
    {
      File file=files[i];
      if(file.canRead()&&(file.isDirectory()||file.getName().contains(".mp3"))){
    	  items.add(file.getName());
          paths.add(file.getPath());
      } 
    }
    
    /* ʹ���Զ����MyAdapter�������ݴ���ListActivity */
    setListAdapter(new MusicSettingAdapter(this,items,paths,checkFileName));
  }
  
  
  
   
  
  /* �趨ListItem������ʱҪ���Ĳ��� */
  @Override
  protected void onListItemClick(ListView l,View v,int position,long id)
  {
    File file = new File(paths.get(position));
    if(file.canRead())
    {
      if(file.isDirectory())
      {
        /* ������ļ��о�����getFileDir() */
        getFileDir(paths.get(position),musicFilePath);
      }
      else
      {
        /* ������ļ�����fileHandle() */
        fileHandle(file);
      }
    }
    else
    {
      /* ����AlertDialog��ʾȨ�޲��� */
      new AlertDialog.Builder(this)
          .setTitle("Message")
          .setMessage("Ȩ�޲���!")
          .setPositiveButton("OK",
            new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface dialog,int which)
              {
              }
            }).show();
    }
  }
  
  /* �����ļ���method */
  private void fileHandle(final File file){
    /* �����ļ�ʱ��OnClickListener */
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
        
    /* ѡ�񼸸��ļ�ʱ������Ҫ�����ļ���ListDialog */
   
    new AlertDialog.Builder(MusicSettingActivity.this)  
    	.setTitle(file.getName())
        .setItems(Const.MUSIC_CHECK_ITEM,builderListener) 
        .show();
  }

protected boolean handle(File file) {
	return sqlUtil.setString( this,Const.DB_FIELD_MUSICFILE, file.getAbsolutePath());
} 
   
}

