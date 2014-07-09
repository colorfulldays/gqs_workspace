package com.gqs.Database;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gqs.Const.Const;
import com.gqs.util.Exam;
import com.gqs.util.FileUtils;

public class DatabaseHelper {
	private SQLiteDatabase db;
    private Context context;
    
	public DatabaseHelper(Context context) {
		this.context=context;
		System.out.println("开启数据库"+FileUtils.getPath()
				+ Const.FOLDER_NAME + "/" + Const.DB_FILE_NAME);
		db = SQLiteDatabase.openDatabase(FileUtils.getPath()
				+ Const.FOLDER_NAME + "/" + Const.DB_FILE_NAME, null, SQLiteDatabase.OPEN_READONLY);
		
	}

	// 关闭数据库
	public void close() {
		if (db != null) {
			// dbHelper.close();
			db.close();
		}
	}

	public ArrayList<Exam> getQuetion(String table) {
		ArrayList<Exam> list = new ArrayList<Exam>();
		// Cursor result=db.Query("SELECT ID, name, inventory FROM mytable");
		Cursor result = db.rawQuery("select * from " + table, null);
		result.moveToFirst();
		if (table.equals(Const.CHOICE_TABLE)) {
			

			while (!result.isAfterLast()) {
				Exam exam = new Exam(context);
				exam.setType(Const.CHOICE);
				int id = result.getInt(0);
				System.out.println("DatabaseHelper....id:"+id);
				exam.setId(id + "");
				String answer = result.getString(8);
				exam.setAnswer(answer);
				String a = result.getString(4);
				String b = result.getString(5);
				String c = result.getString(6);
				String d = result.getString(7);
				exam.setOptionA(a);
				exam.setOptionB(b);
				exam.setOptionC(c);
				exam.setOptionD(d);
				String question = result.getString(2);
				System.out.println("DatabaseHelper....question:"+question);
				exam.setQuestion(question);
				String pic = result.getString(3);
				System.out.println("DatabaseHelper....pic:"+pic);
				if ("".equals(pic.trim())||pic==null) {
					exam.setImage(false);

				} else {
					exam.setImage(true);
					exam.setPic("http://www.tjttjx.com/" + pic);
				}

				list.add(exam);

				result.moveToNext();
			}
		} else {
			while (!result.isAfterLast()) {
				Exam exam = new Exam(context);
				exam.setType(Const.JUDGE);
				int id = result.getInt(0);
				exam.setId(id + "");
				String answer = result.getString(4);
				exam.setAnswer(answer);

				String question = result.getString(2);
				exam.setQuestion(question);
				String pic = result.getString(3);
				System.out.println("id:"+id+"pic..."+pic);
				if ("".equals(pic.trim())||pic==null) {
					exam.setImage(false);

				} else {
					exam.setImage(true);
					exam.setPic("http://www.tjttjx.com/" + pic);
				}
				list.add(exam);
				result.moveToNext();
			}
		}
		
		result.close();
//		close();
		return list;

	}

}
