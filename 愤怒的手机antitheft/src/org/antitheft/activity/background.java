package org.antitheft.activity;

import org.antitheft.Const;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

public class background extends View{
   private Paint paint;
   private Bitmap bmLogo;
   private Bitmap bmKey;
   private Bitmap bmmain_bg;
   private Matrix matrix;
	public background(Context context) {
		super(context);
		paint=new Paint();
		bmLogo=Const.loadBitmap("fenghuo_logo.png");
		bmKey=Const.loadBitmap("key.png");
		bmmain_bg=Const.loadBitmap("main_bg.jpg");
		matrix=new Matrix();


	}
	
    @Override
    protected void onDraw(Canvas canvas) {
//    	float bmpWidth=getWidth()/bmmain_bg.getWidth();
//    	float bmpHeight=getHeight()/bmmain_bg.getHeight();
    	matrix.postScale(getWidth(), getHeight());
    	Bitmap resizeBmp=Bitmap.createBitmap(bmmain_bg, 0, 0, bmmain_bg.getWidth(), bmmain_bg.getHeight(), matrix, true);
    	canvas.drawBitmap(resizeBmp, matrix, paint);
    	resizeBmp=Bitmap.createBitmap(bmKey, 0, 0, bmKey.getWidth(), bmKey.getHeight(), matrix, true);
//    	canvas.drawBitmap(bmmain_bg, 0, 0, paint);
    	canvas.drawBitmap(bmKey, matrix, paint);
    	canvas.drawBitmap(bmLogo, 0,getHeight()-40, paint);
    }
}
