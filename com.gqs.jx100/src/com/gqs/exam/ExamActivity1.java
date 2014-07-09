package com.gqs.exam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.gqs.Const.Const;
import com.gqs.util.Exam;

public class ExamActivity1 extends Activity implements OnGestureListener{
	 private ArrayList<Exam> listChoice;
	    private ArrayList<Exam> listJudge;
//		private MyChoiceAdapter choiceAdapter;
		private Handler handler;
		private int minute=45;
		private int second=0;
		private Button btnSubmit;
		private TextView tvTime;
		private ViewFlipper flipper;  
		private GestureDetector detector;

		private LayoutInflater inflater;
		AssetManager am = null;
		private static final int FLING_MIN_DISTANCE = 70;  
		private RadioButton rbA;
		private RadioButton rbB;
		private RadioButton rbC;
		private RadioButton rbD;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exam);
		init();
//		new Thread(new LoadExam()).start();
		setExam(listChoice);
		setExam(listJudge);
	    btnSubmit.setOnClickListener(new OnClickListener()
	    {

			@Override
			public void onClick(View arg0) {
			
			
				Toast.makeText(getApplicationContext(), Const.countRight+"", Toast.LENGTH_SHORT).show();
			}
	    
	    });
//		listViewChoice.setAdapter(choiceAdapter);
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
		inflater=LayoutInflater.from(getApplicationContext());
		flipper=(ViewFlipper)findViewById(R.id.ViewFlipper);
		am=getAssets();

		detector = new GestureDetector(this);
		tvTime=(TextView)findViewById(R.id.tvTime);
		btnSubmit=(Button)findViewById(R.id.btnSubmit);
		listChoice=getExam(Const.listChoice,60);
		listJudge=getExam(Const.listJudge,40);

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
		new AlertDialog.Builder(ExamActivity1.this).setMessage("您确定要退出考试吗？")
		.setNegativeButton("继续考试", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Const.isDoing=false;
				Const.countRight=0;
				ExamActivity1.this.finish();
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
	private void setExam(ArrayList<Exam> list)
	{
		Drawable drawable=null;
		for(int i=0;i<list.size();i++)
		{
			list.get(i).examWidget.tvQuestion.setText((i+1)+"、"+list.get(i).getQuestion());
			if(list.get(i).getType()==Const.CHOICE)
			{
				list.get(i).examWidget.rbA.setText("A、"+list.get(i).getOptionA());
				list.get(i).examWidget.rbB.setText("B、"+list.get(i).getOptionB());
				list.get(i).examWidget.rbC.setText("C、"+list.get(i).getOptionC());
				list.get(i).examWidget.rbD.setText("D、"+list.get(i).getOptionD());
			}
			else
			{
				list.get(i).examWidget.rbA.setText(" 正确");
				list.get(i).examWidget.rbB.setText(" 错误");
				list.get(i).examWidget.rbC.setVisibility(View.GONE);
				list.get(i).examWidget.rbD.setVisibility(View.GONE);
			}
			if(list.get(i).isImage())
			{
				
				System.out.println("加载图片"+"xz"+list.get(i).getId()+".jpg");
				
				try {
					if(list.get(i).getType()==Const.CHOICE)
					{
						drawable = Drawable.createFromStream(am.open("pic/xz"+list.get(i).getId()+".jpg"),"src");

					}
					else
					{
						drawable = Drawable.createFromStream(am.open("pic/pd"+list.get(i).getId()+".jpg"),"src");

					}
				} catch (IOException e) {
					System.out.println("图片加载失败"+e.toString());
					e.printStackTrace();
				}
			   if(drawable!=null)
			   {
				   list.get(i).examWidget.ivQuestion.setImageDrawable(drawable);

			   }
			}
			else
			{
				list.get(i).examWidget.ivQuestion.setImageDrawable(null);
			}
			flipper.addView(list.get(i).examWidget.linearLayout);
		}

	}

	
	public class LoadExam extends AsyncTask
	{

		@Override
		protected Object doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		
		
	}
}
