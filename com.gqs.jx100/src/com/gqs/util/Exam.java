package com.gqs.util;

import com.gqs.Const.Const;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class Exam implements OnCheckedChangeListener{
//题目
	
private String question;
private String id;
private String pic;

public ExamWidget examWidget;
private Context context;
public Exam(Context context)
{
	this.context=context;
	examWidget=new ExamWidget(this.context);
	examWidget.rbA.setOnCheckedChangeListener(this);
	examWidget.rbB.setOnCheckedChangeListener(this);
	examWidget.rbC.setOnCheckedChangeListener(this);
	examWidget.rbD.setOnCheckedChangeListener(this);
}
public String getPic() {
	return pic;
}
public void setPic(String pic) {
	this.pic = pic;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
//选项
private String optionA;
private String optionB;
private String optionC;
private String optionD;
//正确答案
private String answer;
//题目类型
private int type;
//是否有图片
private boolean isImage;

private int result;
private int state;

public String getQuestion() {
	return question;
}
public void setQuestion(String question) {
	this.question = question;
}
public String getOptionA() {
	return optionA;
}
public void setOptionA(String optionA) {
	this.optionA = optionA;
}
public String getOptionB() {
	return optionB;
}
public void setOptionB(String optionB) {
	this.optionB = optionB;
}
public String getOptionC() {
	return optionC;
}
public void setOptionC(String optionC) {
	this.optionC = optionC;
}
public String getOptionD() {
	return optionD;
}
public void setOptionD(String optionD) {
	this.optionD = optionD;
}
public String getAnswer() {
	return answer;
}
public void setAnswer(String answer) {
	this.answer = answer;
}
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
public boolean isImage() {
	return isImage;
}
public void setImage(boolean isImage) {
	this.isImage = isImage;
}

@Override
public void onCheckedChanged(CompoundButton radioButton, boolean isChecked) {
	System.out.println("RadioButton是否选中...."+isChecked);
	if(!isChecked)
	{
		System.out.println("返回");
		return;
	}
	System.out.println("答案:"+answer);
	state=Const.CHECKED;
	int id=radioButton.getId();
	if(id==examWidget.rbA.getId())
	{
		System.out.println("选中A");
		Const.choice_selected="A";
		Const.judge_selected=0;
	}
	else if(id==examWidget.rbB.getId())
	{
		System.out.println("选中B");
		Const.choice_selected="B";
		Const.judge_selected=1;
	}
	else if(id==examWidget.rbC.getId())
	{
		System.out.println("选中C");
		Const.choice_selected="C";
	}
	else if(id==examWidget.rbD.getId())
	{
		System.out.println("选中D");
		Const.choice_selected="D";
	}
    if(type==Const.CHOICE&&answer.equals(Const.choice_selected))
    {
    	System.out.println("选择....."+Const.choice_selected);
    	result=Const.RIGHT;
    	Const.countRight++;
    }
    else if(type==Const.JUDGE&&answer.equals(Const.judge_selected))
    {
    	result=Const.RIGHT;
    	Const.countRight++;
    }
    else
    {
    	
    	if(result==Const.RIGHT)
    	{
    		System.out.println("错误..."+state);
    		result=Const.WRONG;
        	Const.countRight--;

    	}
    }
		
}

}
