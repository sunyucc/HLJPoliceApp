package hlpolice.pahlj.com.hljpoliceapp.bean;

import java.util.List;

/**
 * Created by sunyu on 2016/11/15.
 */

public class FunctionBean {

    /**
     * data : [{"funcid":10003,"tbdz":"http://www.83027110.com/stwx/images/1_07.png","mkmc":"我要咨询","qqdz":"http://www.83027110.com/stwx/zxzx.html","gnms":"我要咨询","scbj":"n","cjsj":"2016-11-16","gxsj":"","mklb":"01"}]
     * mklb : 01
     * mc : 扩展一
     */

    private String mklb;
    private String mc;
    private List<DataBean> data;

    public String getMklb() {
        return mklb;
    }

    public void setMklb(String mklb) {
        this.mklb = mklb;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * funcid : 10003
         * tbdz : http://www.83027110.com/stwx/images/1_07.png
         * mkmc : 我要咨询
         * qqdz : http://www.83027110.com/stwx/zxzx.html
         * gnms : 我要咨询
         * scbj : n
         * cjsj : 2016-11-16
         * gxsj :
         * mklb : 01
         */

        private int funcid;
        private String tbdz;
        private String mkmc;
        private String qqdz;
        private String gnms;
        private String scbj;
        private String cjsj;
        private String gxsj;
        private String mklb;

        public int getFuncid() {
            return funcid;
        }

        public void setFuncid(int funcid) {
            this.funcid = funcid;
        }

        public String getTbdz() {
            return tbdz;
        }

        public void setTbdz(String tbdz) {
            this.tbdz = tbdz;
        }

        public String getMkmc() {
            return mkmc;
        }

        public void setMkmc(String mkmc) {
            this.mkmc = mkmc;
        }

        public String getQqdz() {
            return qqdz;
        }

        public void setQqdz(String qqdz) {
            this.qqdz = qqdz;
        }

        public String getGnms() {
            return gnms;
        }

        public void setGnms(String gnms) {
            this.gnms = gnms;
        }

        public String getScbj() {
            return scbj;
        }

        public void setScbj(String scbj) {
            this.scbj = scbj;
        }

        public String getCjsj() {
            return cjsj;
        }

        public void setCjsj(String cjsj) {
            this.cjsj = cjsj;
        }

        public String getGxsj() {
            return gxsj;
        }

        public void setGxsj(String gxsj) {
            this.gxsj = gxsj;
        }

        public String getMklb() {
            return mklb;
        }

        public void setMklb(String mklb) {
            this.mklb = mklb;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "funcid=" + funcid +
                    ", tbdz='" + tbdz + '\'' +
                    ", mkmc='" + mkmc + '\'' +
                    ", qqdz='" + qqdz + '\'' +
                    ", gnms='" + gnms + '\'' +
                    ", scbj='" + scbj + '\'' +
                    ", cjsj='" + cjsj + '\'' +
                    ", gxsj='" + gxsj + '\'' +
                    ", mklb='" + mklb + '\'' +
                    '}';
        }
    }
}
