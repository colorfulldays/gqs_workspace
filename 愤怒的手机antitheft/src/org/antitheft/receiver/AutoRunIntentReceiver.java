package org.antitheft.receiver;
 
import org.antitheft.Const;
import org.antitheft.activity.MainActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/* ��׽android.intent.action.BOOT_COMPLETED��Receiver�� */
public class AutoRunIntentReceiver extends BroadcastReceiver
{
  @Override
  public void onReceive(Context context, Intent intent)
  {
    //����һ��ֵ������ʵ�ֿ����Զ���С�� 
    /* ���յ�Receiverʱ��ָ�����������EX06_16.class�� */
    Intent mBootIntent = new Intent(context, MainActivity.class); 
    /* �趨Intent����ΪFLAG_ACTIVITY_NEW_TASK */ 
    mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    mBootIntent.putExtra(Const.AFTER_AUTORUN,Const.TRUE);  
    Bundle bundle = new Bundle();
    bundle.putInt(Const.AFTER_AUTORUN, Const.TRUE);
    mBootIntent.putExtras(bundle);
    /* ��Intent��startActivity���͸�����ϵͳ */ 
    context.startActivity(mBootIntent);
  }
}

