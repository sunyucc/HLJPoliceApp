package hljpolice.pahlj.com.hljpoliceapp;

/**
 * Created by sunyu on 2016/11/15.
 */

public interface I {
    String SERVER_ROOT = "http://192.168.16.220:8088/cs/gyqq/";
    //新闻链接
    String NEWS_MOUDLES = "newsApp/searchPage.go?pageIndex=1&pageSize=5";
    //版本信息
    String VERSION_INFO = "../mobile/version.json";
    //功能模块
    String FUNCTION_MOUDLES = "htmlfuncApp/searchAll.go";

    String App_OLD_TYPE = "apptype=2";
    String APP_TYPE = "apptype=3";

    String UPDATE_APP = "update_app";
    String FILE_NAME = "file_name";
    //首页标题
    String MENU_TITLE = "平安黑龙江";

    String CHANGE_APPTYPE = "javascript:typemeg('3')";

    String UPDATE_APK ="http://ftp-apk.pconline.com.cn/1df1d50d5f636bc3df7cb539a4cb3115/pub/download/201010/wandoujia-yundongshidai4_ad_1116.apk";

}
