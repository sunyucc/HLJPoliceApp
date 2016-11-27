package hljpolice.pahlj.com.hljpoliceapp.bean;

import java.util.List;

/**
 * Created by sunyu on 2016/11/23.
 */

public class ShiXiangBean {

    /**
     * total : 352
     * data : [{"sxid":10152,"sxmc":"损坏异地换领居民身份证","sxywdl":"01","sxfwlx":"02","sxywlb":"01","zn":"Y","yy":"Y","sb":"","zdBsckid":"101201","scbj":"n","gxsj":"","cjsj":"2016-11-14","djs":149},{"sxid":10090,"sxmc":"随军家属户口迁入","sxywdl":"01","sxfwlx":"04","sxywlb":"01","zn":"Y","yy":"Y","sb":"Y","zdBsckid":"101201","scbj":"n","gxsj":"","cjsj":"2016-11-14","djs":145},{"sxid":10114,"sxmc":"其他无户口人员补录户口登记","sxywdl":"01","sxfwlx":"04","sxywlb":"01","zn":"Y","yy":"Y","sb":"Y","zdBsckid":"101201","scbj":"n","gxsj":"","cjsj":"2016-11-14","djs":142},{"sxid":10089,"sxmc":"个体工商户户口迁入","sxywdl":"01","sxfwlx":"04","sxywlb":"01","zn":"Y","yy":"Y","sb":"Y","zdBsckid":"101201","scbj":"n","gxsj":"","cjsj":"2016-11-14","djs":141},{"sxid":10060,"sxmc":"出生出国（出境）人员子女申报户口 一、已注销户口的出国（出境）人员所生子女要求回国落户的","sxywdl":"01","sxfwlx":"04","sxywlb":"01","zn":"Y","yy":"Y","sb":"Y","zdBsckid":"101201","scbj":"n","gxsj":"","cjsj":"2016-11-14","djs":140},{"sxid":10150,"sxmc":"首次申领居民身份证","sxywdl":"01","sxfwlx":"02","sxywlb":"01","zn":"Y","yy":"Y","sb":"","zdBsckid":"101201","scbj":"n","gxsj":"","cjsj":"2016-11-14","djs":140},{"sxid":10106,"sxmc":"未办理《出生医学证明》的无户口人员补录户口登记","sxywdl":"01","sxfwlx":"04","sxywlb":"01","zn":"Y","yy":"Y","sb":"Y","zdBsckid":"101201","scbj":"n","gxsj":"","cjsj":"2016-11-14","djs":139},{"sxid":10149,"sxmc":"公民因丢失、损坏换领补领居住证收费标准","sxywdl":"01","sxfwlx":"01","sxywlb":"01","zn":"Y","yy":"","sb":"","zdBsckid":"101201","scbj":"n","gxsj":"","cjsj":"2016-11-14","djs":137},{"sxid":10147,"sxmc":"公民因丢失、损坏补办居民户口簿收费标准","sxywdl":"01","sxfwlx":"01","sxywlb":"01","zn":"Y","yy":"","sb":"","zdBsckid":"101201","scbj":"n","gxsj":"","cjsj":"2016-11-14","djs":136},{"sxid":10129,"sxmc":"血型变更、更正","sxywdl":"01","sxfwlx":"04","sxywlb":"01","zn":"Y","yy":"Y","sb":"Y","zdBsckid":"101201","scbj":"n","gxsj":"","cjsj":"2016-11-14","djs":133}]
     */

    private int total;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sxid : 10152
         * sxmc : 损坏异地换领居民身份证
         * sxywdl : 01
         * sxfwlx : 02
         * sxywlb : 01
         * zn : Y
         * yy : Y
         * sb :
         * zdBsckid : 101201
         * scbj : n
         * gxsj :
         * cjsj : 2016-11-14
         * djs : 149
         */

        private int sxid;
        private String sxmc;
        private String sxywdl;
        private String sxfwlx;
        private String sxywlb;
        private String zn;
        private String yy;
        private String sb;
        private String zdBsckid;
        private String scbj;
        private String gxsj;
        private String cjsj;
        private int djs;

        public int getSxid() {
            return sxid;
        }

        public void setSxid(int sxid) {
            this.sxid = sxid;
        }

        public String getSxmc() {
            return sxmc;
        }

        public void setSxmc(String sxmc) {
            this.sxmc = sxmc;
        }

        public String getSxywdl() {
            return sxywdl;
        }

        public void setSxywdl(String sxywdl) {
            this.sxywdl = sxywdl;
        }

        public String getSxfwlx() {
            return sxfwlx;
        }

        public void setSxfwlx(String sxfwlx) {
            this.sxfwlx = sxfwlx;
        }

        public String getSxywlb() {
            return sxywlb;
        }

        public void setSxywlb(String sxywlb) {
            this.sxywlb = sxywlb;
        }

        public String getZn() {
            return zn;
        }

        public void setZn(String zn) {
            this.zn = zn;
        }

        public String getYy() {
            return yy;
        }

        public void setYy(String yy) {
            this.yy = yy;
        }

        public String getSb() {
            return sb;
        }

        public void setSb(String sb) {
            this.sb = sb;
        }

        public String getZdBsckid() {
            return zdBsckid;
        }

        public void setZdBsckid(String zdBsckid) {
            this.zdBsckid = zdBsckid;
        }

        public String getScbj() {
            return scbj;
        }

        public void setScbj(String scbj) {
            this.scbj = scbj;
        }

        public String getGxsj() {
            return gxsj;
        }

        public void setGxsj(String gxsj) {
            this.gxsj = gxsj;
        }

        public String getCjsj() {
            return cjsj;
        }

        public void setCjsj(String cjsj) {
            this.cjsj = cjsj;
        }

        public int getDjs() {
            return djs;
        }

        public void setDjs(int djs) {
            this.djs = djs;
        }
    }

    @Override
    public String toString() {
        return "ShiXiangBean{" +
                "total=" + total +
                ", data=" + data +
                '}';
    }
}
