package hljpolice.pahlj.com.hljpoliceapp.bean;

import java.io.Serializable;

/**
 * Created by sunyu on 2016/11/20.
 */

/**
 * 版本更新json的实体类
 */
public class Version implements Serializable {

    /**
     * comm : {"qzgx":"0","sxdjs":"0"}
     * android : {"vercode":"12","version":"1.0.11.2","apkname":"hlj_hlw.apk","message":"xxx"}
     * ios : {"vercode":"10","version":"1.2.1.3","message":"xxxx"}
     */

    private CommBean comm;
    private AndroidBean android;
    private IosBean ios;

    public CommBean getComm() {
        return comm;
    }

    public void setComm(CommBean comm) {
        this.comm = comm;
    }

    public AndroidBean getAndroid() {
        return android;
    }

    public void setAndroid(AndroidBean android) {
        this.android = android;
    }

    public IosBean getIos() {
        return ios;
    }

    public void setIos(IosBean ios) {
        this.ios = ios;
    }

    public static class CommBean {
        /**
         * qzgx : 0
         * sxdjs : 0
         */

        private String qzgx;
        private String sxdjs;

        public String getQzgx() {
            return qzgx;
        }

        public void setQzgx(String qzgx) {
            this.qzgx = qzgx;
        }

        public String getSxdjs() {
            return sxdjs;
        }

        public void setSxdjs(String sxdjs) {
            this.sxdjs = sxdjs;
        }
    }

    public static class AndroidBean {
        /**
         * vercode : 12
         * version : 1.0.11.2
         * apkname : hlj_hlw.apk
         * message : xxx
         */

        private String vercode;
        private String version;
        private String apkname;
        private String message;

        public String getVercode() {
            return vercode;
        }

        public void setVercode(String vercode) {
            this.vercode = vercode;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
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
    }

    public static class IosBean {
        /**
         * vercode : 10
         * version : 1.2.1.3
         * message : xxxx
         */

        private String vercode;
        private String version;
        private String message;

        public String getVercode() {
            return vercode;
        }

        public void setVercode(String vercode) {
            this.vercode = vercode;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
