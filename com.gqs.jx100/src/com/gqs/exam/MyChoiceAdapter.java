package com.gqs.exam;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.gqs.util.Exam;

public class MyChoiceAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
private ArrayList<Exam> list;
private Context context;
AssetManager am = null;   
 

public MyChoiceAdapter(ArrayList<Exam> list,Context contex)
{
	this.context=contex;
	am = context.getAssets();  
	mInflater=LayoutInflater.from(contex);
	
	this.list=list;
}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}
	

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null)
		{
			convertView=mInflater.inflate(R.layout.question, null);
			holder=new ViewHolder();
			holder.tvQuestion=(TextView)convertView.findViewById(R.id.tvQuestion);
			holder.imageQuestion=(ImageView)convertView.findViewById(R.id.ivQuestion);
			holder.rbA=(RadioButton)convertView.findViewById(R.id.rbA);
			holder.rbB=(RadioButton)convertView.findViewById(R.id.rbB);
			holder.rbC=(RadioButton)convertView.findViewById(R.id.rbC);
			holder.rbD=(RadioButton)convertView.findViewById(R.id.rbD);
			convertView.setTag(holder);
		}
		else
		{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.tvQuestion.setText((position+1)+"°¢"+list.get(position).getQuestion());
		holder.rbA.setText("A°¢"+list.get(position).getOptionA());
		holder.rbB.setText("B°¢"+list.get(position).getOptionB());
		holder.rbC.setText("C°¢"+list.get(position).getOptionC());
		holder.rbD.setText("D°¢"+list.get(position).getOptionD());
		System.out.println("adapter...id:"+list.get(position).getId());
		System.out.println(list.get(position).isImage());
		if(list.get(position).isImage())
		{
			
			System.out.println("º”‘ÿÕº∆¨"+"xz"+list.get(position).getId()+".jpg");
			Drawable drawable=null;
			try {
				drawable = Drawable.createFromStream(am.open("pic/xz"+list.get(position).getId()+".jpg"),"src");
			} catch (IOException e) {
				System.out.println("Õº∆¨º”‘ÿ ß∞‹");
				e.printStackTrace();
			}
		   if(drawable!=null)
		   {
				holder.imageQuestion.setImageDrawable(drawable);

		   }
		}
		else
		{
			holder.imageQuestion.setImageDrawable(null);
		}
		return convertView;
	}
    static class  ViewHolder
    {
    	TextView tvQuestion;
    	ImageView imageQuestion;
    	RadioButton rbA;
    	RadioButton rbB;
    	RadioButton rbC;
    	RadioButton rbD;
    }
}
