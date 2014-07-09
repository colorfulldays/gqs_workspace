import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class g {
final static int REQUEST_TALK_ALL = 2;
final static int REQUEST_TALK_PRIVATE=3;
final static int REQUEST_REGISTER = 0;
final static int REQUEST_LOGIN = 1;
final static int STATE_ONLINE=0;
final static int SATE_OFFLINE=1;
static ArrayList<MyThread> threads=new ArrayList<MyThread>();
public static ArrayList<Socket> sockets=new ArrayList<Socket>();
static DAOSql ds=new DAOSql();
static List<String> userList=new ArrayList<String>();
//发送群消息
public static void sendAll(String msg) {
	for (MyThread thread : g.threads) {
		if (thread != null && thread.state == g.STATE_ONLINE) {
			try {
				thread.write(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

public static void sendUser()
{
	for(MyThread thread:threads)
	{
		if (thread!=null&&thread.state==STATE_ONLINE) {
			try {
				for(int i=userList.size()-1;i>=0;i--)
				{
					thread.write("users"+":"+userList.get(i));
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
public static void sendUser(String user)
{
	for(MyThread thread:threads)
	{
		if (thread!=null&&thread.state==STATE_ONLINE) {
			try {
				
					thread.write("removeUser"+":"+user);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
//发送私人消息
public static void sendPrivate(String msg,String name)
{
	for(MyThread thread:threads)
	{
		if (thread!=null&&thread.state==STATE_ONLINE&&thread.getUser().getName().equals(name)) {
			try {
				thread.write(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}
}
}
