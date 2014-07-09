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

public class Login extends Activity {
	private Button btnLogin;
	private Button btnRegister;
	private EditText etUser;
	private EditText etPWD;
	private Thread thread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginlayout);
		init();
		g.handler=new Handler()
		{
			public void handleMessage(Message msg) {
				
				String str=msg.obj.toString();
				String[] s = str.split(":");
				System.out.println("login"+msg.obj.toString());
				if("users".equals(s[0]))
				{
					if (!g.users.contains(s[1])) {
						
						g.users.add(s[1]);
					}
				}
				  else if (msg.obj.toString().equals("no")) {
					Toast.makeText(getApplicationContext(), "用户名或密码不正确",
							Toast.LENGTH_SHORT).show();
					etUser.setText("");
					etPWD.setText("");
					return;
				} else if("yes".equals(msg.obj.toString())){
					Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT)
					.show();
			       Intent i = new Intent(getApplicationContext(),
					AndroidClientActivity.class);
			       i.putExtra("name", etUser.getText().toString());
			      i.putExtra("pwd", etPWD.getText().toString());
			       startActivity(i);
				}
			};
		};
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				connectServer();
				if (etUser.getText().toString().equals("")
						|| etPWD.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "用户名和密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (g.pw != null) {
					g.pw.println(g.REQUEST_LOGIN+"," + etUser.getText().toString() + ","
							+ etPWD.getText().toString());
					g.pw.flush();
				}
			}
		});
		btnRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				connectServer();
				Intent i = new Intent(getApplicationContext(), Register.class);
				startActivity(i);
			}
		});
	}
	public void init() {
		etUser = (EditText) findViewById(R.id.etUser);
		etPWD = (EditText) findViewById(R.id.etPWD);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		
	}
	public void beginRead()
	{
		g.con = new Connect();
		thread=new Thread(g.con);
		thread.start();
	}
	public void connectServer() {
		// 连接服务器
		if (g.socket==null) {
			g.connect();
			if(thread==null)
			{
				beginRead();
			}
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (g.pw != null) {
			g.pw.println("quit");
			g.pw.flush();
			g.pw.close();
		}
		if (g.socket != null) {
			try {
				g.socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.onDestroy();
	}

}
