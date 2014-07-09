package org.antitheft.receiver;
 
import org.antitheft.Const;
import org.antitheft.activity.MainActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/* 捕捉android.intent.action.BOOT_COMPLETED的Receiver类 */
public class AutoRunIntentReceiver extends BroadcastReceiver
{
  @Override
  public void onReceive(Context context, Intent intent)
  {
    //更新一个值，用来实现开机自动最小化 
    /* 当收到Receiver时，指定开启否程序（EX06_16.class） */
    Intent mBootIntent = new Intent(context, MainActivity.class); 
    /* 设定Intent开启为FLAG_ACTIVITY_NEW_TASK */ 
    mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    mBootIntent.putExtra(Const.AFTER_AUTORUN,Const.TRUE);  
    Bundle bundle = new Bundle();
    bundle.putInt(Const.AFTER_AUTORUN, Const.TRUE);
    mBootIntent.putExtras(bundle);
    /* 将Intent以startActivity传送给操作系统 */ 
    context.startActivity(mBootIntent);
  }
}

