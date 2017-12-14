package hljpolice.pahlj.com.hljpoliceapp;

/**
 * Created by sunyu on 2016/11/15.
 */

public interface I {

    String SINA_SERVER = "http://weibo.com/u/2729830305";//加载新浪微博

    String SERVER_ROOT = "https://jiekou.hljga.gov.cn/cs/mhnmcz/";//首页功能模块url

    //    String SHIXIANG_ADDRESS = "http://www.83027110.com/stwx/sxzn.html"; //事项地址
    String VERSION_SERVER = "http://file.hljga.gov.cn:8081/mobile/";  //版本请求及更新

    String NEWS_MOUDLES = "newsApp/searchPage.go?pageIndex=1&pageSize=10";

    String VERSION_INFO = "versionInfo.json";   //版本信息

    String VERSION_JIEKOU = "https://jiekou.hljga.gov.cn/cs/mobile/"; //版本信息接口

    String FUNCTION_MOUDLES = "htmlfuncApp/searchAll.go";

    String SHIXIANG_SEARCH = "jcShixiang/searchPager.go";  //事项搜索

    String SHIXIANG_MODULE_NAME = "zdBiaozhun/42/getBiaoZhunByGlid.go";//获取警种的各项名称

    String PAGE_INDEX = "pageIndex";  //分页下载的页码

    String PAGE_SIZE = "pageSize"; // 一页的行数

    String JFXW_URL = "http://yingyong.hljga.gov.cn/jfxw.html";//警务资讯url

    String UPDATE_APP = "update_app";

    //文件名
    String FILE_NAME = "file_name";

    // 改变登录时的标签，用于判断当前登录的设备
    String CHANGE_APPTYPE = "javascript:typemeg('3')";

    String TARGET = "target";
    String TITLE = "title";
    //事项中心
    String SHIXIANG_TITLE = "事项中心";

    //首页标题
    String MENU_TITLE = "平安龙江";
    // 上传点击数
    String DJS_SERVER = "https://jiekou.hljga.gov.cn/cs/mhnmcz/jcShixiang/djs.go";
    String DJS_SXID = "sxid";

    interface YSRZ {
        String NAME = "name";   // 姓名
        String IDNUMBER = "idNumber";   // 身份证号
        String PIC_NAME = "picname";   // 图片名称

        /*
        注册
        */
        String UPLOAD_YZ = "http://113.0.63.200:9991/lttestNew/file/goonesuoApp.go"; // 上传到一所认证
        String REPETITION_URL = "http://113.0.63.200:9991/lttestNew/file/yzPhoneCf.go"; // 手机号重复认证
        String GAIN_YZM = "http://113.0.63.200:9991/lttestNew/login/vadatorCode/sendCode.go"; // 获取短信验证码
        String YZMRZ_URL = "http://113.0.63.200:9991/lttestNew/file/yzmyz.go"; // 验证手机验证码
        /*
        治安
        */
        String ZHIAN_URL="http://113.0.63.200:9991/lttestNew/file/rebackZhianApp.go";
    }
}
