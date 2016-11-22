package hljpolice.pahlj.com.hljpoliceapp;

/**
 * Created by sunyu on 2016/11/15.
 */

public interface I {
    String SERVER_ROOT = "http://61.180.150.46:8088/cs/gyqq/";
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
}
