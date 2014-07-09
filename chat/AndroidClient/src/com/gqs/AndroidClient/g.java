package com.gqs.AndroidClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Handler;
import android.widget.SimpleAdapter;

public class g {
	final static String ADDRESS = "10.0.2.2";
	final static int PORT = 54321;
	final static String REQUEST_REGISTER="0";
	final static String REQUEST_LOGIN="1";
	final static String REQUEST_TALKALL="2";
	final static String REQUEST_TALKPRIVATE="3";
	static Socket socket;
	static BufferedReader br;
	static PrintWriter pw;
	static SimpleAdapter simpleAdapter;
	static Connect con;
	static Handler handler;
	static List<String> users=new ArrayList<String>();
//	static String[] users=new String[0];
	static int[] images = new int[] { R.drawable.image1, R.drawable.image2,
			R.drawable.image3, R.drawable.image4, R.drawable.image5,
			R.drawable.image6, R.drawable.image7, R.drawable.image8,
			R.drawable.image9, R.drawable.image10, R.drawable.image11,
			R.drawable.image12, R.drawable.image13, R.drawable.image14,
			R.drawable.image15 };
	static String faceContent;
	static HashMap<Integer, Object> faceMap = new HashMap<Integer, Object>();
	// 连接服务器
	public static void connect() {
		try {
			socket = new Socket(g.ADDRESS, g.PORT);
			br = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "utf-8"));
			// Socket只能获取一次流文件
			pw = new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static String FilterHtml(String str) {
		str = str.replaceAll("<(?!br|img)[^>]+>", "").trim();
		return g.UnicodeToGBK2(str);
	}
	public static String UnicodeToGBK2(String s) {
		String[] k = s.split(";");
		String rs = "";

		for (int i = 0; i < k.length; i++) {
			int strIndex = k[i].indexOf("&#");
			String newstr = k[i];
			if (strIndex > -1) {
				String kstr = "";
				if (strIndex > 0) {
					kstr = newstr.substring(0, strIndex);
					rs += kstr;
					newstr = newstr.substring(strIndex);
				}
				int m = Integer.parseInt(newstr.replace("&#", ""));
				char c = (char) m;
				rs += c;
			} else {
				rs += k;
			}
		}
		return rs;
	}

	// 添加表情
	public static void addFace() {
		faceMap.put(images[0], "[smile15]");
		faceMap.put(images[1], "[smile1]");
		faceMap.put(images[2], "[smile2]");
		faceMap.put(images[3], "[smile3]");
		faceMap.put(images[4], "[smile4]");
		faceMap.put(images[5], "[smile5]");
		faceMap.put(images[6], "[smile6]");
		faceMap.put(images[7], "[smile7]");
		faceMap.put(images[8], "[smile8]");
		faceMap.put(images[9], "[smile9]");
		faceMap.put(images[10], "[smile10]");
		faceMap.put(images[11], "[smile11]");
		faceMap.put(images[12], "[smile12]");
		faceMap.put(images[13], "[smile13]");
		faceMap.put(images[14], "[smile14]");
	}
	public static List<String> getUsers(String s)
	{
		List<String> list=new ArrayList<String>();
		String[] users=s.split(",");
		for(int i=0;i<users.length;i++)
		{
			list.add(users[i]);
		}
		return list;
	}
}
