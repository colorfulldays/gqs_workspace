package util;

import org.antitheft.Const;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	
	public static void show(Context context,String text){ 
		show(context, text, Const.showToast);
	}
	
	public static void show(Context context,String text,boolean beShow){
		if(beShow){
			Toast.makeText(context, text, Toast.LENGTH_LONG).show();
		}
	}
	
}
