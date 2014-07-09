package org.antitheft.activity;

/* import���class */
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

/* �Զ����Adapter���̳�android.widget.BaseAdapter */
public class MusicSettingAdapter extends BaseAdapter
{
  /* �������� 
     mIcon1��������Ŀ¼��ͼ�ļ�
     mIcon2�������ڼ����ͼƬ
     mIcon3���ļ��е�ͼ�ļ�
     mIcon4���ļ���ͼƬ
  */
  private LayoutInflater mInflater;
  private Bitmap mIconRoot;
  private Bitmap mIconBack;
  private Bitmap mIconFolder;
  private Bitmap mIconMp3;
  private List<String> items;
  private List<String> paths;
  private String checkFileName;
  /* MyAdapter�Ĺ������������������  */  
  public MusicSettingAdapter(Context context,List<String> it,List<String> pa,String checkFileName)
  {
    /* ������ʼ�� */
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
  
  /* �̳�BaseAdapter������дmethod */
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
      /* ʹ�ø涨���file_row��ΪLayout */
      convertView = mInflater.inflate(R.layout.music_file_row, null);
      /* ��ʼ��holder��text��icon */
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
    /* �趨[������Ŀ¼]��������icon */
    if(items.get(position).toString().equals("b1"))
    {
      holder.text.setText("��Ŀ¼..");
      holder.icon.setImageBitmap(mIconRoot);
    }
    /* �趨[�����ڼ���]��������icon */
    else if(items.get(position).toString().equals("b2"))
    {
      holder.text.setText("������һ��..");
      holder.icon.setImageBitmap(mIconBack);
    }
    /* �趨[�ļ����ļ���]��������icon */
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

