package org.antitheft.activity;




import org.antitheft.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.LinearLayout;

import com.waps.AdView;
import com.waps.AppConnect;

public class Welcome extends Activity{
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
//	Const.instance=this;
//	background bg=new background(getApplicationContext());
	setContentView(R.layout.welcome);
	AppConnect.getInstance(this);
	new Handler().postDelayed(new Runnable(){
         @Override
         public void run() {
        	 
     		Intent intent = new Intent(Welcome.this, MainActivity.class);
    		startActivity(intent);
    		Welcome.this.finish();
         }
           
        }, 2000);
}
}
