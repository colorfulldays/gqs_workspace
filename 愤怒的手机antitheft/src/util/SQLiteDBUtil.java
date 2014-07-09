package util;
 

import org.antitheft.Const;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteDBUtil extends SQLiteOpenHelper {
	
	public String createTempSQL_ = "create table if not exists temp (id integer)";
	public String dropTempSQL_ = "drop table if exists temp";
	String insertSql = "insert into "+Const.DB_TABLE+" (name,value) values (?,?)";
	String updateSql = "update "+Const.DB_TABLE+" set value=? where name=?";
	public String dropSQL = "drop table if exists "+Const.DB_TABLE;
	public String createSQL = "create table if not exists "+Const.DB_TABLE+" (id integer primary key autoincrement  ,value text, name text)";
	
	
	 public boolean update( Context context,String fieldname,String fieldvalue){  
		  String sql = "";
		 if(get(context,fieldname).equals("")){//插入 
			sql = insertSql;
			return  executeUpdate(context,sql,new String[]{fieldname,fieldvalue}); 
		 }else{//更新
			 sql = updateSql;
			 return  executeUpdate(context,sql,new String[]{fieldvalue,fieldname}); 
		 } 
	 }
	 
	 public String get( Context context,String fieldname){ 
		 String sql = "select value from "+Const.DB_TABLE+" where name=?"; 
		 SQLiteDatabase db = this.getWritableDatabase();
		 Cursor rs =  executeQuery(context,db,sql,new String[]{fieldname}); 
		 String ret = "";
		 if(rs==null){
			 createTable(context);
			 return ret;
		 }
		 while(rs.moveToNext()){ 
			 ret = rs.getString(0);
		 } 
		 if(!rs.isClosed()){
			 rs.close();
		 }
		 if(db.isOpen()){
			 db.close();
		 }
		 return ret; 
	 }
	
	
	public SQLiteDBUtil(Context context){ 
		super(context , Const.DB_NAME, null, Const.DB_VERSION);
	}
	
	SQLiteDBUtil(Context context, String name, CursorFactory factory,int version) {  
		super(context, name, factory, version); 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		 
	}
 
 
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//db.execSQL(dropSQL);
		//db.execSQL(createSQL);
	} 
	
	public boolean createTable(Context context) {
		return executeUpdate(context,createSQL,null); 
	}
	
	
	
	//更新语句
	public  boolean executeUpdate(Context context,String sql,String[] fields){ 
		ToastUtil.show(context, sql); 
		boolean ret = false;
		try{
			SQLiteDatabase db = this.getWritableDatabase(); 
			if(fields==null){
				db.execSQL(sql,new String[]{});
			}else{
				db.execSQL(sql,fields);
			}
			if(db.isOpen()){ 
				db.close();
			} 
			ret = true;
		}catch (Exception e) {
			e.printStackTrace();
			ToastUtil.show(context, e.getMessage());
		}
		return ret;
	}
	
	//查询，返回结果集
	public Cursor executeQuery(Context context,SQLiteDatabase db,String sql,  String[] fileds){
		ToastUtil.show(context, sql);
		Cursor cursor = null;
		try{ 
			cursor = db.rawQuery(sql,fileds); 
		}catch (Exception e) { 
			ToastUtil.show(context, e.getMessage());
			e.printStackTrace(); 
		}
		return cursor;
	}

	public boolean dropTable(Context context) {
		return executeUpdate(context,dropSQL,null); 
	}

	public int getInt(Context context, String fieldname,int defaultValue) {
		String get =   get(context,fieldname);  
		if(get.equals("")){
			return defaultValue;
		}else{
			return Integer.parseInt(get);
		}
	}

	public boolean setInt(Context context, String fieldname,int fieldvalue) {
		 return update(context, fieldname, ""+fieldvalue);
	}

	public boolean setString(Context context, String fieldname, String fieldvalue) {
		 return update(context, fieldname, fieldvalue);
	}

	public String getString(Context context,String dbFieldPhoneimei) {
		return get(context,dbFieldPhoneimei);
	}
	
	
	
}
