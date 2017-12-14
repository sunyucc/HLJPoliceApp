package hljpolice.pahlj.com.hljpoliceapp.bean;

/**
 * Created by Merross on 2017/11/22.
 */

public class Status {

    @Override
    public String toString() {
        return "Status{" +
                "statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                ", navTabId=" + navTabId +
                ", rel=" + rel +
                ", callbackType='" + callbackType + '\'' +
                ", forwardUrl=" + forwardUrl +
                ", data=" + data +
                ", data2=" + data2 +
                '}';
    }

    /**
     * statusCode : 300
     * message : 图片文件不存在
     * navTabId : null
     * rel : null
     * callbackType : closeCurrent
     * forwardUrl : null
     * data : null
     * data2 : null
     */

    private String statusCode;
    private String message;
    private Object navTabId;
    private Object rel;
    private String callbackType;
    private Object forwardUrl;
    private Object data;
    private Object data2;

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

    public Object getNavTabId() {
        return navTabId;
    }

    public void setNavTabId(Object navTabId) {
        this.navTabId = navTabId;
    }

    public Object getRel() {
        return rel;
    }

    public void setRel(Object rel) {
        this.rel = rel;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }

    public Object getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(Object forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData2() {
        return data2;
    }

    public void setData2(Object data2) {
        this.data2 = data2;
    }
}
