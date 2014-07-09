package com.gqs.exam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.gqs.Const.Const;
import com.gqs.util.Exam;

public class ExamActivity extends Activity implements OnGestureListener{
	
	
    private ArrayList<Exam> listChoice;
    private ArrayList<Exam> listJudge;
	private MyChoiceAdapter choiceAdapter;
	private Handler handler;
	private int minute=45;
	private int second=0;
	private Button btnSubmit;
	private TextView tvTime;
	private ViewFlipper flipper;  
	private GestureDetector detector;
	private TextView tv_present;
	private ImageView iv_present;
	private RadioButton rbA_present;
	private RadioButton rbB_present;
	private RadioButton rbC_present;
	private RadioButton rbD_present;
	
	private TextView tv_next;
	private ImageView iv_next;
	private RadioButton rbA_next;
	private RadioButton rbB_next;
	private RadioButton rbC_next;
	private RadioButton rbD_next;
	
	private TextView tv_preview;
	private ImageView iv_preview;
	private RadioButton rbA_preview;
	private RadioButton rbB_preview;
	private RadioButton rbC_preview;
	private RadioButton rbD_preview;
	
	private int present;
	private int next;
	private int preview;
	AssetManager am = null;
	private static final int FLING_MIN_DISTANCE = 100;  
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.exam);
	init();
	
    btnSubmit.setOnClickListener(new OnClickListener()
    {

		@Override
		public void onClick(View arg0) {
		
		
			
		}
    
    });
//	listViewChoice.setAdapter(choiceAdapter);
	handler=new Handler()
	{
		public void handleMessage(Message msg)
		{
			tvTime.setText(minute+" :"+second);
		}
	};
	new Thread(new UpdateTime()).start();
}
public void init()
{
	flipper=(ViewFlipper)findViewById(R.id.ViewFlipper);
	am=getAssets();

	

	

	detector = new GestureDetector(this);
	tvTime=(TextView)findViewById(R.id.tvTime);
	btnSubmit=(Button)findViewById(R.id.btnSubmit);
	listChoice=getExam(Const.listChoice,60);
	listJudge=getExam(Const.listJudge,40);
	choiceAdapter=new MyChoiceAdapter(listChoice,this);
	
	present=0;
	next=1;
	preview=0;
	setExam(0,present, listChoice);
	setExam(0,preview, listChoice);
	setExam(1,next, listChoice);

}
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	if(keyCode==KeyEvent.KEYCODE_BACK)
	{
		showDialog();
	}
	return true;
}
//显示提示对话框
public void showDialog()
{
	new AlertDialog.Builder(ExamActivity.this).setMessage("您确定要退出考试吗？")
	.setNegativeButton("继续考试", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
	}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			Const.isDoing=false;
			ExamActivity.this.finish();
		}
	}).show();
}
private class UpdateTime implements Runnable
{

	@Override
	public void run() {
		while(Const.isDoing)
		{
			if(minute<=0)
			{
				break;
			}
			if(second<=0)
			{
				second=60;
				minute--;
			}
			second--;
			Message msg=handler.obtainMessage();
			msg.arg1=minute;
			msg.arg2=second;
			handler.sendMessage(msg);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Const.isDoing=false;
	}
	
}
//随即取得试题
public ArrayList<Exam> getExam(ArrayList<Exam> srcList,int getCount)
{
	Random random=new Random();
	ArrayList<Exam> getList=new ArrayList<Exam>();
	int count=srcList.size();
	int []indexs=new int[count];
	for(int i=0;i<count;i++)
	{
	    indexs[i]=i;
	}
	int [] exams=new int[getCount];
	int i=0;
	int temp=0;
    while(i<getCount)
    {
       temp=random.nextInt(count);
       exams[i]=indexs[temp];
       indexs[temp]=indexs[count-1];
       count--;
       i++;
    }
    for(int n=0;n<getCount;n++)
    {
    	getList.add(srcList.get(exams[n]));
    }
    return getList;
}
@Override
public boolean onDown(MotionEvent e) {
	// TODO Auto-generated method stub
	return false;
}
@Override
public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
		float velocityY) {
	 if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE) {  
         //设置View进入和退出的动画效果  
         this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,  
                 R.anim.left_in));  
         this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,  
                 R.anim.left_out));  
         this.flipper.showNext();  
         return true;  
     }  
     if (e1.getX() - e2.getX() < -FLING_MIN_DISTANCE) {  
         this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,  
                 R.anim.right_in));  
         this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,  
                 R.anim.right_out));  
         this.flipper.showPrevious();  
         return true;  
     } 
	return false;
}
@Override
public void onLongPress(MotionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
		float distanceY) {
	// TODO Auto-generated method stub
	return false;
}
@Override
public void onShowPress(MotionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public boolean onSingleTapUp(MotionEvent e) {
	// TODO Auto-generated method stub
	return false;
}
@Override
public boolean onTouchEvent(MotionEvent event) {
	// TODO Auto-generated method stub
	  return this.detector.onTouchEvent(event); 
}
private void setExam(int question,int index,ArrayList<Exam> list)
{
	switch(index)
	{
	case 0:
		tv_preview.setText(listChoice.get(question).getQuestion());
		rbA_preview.setText("A、"+list.get(question).getOptionA());
		rbB_preview.setText("B、"+list.get(question).getOptionB());
		rbC_preview.setText("C、"+list.get(question).getOptionC());
		rbD_preview.setText("D、"+list.get(question).getOptionD());
		if(list.get(question).isImage())
		{
			try {
				iv_preview.setImageDrawable(Drawable.createFromStream(am.open("pic/xz"+list.get(question).getId()+".jpg"),"src"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		else
		{
			iv_preview.setImageDrawable(null);
		}
	case 1:
		tv_present.setText(listChoice.get(question).getQuestion());
		rbA_present.setText("A、"+list.get(question).getOptionA());
		rbB_present.setText("B、"+list.get(question).getOptionB());
		rbC_present.setText("C、"+list.get(question).getOptionC());
		rbD_present.setText("D、"+list.get(question).getOptionD());
		if(list.get(question).isImage())
		{
			try {
				iv_present.setImageDrawable(Drawable.createFromStream(am.open("pic/xz"+list.get(question).getId()+".jpg"),"src"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		else
		{
			iv_present.setImageDrawable(null);
		}
	}
}
}
