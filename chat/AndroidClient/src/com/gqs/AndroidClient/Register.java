package com.gqs.AndroidClient;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
	private Button btnConfirm;
	private Button btnCancel;
	private EditText etUser;
	private EditText etPWD;
	Handler childHanler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registerlayout);
		g.handler = new Handler() {
			public void handleMessage(Message msg) {
				System.out.println("handler");
				if (msg.obj.toString().equals("yes")) {
					Toast.makeText(getApplicationContext(), "注册成功",
							Toast.LENGTH_SHORT).show();
					Intent i = new Intent(getApplicationContext(), Login.class);
					startActivity(i);
					finish();
				} else if (msg.obj.toString().equals("no")) {
					Toast.makeText(getApplicationContext(), "该用户已存在，请重新输入",
							Toast.LENGTH_SHORT).show();
					etUser.setText("");
					etPWD.setText("");
				} else {
					Toast.makeText(getApplicationContext(), msg.obj.toString(),
							Toast.LENGTH_SHORT).show();
				}
			}
		};
		init();
		btnConfirm.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (etUser.getText().toString().equals("")
						|| etPWD.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "用户名和密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				g.pw.println(g.REQUEST_REGISTER+"," + etUser.getText().toString() + ","
						+ etPWD.getText().toString());
				g.pw.flush();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Login.class);
				startActivity(i);
			}
		});
	}
    public void init()
    {
    	btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		etUser = (EditText) findViewById(R.id.etRUser);
		etPWD = (EditText) findViewById(R.id.etRPWD);
    }
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		if (g.pw != null) {
//			g.pw.println("quit");
//			g.pw.flush();
//			g.pw.close();
//		}
//		if (g.socket != null) {
//			try {
//				g.socket.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		super.onDestroy();
//	}
}
