package com.gqs.util;

import com.gqs.exam.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class ExamWidget {
public LinearLayout linearLayout;
public RadioButton rbA;
public RadioButton rbB;
public RadioButton rbC;
public RadioButton rbD;
public ImageView ivQuestion;
public TextView tvQuestion;
private LayoutInflater inflater;
private Context context;
public ExamWidget(Context context)
{
	this.context=context;
	inflater=LayoutInflater.from(context);
	linearLayout=(LinearLayout)inflater.inflate(R.layout.question, null);
	rbA=(RadioButton)linearLayout.findViewById(R.id.rbA);
	rbB=(RadioButton)linearLayout.findViewById(R.id.rbB);
	rbC=(RadioButton)linearLayout.findViewById(R.id.rbC);
	rbD=(RadioButton)linearLayout.findViewById(R.id.rbD);
    ivQuestion=(ImageView)linearLayout.findViewById(R.id.ivQuestion);
    tvQuestion=(TextView)linearLayout.findViewById(R.id.tvQuestion);
    


}
}
