package hljpolice.pahlj.com.hljpoliceapp;

/**
 * Created by sunyu on 2016/11/15.
 */

public interface I {
    String JFXW_SERVER = "http://www.83027110.com/stwx/jfxw.html";
    String SINA_SERVER = "http://weibo.com/u/2729830305";//加载新浪微博

    String SERVER_ROOT = "https://jiekou.hljga.gov.cn/cs/mhnmcz/";
    //事项地址
//    String SHIXIANG_ADDRESS = "http://www.83027110.com/stwx/sxzn.html";
    String VERSION_SERVER = "http://file.hljga.gov.cn:8081/mobile/";
    //新闻链接
    String NEWS_MOUDLES = "newsApp/searchPage.go?pageIndex=1&pageSize=5";
    //版本信息
    String VERSION_INFO = "version.json";
    String FUNCTION_MOUDLES = "htmlfuncApp/searchAll.go";
    //事项搜索
    String SHIXIANG_SEARCH = "jcShixiang/searchPager.go";
    //获取警种的各项名称
    String SHIXIANG_MODULE_NAME = "zdBiaozhun/1/getBiaoZhunByGlid.go";
    //    http://www.83027110.com/stwx/sxzn.html?bmid=101201&sxid=10090&zn=Y&yy=Y&sb=Y&sxmc=随军家属户口迁入
    //分页下载的页码
    String PAGE_INDEX = "pageIndex";
    // 一页的行数
    String PAGE_SIZE = "pageSize";

    String UPDATE_APP = "update_app";

    //文件名
    String FILE_NAME = "file_name";
    // 改变登录时的标签，用于判断当前登录的设备
    String CHANGE_APPTYPE = "javascript:typemeg('3')";
    String TARGET = "target";

    //事项中心
    String SHIXIANG_TITLE = "事项中心";

    //首页标题
    String MENU_TITLE = "平安龙江";

}
