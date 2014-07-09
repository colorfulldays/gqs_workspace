package com.gqs.exam;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Toast;

import com.gqs.Const.Const;
import com.gqs.Database.DatabaseHelper;
import com.gqs.util.FileUtils;

public class Welcome extends Activity {
	AssetManager am = null;
//	private ProgressDialog progressDialog;
	InputStream is;
	private DatabaseHelper databaseHelper;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        
//        progressDialog = ProgressDialog.show(Welcome.this, "Loading...", "Please wait...", false, false);   
      
        if(FileUtils.isExistSDCard())
        {
            new Thread(new Runnable()
            {
    			@Override
    			public void run() {
    				// TODO Auto-generated method stub

    				FileUtils.createDir(Const.FOLDER_NAME);
    			
    	        	if(!FileUtils.isExist(Const.DB_FILE_NAME))
    	        	{
    	        		try {
    	        			am=getAssets();
    						is=am.open(Const.DB_FILE_NAME);
    						
    						FileUtils.fromAssetWriteToSDCard(is, Const.DB_FILE_NAME);
    						
    						
    					} catch (IOException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    	  
    	        	}
//    	        	progressDialog.dismiss();
    	        	databaseHelper=new DatabaseHelper(Welcome.this);
    	        	Const.listChoice=databaseHelper.getQuetion(Const.CHOICE_TABLE);
					Const.listJudge=databaseHelper.getQuetion(Const.JUDGE_TABLE);
    	        	Intent intent=new Intent(Welcome.this,MainActivity.class);
    				startActivity(intent);
    				Welcome.this.finish();
    			}
            	
            }).start();
        	
        }
        else
        {
        	 Toast.makeText(getApplicationContext(), "¥Ê¥¢ø®º”‘ÿ ß∞‹", Toast.LENGTH_SHORT).show();
        	 Welcome.this.finish();
        }
       
       
  
    }
    public void init()
    {
    	
    }
}