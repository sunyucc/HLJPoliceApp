package hlpolice.pahlj.com.hljpoliceapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import hlpolice.pahlj.com.hljpoliceapp.service.CheckApkVersionService;

/**
 * Created by sunyu on 2016/11/15.
 */

public class HLJPoliceApplication extends Application {
    public static Context application;
    public static String ROOT_URL = "http://10.0.2.2:8080/";
    public static String APP_INFO = "apk_info.json";
    public static String APP_FILE_NAME = "baidu_safe.apk";
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Intent intent = new Intent(this, CheckApkVersionService.class);
        startService(intent);

    }

    private static class HLJPoliceInstance{
        static final HLJPoliceApplication instance = new HLJPoliceApplication();
    }
    public static HLJPoliceApplication getApplication() {
        return HLJPoliceInstance.instance;
    }

}
