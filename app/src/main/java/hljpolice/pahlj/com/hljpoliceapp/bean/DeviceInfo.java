package hljpolice.pahlj.com.hljpoliceapp.bean;

public class DeviceInfo {
	private String IMEI;
	private String ICCID;
	private String DeviceIP;
	private String PhoneNumber;
	private String Serial;
	private String DeviceName;
	private String DeviceModel;
	private String DeviceId;
	
	
	public String getDeviceName() {
		return DeviceName;
	}
	public void setDeviceName(String deviceName) {
		DeviceName = deviceName;
	}
	public String getDeviceModel() {
		return DeviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		DeviceModel = deviceModel;
	}
	public String getDeviceId() {
		return DeviceId;
	}
	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}
	public String getSerial() {
		return Serial;
	}
	public void setSerial(String serial) {
		Serial = serial;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getIMEI() {
		return IMEI;
	}
	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
	public String getDeviceIP() {
		return DeviceIP;
	}
	public void setDeviceIP(String deviceIP) {
		DeviceIP = deviceIP;
	}
	
	
}
