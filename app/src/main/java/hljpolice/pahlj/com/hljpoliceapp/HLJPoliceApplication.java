package hljpolice.pahlj.com.hljpoliceapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import hljpolice.pahlj.com.hljpoliceapp.service.CheckAppVersionService;

/**
 * Created by sunyu on 2016/11/15.
 */

public class HLJPoliceApplication extends Application {
    public static Context application;
    private static HLJPoliceApplication  instance = null;
    private static int currentVersion;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        try {
            PackageInfo packageInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
            currentVersion = packageInfo.versionCode;// 得到当前App的版本
            // 启动检查版本的Service
            Intent intent = new Intent(this, CheckAppVersionService.class);
            startService(intent);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized HLJPoliceApplication getInstance() {
        if (instance == null) {
            instance = new HLJPoliceApplication();
        }
        return instance;
    }
    public int getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(int currentVersion) {
        this.currentVersion = currentVersion;
    }

}
