package org.antitheft.receiver;
 
import org.antitheft.Const;
import org.antitheft.activity.PhoneNumberActivity;
import org.antitheft.activity.AlertActivity;
import org.antitheft.service.AlertService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/* ��׽android.intent.action.BOOT_COMPLETED��Receiver�� */
public class ActivitiesIntentReceiver extends BroadcastReceiver
{
  @Override
  public void onReceive(Context context, Intent intent)
  {
	  
	  if(intent.getAction().toString().equals(Const.ACTIVITIES_INTENT)){
		  Bundle bundle = intent.getExtras(); 
		  if(bundle!=null){
			  String type = bundle.getString("type"); 
			   if(type.equals("phoneNumberActivity")){ 
				  	Intent mBootIntent = new Intent(context, PhoneNumberActivity.class); 
		 		    mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		 		    context.startActivity(mBootIntent);   
			   } 
			   
		  }else{ 
			  //������������ͷ���
	 		    Intent mBootIntent = new Intent(context, AlertActivity.class); 
	 		    mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	 		    context.startActivity(mBootIntent); 
	 		    
			    Intent musicServiceIntent = new Intent(context,AlertService.class);
			    context.startService(musicServiceIntent);
			   
		  }   
	  }
  }
}

