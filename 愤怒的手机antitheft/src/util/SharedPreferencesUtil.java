package util;

 
 
 

import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
	static String prefix = "x001";
	static String pref_field_name = ""; 
	static int pref_mode = Context.MODE_PRIVATE; 
	 
	public static SharedPreferences getSharedPreferences(ContextWrapper mainActivity){
		return mainActivity.getSharedPreferences(prefix+pref_field_name,pref_mode);
	}
	 
	public static int getInt(ContextWrapper mainActivity,int defaultValue){
		int ret = -1;
		try{
			SharedPreferences pres = getSharedPreferences(mainActivity);
		    if(pres !=null)
		    { 
		      ret = pres.getInt(prefix+pref_field_name,defaultValue);
		    } 
		}catch (Exception e) {
			ToastUtil.show(mainActivity, e.getMessage());
		}
		
	    return ret;
	}
	
	public static boolean setInt(ContextWrapper mainActivity,int eyeStatus){
		boolean ret = false;
		try{
			SharedPreferences pres = getSharedPreferences(mainActivity); 
			ret = updateInt(pres, prefix+pref_field_name,eyeStatus);
		}catch (Exception e) {
			ToastUtil.show(mainActivity, e.getMessage());
		}
	    return ret;
	}
	
	public static boolean updateInt(SharedPreferences sp,String name,int value){
		if(sp!=null){
			SharedPreferences.Editor ed = sp.edit(); 
            ed.putInt(name,value); 
            ed.commit();
            return true;
		}
		return false;
	}

	
	
	public static String getString(Service service,String defaultValue){
		
		String ret = "";
		SharedPreferences pres = getSharedPreferences(service);
	    if(pres !=null)
	    { 
	      ret = pres.getString(prefix+pref_field_name,defaultValue);
	    }  
	    return ret;
	}
	
	
	public static String getString(ContextWrapper mainActivity,String defaultValue){
		String ret = "";
		SharedPreferences pres = getSharedPreferences(mainActivity);
	    if(pres !=null)
	    {
	      ret = pres.getString(prefix+pref_field_name,defaultValue);
	    }  
	    return ret;
	}
	
	public static boolean setString(ContextWrapper mainActivity,String eyeStatus){
		SharedPreferences pres = getSharedPreferences(mainActivity); 
		return updateString(pres, prefix+pref_field_name,eyeStatus); 
	}
	
	public static boolean updateString(SharedPreferences sp,String name,String value){
		if(sp!=null){
			SharedPreferences.Editor ed = sp.edit(); 
            ed.putString(name,value); 
            ed.commit();
            return true;
		}
		return false;
	}
}
