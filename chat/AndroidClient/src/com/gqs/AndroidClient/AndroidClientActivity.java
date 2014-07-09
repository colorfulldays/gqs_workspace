package com.gqs.AndroidClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.sax.Element;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidClientActivity extends Activity {
	private EditText etMsg;
	private Button btnSend;
	private Button btnClear;
	private EditText etChat;
	Handler childHanler;
	Handler handler;
	private TextView addImage;
	private String password;
	private String username = null;
	private boolean flag = true;
	private ArrayAdapter<String> adapter;
	private String talker = "all";
	private Button btnExit;
	private StringBuffer msg;
	private Spinner sp;
	private TextView tvTalker;
	private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 初始化
		init();
		/**
		 * 将表情添加到聊天输入框
		 */
		addImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showImageDialog();
			}
		});
		sp.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				talker = (String) parent.getAdapter().getItem(position);
				tvTalker.setText("To:" + talker);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, g.users);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setPrompt("用户列表");
		sp.setAdapter(adapter);
		btnClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				etMsg.setText("");
			}
		});
		btnSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (etMsg.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "发送信息为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				g.faceContent = etMsg.getText().toString();
				if("all".equals(talker))
				{
					msg.append(g.REQUEST_TALKALL + "," + username + "," + talker
							+ "," + g.faceContent);
				}
				else
				{
					msg.append(g.REQUEST_TALKPRIVATE + "," + username + "," + talker
							+ "," + g.faceContent);
				}
				if (g.pw != null) {
					g.pw.println(msg.toString());
					g.pw.flush();
					etMsg.setText("");
					msg.delete(0, msg.length());
				}
			}
		});
		// 退出程序
		btnExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "quit",
						Toast.LENGTH_SHORT).show();
				g.pw.println("quit");
				g.pw.flush();
				try {
					if(g.pw != null) {
						g.pw.close();
					}
					g.br.close();
					g.socket.close();
					g.socket=null;
					g.users=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				AndroidClientActivity.this.finish();
				Intent intent=new Intent(getApplicationContext(),Login.class);
				startActivity(intent);
			}

		});
		g.handler = new Handler() {
			public void handleMessage(Message msg) {
				String str = (String) msg.obj;
				String[] s = str.split(":");
				System.out.println("user"+s[0]);
				if (s[0].equals("users")) {
//					g.users = s[1].split(",");	
					System.out.println("add:"+s[1]);
					if (!g.users.contains(s[1])) {
					
						g.users.add(s[1]);
					}
					adapter.notifyDataSetChanged();
				}
				else if("removeUser".equals(s[0])) {
					System.out.println("remove:"+s[1]);
					if (g.users.contains(s[1])) {
						
						g.users.remove(s[1]);
					}
					adapter.notifyDataSetChanged();
				}
				else {
					Log.i("client", "handleMessage:" + str);
					Toast.makeText(getApplicationContext(), s[0],
							Toast.LENGTH_SHORT).show();
					etChat.append(s[0] + ":" + s[1] + "\n");
				}
			}
		};
	}
	public void init() {
		msg=new StringBuffer();
		etChat = (EditText) findViewById(R.id.chatPanel);
		etMsg = (EditText) findViewById(R.id.etMessage);
		btnSend = (Button) findViewById(R.id.btnSend);
		btnClear = (Button) findViewById(R.id.btnClear);
		btnExit = (Button) findViewById(R.id.btnExit);
		tvTalker = (TextView) findViewById(R.id.tvTalker);
		addImage = (TextView) findViewById(R.id.addFace);
		sp = (Spinner) findViewById(R.id.spinner);
		for (int i = 0; i < g.images.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("face", g.images[i]);
			list.add(map);
		}
		// 将表情图片添加到map中
		g.addFace();
		// 获取用户名和密码
		Intent intent = AndroidClientActivity.this.getIntent();
		username = intent.getStringExtra("name");
		password = intent.getStringExtra("pwd");
		g.users.add("all");
	}
	// 当Activity销毁时，关闭连接
	protected void onDestroy() {
		g.pw.println("quit");
		g.pw.flush();
		flag = false;
		try {
			if (g.pw != null&&g.socket!=null&&g.br!=null) {
				g.pw.close();
				g.br.close();
				g.socket.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onDestroy();
	}

	// 显示表情框
	public void showImageDialog() {
		LayoutInflater inflater = LayoutInflater
				.from(AndroidClientActivity.this);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.dialoglayout, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(
				AndroidClientActivity.this);
		GridView gv = (GridView) layout.findViewById(R.id.gridview);
		g.simpleAdapter = new SimpleAdapter(AndroidClientActivity.this, list,
				R.layout.itemlayout, new String[] { "face" },
				new int[] { R.id.itemImageview });
		gv.setAdapter(g.simpleAdapter);
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				@SuppressWarnings("unchecked")
				HashMap<String, Object> m = (HashMap<String, Object>) parent
						.getAdapter().getItem(position);
				CharSequence cs = Html.fromHtml("<img src='" + m.get("face")
						+ "'/>", imageGetter, null);
				int cursor = etMsg.getSelectionStart();
				etMsg.getText().insert(cursor, cs);
			}
		});
		builder.setTitle("选择表情")
				.setMessage("要添加的表情")
				.setView(layout)
				.setPositiveButton("确 定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}

						}).show();
	}

	ImageGetter imageGetter = new ImageGetter() {
		@Override
		public Drawable getDrawable(String source) {
			int id = Integer.parseInt(source);
			// 根据id从资源文件中获取图片对象
			Drawable d = getResources().getDrawable(id);
			d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			return d;
		}
	};

}