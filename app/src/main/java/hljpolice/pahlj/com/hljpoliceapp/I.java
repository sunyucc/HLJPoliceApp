package hljpolice.pahlj.com.hljpoliceapp;

/**
 * Created by sunyu on 2016/11/15.
 */

public interface I {
    String SERVER_ROOT = "http://61.180.150.46:8088/cs/gyqq/";
    //事项地址
    String SHIXIANG_ADDRESS = "http://www.83027110.com/stwx/sxzn.html";
    //新闻链接
    String NEWS_MOUDLES = "newsApp/searchPage.go?pageIndex=1&pageSize=5";
    //版本信息
    String VERSION_INFO = "../mobile/version.json";
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

    String App_OLD_TYPE = "apptype=2";
    String APP_TYPE = "apptype=3";

    String UPDATE_APP = "update_app";

    String FILE_NAME = "file_name";
    //首页标题
    String MENU_TITLE = "平安黑龙江";
    // 改变登录时的标签，用于判断当前登录的设备
    String CHANGE_APPTYPE = "javascript:typemeg('3')";
    // 更新apk
    String UPDATE_APK = "http://ftp-apk.pconline.com.cn/1df1d50d5f636bc3df7cb539a4cb3115/pub/download/201010/wandoujia-yundongshidai4_ad_1116.apk";
    //事项中心
    String SHIXIANG_TITLE = "事项中心";
}
