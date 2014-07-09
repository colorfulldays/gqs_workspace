package org.antitheft.activity;

/* import相关class */
import java.io.File;
import java.util.List;

import org.antitheft.R;
import org.antitheft.R.drawable;
import org.antitheft.R.id;
import org.antitheft.R.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/* 自定义的Adapter，继承android.widget.BaseAdapter */
public class MusicSettingAdapter extends BaseAdapter
{
  /* 变量声明 
     mIcon1：并到根目录的图文件
     mIcon2：并到第几层的图片
     mIcon3：文件夹的图文件
     mIcon4：文件的图片
  */
  private LayoutInflater mInflater;
  private Bitmap mIconRoot;
  private Bitmap mIconBack;
  private Bitmap mIconFolder;
  private Bitmap mIconMp3;
  private List<String> items;
  private List<String> paths;
  private String checkFileName;
  /* MyAdapter的构造符，传入三个参数  */  
  public MusicSettingAdapter(Context context,List<String> it,List<String> pa,String checkFileName)
  {
    /* 参数初始化 */
	  this.checkFileName = checkFileName;
    mInflater = LayoutInflater.from(context);
    items = it;
    paths = pa;
    mIconRoot = BitmapFactory.decodeResource(context.getResources(),R.drawable.back01);
    mIconBack = BitmapFactory.decodeResource(context.getResources(),
                                          R.drawable.back02);
    mIconFolder = BitmapFactory.decodeResource(context.getResources(),
                                          R.drawable.folder);
    mIconMp3 = BitmapFactory.decodeResource(context.getResources(),
                                          R.drawable.doc);
  }
  
  /* 继承BaseAdapter，需重写method */
  @Override
  public int getCount()
  {
    return items.size();
  }

  @Override
  public Object getItem(int position)
  {
    return items.get(position);
  }
  
  @Override
  public long getItemId(int position)
  {
    return position;
  }
  
  
  
  @Override
  public View getView(int position,View convertView,ViewGroup parent)
  {
    ViewHolder holder;
    Log.e("", position +"");
  //  if(convertView == null)
    {
      /* 使用告定义的file_row作为Layout */
      convertView = mInflater.inflate(R.layout.music_file_row, null);
      /* 初始化holder的text与icon */
      holder = new ViewHolder();
      holder.text = (TextView) convertView.findViewById(R.id.rowtext);
      holder.icon = (ImageView) convertView.findViewById(R.id.icon);
      
      convertView.setTag(holder);
    }
    /*else
    {
      holder = (ViewHolder) convertView.getTag();
    }*/

    File f=new File(paths.get(position).toString());
    /* 设定[并到根目录]的文字与icon */
    if(items.get(position).toString().equals("b1"))
    {
      holder.text.setText("根目录..");
      holder.icon.setImageBitmap(mIconRoot);
    }
    /* 设定[并到第几层]的文字与icon */
    else if(items.get(position).toString().equals("b2"))
    {
      holder.text.setText("返回上一级..");
      holder.icon.setImageBitmap(mIconBack);
    }
    /* 设定[文件或文件夹]的文字与icon */
    else
    {
      holder.text.setText(f.getName());  
      if(f.isDirectory())
      {
        holder.icon.setImageBitmap(mIconFolder);
      }
      else
      {
        holder.icon.setImageBitmap(mIconMp3); 
        if(f.getAbsolutePath().trim().equals(checkFileName.trim())){ 
        	Log.i("", position+f.getAbsolutePath());
        	  holder.text.setTextColor(Color.BLUE);
          }
        
      }
    }
    return convertView;
  }
  
  /* class ViewHolder */
  private class ViewHolder
  {
    TextView text;
    ImageView icon;
  }
}

