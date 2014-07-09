package util;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.Prediction;

public class GestureUtil {

	public static boolean match(Activity activity,GestureLibrary lib, Gesture ges) {
		if(lib.load()){ 
			ArrayList<Prediction> prefictions = lib.recognize(ges);
			if(prefictions.size()>0){
				//ToastUtil.show(activity, "prefiction.score = " + prefictions.get(0).score,true);
				return   prefictions.get(0).score > 1.0 ; 
			}
		}
		return false;
	}
	
	public static boolean match(Activity activity,String libFile, Gesture ges) {
		GestureLibrary lib = GestureLibraries.fromFile(libFile); 
		return match(activity,lib, ges);
	}

}
