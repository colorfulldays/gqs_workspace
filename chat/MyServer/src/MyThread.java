import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyThread implements Runnable {
	Socket socket = null;
	BufferedReader br = null;
	PrintWriter pw;
	private User user;
	private boolean flag = true;
	private int request;
	public int state;
	private boolean isSendName = false;
	public static StringBuffer users;
	StringBuffer sb = new StringBuffer();

	public MyThread(Socket s) {
		try {
			this.socket = s;
			users = new StringBuffer();
			state = g.SATE_OFFLINE;
			br = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "utf-8"));
			pw = new PrintWriter(s.getOutputStream(), true);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public User getUser() {
		return user;
	}

	public void write(String msg) throws IOException {
		pw.println(msg);
		pw.flush();
	}

	@Override
	public void run() {

		while (flag) {
			sb.delete(0, sb.length());
			users.delete(0, users.length());
			String msg;
			msg = readFromClient();
			if (msg == null || msg.equals("")) {
				continue;
			}
			if (msg != null && msg.equals("quit")) {
				System.out.println("退出");
				state = g.SATE_OFFLINE;
				g.userList.remove(user.getName());
				break;
			}
			String[] command = msg.split(",");
			try {
				request = Integer.valueOf(command[0]);

			} catch (Exception e) {
				try {
					close();
					return;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			switch (request) {
			case g.REQUEST_REGISTER:
				user = new User(command[1], command[2]);
				try {
					if (!register(user)) {
						msg = "no";
					} else {
						msg = "yes";
					}
					this.write(msg);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case g.REQUEST_LOGIN:
				user = new User(command[1], command[2]);
				try {
					if (g.ds.isLogin(user)) {
						msg = "yes";
						isSendName = true;
						System.out.println("login:" + user.getName());
						if (!g.userList.contains(user.getName())) {
							g.userList.add(user.getName());
						}
						state = g.STATE_ONLINE;
					} else {
						msg = "no";
					}
					write(msg);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case g.REQUEST_TALK_PRIVATE:
				msg = command[1] + ":" +"悄悄de说，"+ command[3];
				try {
					write(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				g.sendPrivate(msg, command[2]);
				break;
			case g.REQUEST_TALK_ALL:
				msg = command[1] + ":" +"对大家说," +command[3];
				g.sendAll(msg);
				break;
			}
			if (isSendName) {
				g.sendUser();
				isSendName = false;
			}
		}
		try {
			g.sendUser(user.getName());
			close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("服务器发送信息出错");
		}

	}

	public void close() throws IOException {

		if (socket != null) {
			socket.close();
			pw.close();
			br.close();
		}

		g.threads.remove(this);
	}

	public boolean register(User u) throws SQLException {

		if (g.ds.checkUer(u)) {
			return false;
		} else {
			g.ds.insert(u);
			return true;
		}
	}

	private String readFromClient() {
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			// 将出错的是socket移除列表
			try {
				close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

}
