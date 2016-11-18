package hlpolice.pahlj.com.hljpoliceapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by yao on 2016/7/2.
 */
public class ApkVersionUtils {
    public static int getApkVersion(Context context) {
        //获取包管理器
        PackageManager packageManager = context.getPackageManager();
        try {
            //获取包信息
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            //获取并返回app的版本
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
