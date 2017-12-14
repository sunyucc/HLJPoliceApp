package hljpolice.pahlj.com.hljpoliceapp.bean;

/**
 * Created by Merross on 2017/11/28.
 */

public class RenZheng {


    /**
     * message : 验证码错误
     * one : {"name":"孙宇","idNumber":"230521199411281912","validFrom":"","validEnd":"","inpicstream":"","picname":"","phone":"null","yzm":"null","picbyte":"","verificationbyte":"","filebyte":"","dk":"","pwd":""}
     */

    private String message;
    private OneBean one;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OneBean getOne() {
        return one;
    }

    public void setOne(OneBean one) {
        this.one = one;
    }

    public static class OneBean {
        /**
         * name : 孙宇
         * idNumber : 230521199411281912
         * validFrom :
         * validEnd :
         * inpicstream :
         * picname :
         * phone : null
         * yzm : null
         * picbyte :
         * verificationbyte :
         * filebyte :
         * dk :
         * pwd :
         */

        private String name;
        private String idNumber;
        private String validFrom;
        private String validEnd;
        private String inpicstream;
        private String picname;
        private String phone;
        private String yzm;
        private String picbyte;
        private String verificationbyte;
        private String filebyte;
        private String dk;
        private String pwd;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getValidFrom() {
            return validFrom;
        }

        public void setValidFrom(String validFrom) {
            this.validFrom = validFrom;
        }

        public String getValidEnd() {
            return validEnd;
        }

        public void setValidEnd(String validEnd) {
            this.validEnd = validEnd;
        }

        public String getInpicstream() {
            return inpicstream;
        }

        public void setInpicstream(String inpicstream) {
            this.inpicstream = inpicstream;
        }

        public String getPicname() {
            return picname;
        }

        public void setPicname(String picname) {
            this.picname = picname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getYzm() {
            return yzm;
        }

        public void setYzm(String yzm) {
            this.yzm = yzm;
        }

        public String getPicbyte() {
            return picbyte;
        }

        public void setPicbyte(String picbyte) {
            this.picbyte = picbyte;
        }

        public String getVerificationbyte() {
            return verificationbyte;
        }

        public void setVerificationbyte(String verificationbyte) {
            this.verificationbyte = verificationbyte;
        }

        public String getFilebyte() {
            return filebyte;
        }

        public void setFilebyte(String filebyte) {
            this.filebyte = filebyte;
        }

        public String getDk() {
            return dk;
        }

        public void setDk(String dk) {
            this.dk = dk;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
    }
}
