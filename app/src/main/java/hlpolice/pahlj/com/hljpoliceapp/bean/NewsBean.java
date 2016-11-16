package hlpolice.pahlj.com.hljpoliceapp.bean;

import java.util.List;

/**
 * Created by sunyu on 2016/11/16.
 */

public class NewsBean {

    /**
     * total : 3
     * data : [{"xwid":1001,"tplj":"http://img.ads.csdn.net/2016/201610200947374106.jpg","xwdz":"www.baidu.com","sfgd":"y","scbj":"n","cjsj":"2016-11-16","gxsj":"","xwmc":"百度"},{"xwid":1002,"tplj":"http://img.ads.csdn.net/2016/201610111013527539.jpg","xwdz":"www.sina.com","sfgd":"y","scbj":"n","cjsj":"2016-11-16","gxsj":"","xwmc":"新浪"},{"xwid":1003,"tplj":"http://c1.ifengimg.com/mappa/2016/09/14/9e21174ba6a40b3e7f6b51763970f80d.jpg","xwdz":"www.ifeng.com","sfgd":"y","scbj":"n","cjsj":"2016-11-16","gxsj":"","xwmc":"凤凰网"}]
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
         * xwid : 1001
         * tplj : http://img.ads.csdn.net/2016/201610200947374106.jpg
         * xwdz : www.baidu.com
         * sfgd : y
         * scbj : n
         * cjsj : 2016-11-16
         * gxsj :
         * xwmc : 百度
         */

        private int xwid;
        private String tplj;
        private String xwdz;
        private String sfgd;
        private String scbj;
        private String cjsj;
        private String gxsj;
        private String xwmc;

        public int getXwid() {
            return xwid;
        }

        public void setXwid(int xwid) {
            this.xwid = xwid;
        }

        public String getTplj() {
            return tplj;
        }

        public void setTplj(String tplj) {
            this.tplj = tplj;
        }

        public String getXwdz() {
            return xwdz;
        }

        public void setXwdz(String xwdz) {
            this.xwdz = xwdz;
        }

        public String getSfgd() {
            return sfgd;
        }

        public void setSfgd(String sfgd) {
            this.sfgd = sfgd;
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

        public String getXwmc() {
            return xwmc;
        }

        public void setXwmc(String xwmc) {
            this.xwmc = xwmc;
        }
    }
}
