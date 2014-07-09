package com.gqs.exam;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.gqs.Const.Const;

public class MainActivity extends Activity{
	private ImageView imageBtnBegin;
	private ImageView imageBtnShow;
	private ImageView imageBtnAbout;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.main);
	init();
	imageBtnBegin.setOnClickListener(new OnClickListener()
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Const.isDoing=true;
			Intent intent=new Intent(MainActivity.this,ExamActivity1.class);
		    startActivity(intent);
		    MainActivity.this.finish();
		}
		
	}
	);
	
	
}
public void init()
{
	imageBtnBegin=(ImageView)findViewById(R.id.imageBtnBegin);
	imageBtnShow=(ImageView)findViewById(R.id.imageBtnShow);
	imageBtnAbout=(ImageView)findViewById(R.id.imageBtnShow);
}
}
