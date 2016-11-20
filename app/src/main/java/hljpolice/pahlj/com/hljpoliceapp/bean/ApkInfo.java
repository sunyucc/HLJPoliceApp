package hljpolice.pahlj.com.hljpoliceapp.bean;


public class ApkInfo {
	private String versionName;
	private int versionCode;
	private String appName;
	private String apkName;
	private String apkInstallPath;
	private String apkDBPath;
	
	
	public String getApkInstallPath() {
		return apkInstallPath;
	}
	public void setApkInstallPath(String apkInstallPath) {
		this.apkInstallPath = apkInstallPath;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getApkName() {
		return apkName;
	}
	public void setApkName(String apkName) {
		this.apkName = apkName;
	}
	
	
}
