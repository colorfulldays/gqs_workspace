package util;

public class TelephonyData {
	/*
	 * 手机卡唯一标识？答：IMSI
	 * 手机唯一标识？答：IMEI
	 */
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneIMEI() {
		return phoneIMEI;
	}
	public void setPhoneIMEI(String phoneIMEI) {
		this.phoneIMEI = phoneIMEI;
	}
	public String getPhoneIMSI() {
		return phoneIMSI;
	}
	public void setPhoneIMSI(String phoneIMSI) {
		this.phoneIMSI = phoneIMSI;
	}
	public String phoneNumber;
	public String phoneIMEI;
	public String phoneIMSI;
	public TelephonyData(String phoneNumber, String phoneIMEI, String phoneIMSI) {
		super();
		this.phoneNumber = phoneNumber;
		this.phoneIMEI = phoneIMEI;
		this.phoneIMSI = phoneIMSI;
	}
	
}
