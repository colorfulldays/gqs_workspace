

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DAOSql {
	private static Connection conn;
	private static Statement state;
	private static ResultSet rs;
	private User user;
	public DAOSql()
	{
		String mysqlDriver="com.mysql.jdbc.Driver";
		String mysqlURL="jdbc:mysql://localhost:3306/User";
		String mysqlUser="root";
		String mysqlPassword="123";
		try
		{
			Class.forName(mysqlDriver);
			System.out.println("驱动接口程序连接成功");
		}
		catch(Exception e)
		{
			System.out.println("驱动接口程序接口连接失败"+mysqlDriver);
			e.printStackTrace();
		}
		try
		{
			conn=DriverManager.getConnection(mysqlURL,mysqlUser,mysqlPassword);
			state=conn.createStatement();
			if(!conn.isClosed())
			{
				System.out.println("数据库连接成功");
			}
		}
		catch(SQLException ex)
		{
			System.out.println("数据库连接失败");
			ex.printStackTrace();
		}
		
	}
	//将用户信息插入到数据库中
	public boolean insert(User u)
	{
		String sql="insert into user(name,password) values(?,?)";
//		  String sql="insert into person(name,zhuanye,xingbie)values (?,?,?)";
		   try {
		    //预编译，防止出现语法冲突
		    PreparedStatement pstmt=conn.prepareStatement(sql);
		    pstmt.setString(1, u.getName());
		    pstmt.setString(2, u.getPassword());
		    pstmt.execute();
		   
	return true;
		}
		catch(SQLException ex)
		{
			System.out.println("数据库连接失败");
			ex.printStackTrace();
			return false;
			
		}
	}
	//检测该用户是否存在
	public boolean checkUer(User u) throws SQLException
	{
		String sql="select name from user where name="+"'"+u.getName()+"'";
		rs=state.executeQuery(sql);
		rs.last();
		int length=rs.getRow();
		System.out.println("length"+length);
		if(length>0)
		{
			return true;
		}
		return false;
		
	}
	//欢迎所有在线用户
	public StringBuffer getUsers() throws SQLException
	{
		String sql="select name from user where sate=1";
		StringBuffer users=new StringBuffer();
		rs=state.executeQuery(sql);
		   while(rs.next())
		   {
			   users.append(rs.getString("name")+",");
		   }
		   return users;
	}
	//将用户改为登录状态
	public void changeState(int n,User u)
	{
		String sql="";
		if(n==1)
		{
			sql="update user set sate=1 where name=? and password=?";
			
		}
		else
		{
			sql="update user set sate=0 where name=? and password=?";
		}
		  System.out.println(sql);
		    PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, u.getName());
				pstmt.setString(2, u.getPassword());
				 pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		    
		
		
	}
	
	//检测登录用户是否合法
	public boolean isLogin(User u) throws SQLException
	{
		String sql="select * from user where name=? and password=?";
		
		   PreparedStatement pstmt=conn.prepareStatement(sql);
		    pstmt.setString(1, u.getName());
		    pstmt.setString(2, u.getPassword());
		  rs=pstmt.executeQuery();
		rs.last();
		int length=rs.getRow();
		if(length>0)
		{
			changeState(1,u);
			System.out.println("登录成功");
			return true;
		}
		else
		{
			System.out.println("登录失败");
			return false;
		}
	}
}
