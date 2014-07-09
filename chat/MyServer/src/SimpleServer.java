
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
//	static InputStream is;
public static void main(String[] args) throws IOException {
	ServerSocket ss=new ServerSocket(54321);

	System.out.print("服务器开启");
	while(true)
	{
		Socket s=ss.accept();
		System.out.print("连接成功");
		MyThread thread=new MyThread(s);
		g.threads.add(thread);
		new Thread(thread).start();
	}
}
}
