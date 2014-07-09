package util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitmapUtil {
	public static Bitmap scale(Bitmap bmp,Matrix matrix){
		return Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight(),matrix,true);
	}
}
