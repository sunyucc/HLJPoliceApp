package hljpolice.pahlj.com.hljpoliceapp.bean;

public class DWZAjax {
	private String statusCode = "300"; 
	private String message = "执行失败"; 
	private String navTabId;
	private String rel;
	private String callbackType="closeCurrent";
	private String forwardUrl;
	private String data;
	private String data2;
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNavTabId() {
		return navTabId;
	}
	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	public String getCallbackType() {
		return callbackType;
	}
	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DWZAjax{" +
				"statusCode='" + statusCode + '\'' +
				", message='" + message + '\'' +
				", navTabId='" + navTabId + '\'' +
				", rel='" + rel + '\'' +
				", callbackType='" + callbackType + '\'' +
				", forwardUrl='" + forwardUrl + '\'' +
				", data='" + data + '\'' +
				", data2='" + data2 + '\'' +
				'}';
	}
}
