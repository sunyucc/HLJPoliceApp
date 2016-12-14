package hljpolice.pahlj.com.hljpoliceapp.bean;

/**
 * 警种分类的实体类
 * Created by sunyu on 2016/11/23.
 */

public class ShiXiangModuleBean {

    /**
     * zdid : 1
     * glid : 1
     * mc : 户政
     * bm : 01
     * jp : hz
     * qp : huzheng
     * scbj : n
     * cjsj : 2016-11-14
     * gxsj :
     */

    private int zdid;
    private int glid;
    private String mc;
    private String bm;
    private String jp;
    private String qp;
    private String scbj;
    private String cjsj;
    private String gxsj;

    public int getZdid() {
        return zdid;
    }

    public void setZdid(int zdid) {
        this.zdid = zdid;
    }

    public int getGlid() {
        return glid;
    }

    public void setGlid(int glid) {
        this.glid = glid;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    public String getQp() {
        return qp;
    }

    public void setQp(String qp) {
        this.qp = qp;
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

    @Override
    public String toString() {
        return "ShiXiangModuleBean{" +
                "zdid=" + zdid +
                ", glid=" + glid +
                ", mc='" + mc + '\'' +
                ", bm='" + bm + '\'' +
                ", jp='" + jp + '\'' +
                ", qp='" + qp + '\'' +
                ", scbj='" + scbj + '\'' +
                ", cjsj='" + cjsj + '\'' +
                ", gxsj='" + gxsj + '\'' +
                '}';
    }
}
