package util;

import android.telephony.TelephonyManager;

public class TelephonyManagerUtil {
	 
	public static TelephonyData getTelephonyData(TelephonyManager telManager) {
		 String phoneNumber = telManager.getLine1Number();
		 String phoneIMEI = telManager.getDeviceId();
		 String phoneIMSI = telManager.getSubscriberId();
		 return new TelephonyData(phoneNumber, phoneIMEI, phoneIMSI);
	}
}
