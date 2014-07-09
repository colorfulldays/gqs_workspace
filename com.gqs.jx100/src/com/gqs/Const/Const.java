package com.gqs.Const;

import java.util.ArrayList;

import com.gqs.util.Exam;

public class Const {
	//数据库
public static final String DBNAME="exam_system";
public static final String TABLE_QUESTION="question";

//文件夹及文件名称
public static final String FOLDER_NAME="jx100";
public static final String DB_FILE_NAME="jiaxiao100.db";
//存储卡路径
public static String path;

//问题类型
public static final String CHOICE_TABLE="choice";
public static final String JUDGE_TABLE="judge";
public static final int CHOICE=0;
public static final int JUDGE=1;
//考试状态
public static  Boolean isDoing=false;

//题目容器
public static ArrayList<Exam> listChoice;
public static ArrayList<Exam> listJudge;

public static final int WRONG=0;
public static final int RIGHT=1;
public static int countWrong;
public static int countRight;

public static String choice_selected;
public static int judge_selected;

//试题状态
public static final int CHECKED=1;
public static final int UNCHECKED=0;
}
