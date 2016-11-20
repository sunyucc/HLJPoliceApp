package hljpolice.pahlj.com.hljpoliceapp.bean;

import java.io.Serializable;

/**
 * Created by sunyu on 2016/11/20.
 */

public class Version implements Serializable {

    /**
     * verCode : 2
     * apkname : hlj_hlw.apk
     * message : 正在下载新版本，请耐心等待
     */

    private String verCode;
    private String apkname;
    private String message;

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getApkname() {
        return apkname;
    }

    public void setApkname(String apkname) {
        this.apkname = apkname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Version{" +
                "verCode='" + verCode + '\'' +
                ", apkname='" + apkname + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
