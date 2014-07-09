import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class SQLHelper {
private DAOSql daoSql;
private User user;
private static Connection conn;
private static Statement state;
private static ResultSet rs;
public SQLHelper()
{
	daoSql=new DAOSql();
}
//public ResultSet getUser(User u)
//{
//	
//}
}
