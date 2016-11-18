package hlpolice.pahlj.com.hljpoliceapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import hlpolice.pahlj.com.hljpoliceapp.HLJPoliceApplication;
import hlpolice.pahlj.com.hljpoliceapp.bean.ApkBean;
import hlpolice.pahlj.com.hljpoliceapp.utils.ApkVersionUtils;
import hlpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;


/**
 * 检查apk版本
 */
public class CheckApkVersionService extends Service {
    public CheckApkVersionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        OkHttpUtils<ApkBean> utils=new OkHttpUtils<>(this);
        utils.url(HLJPoliceApplication.ROOT_URL+HLJPoliceApplication.APP_INFO)
                .targetClass(ApkBean.class)
                .execute(new OkHttpUtils.OnCompleteListener<ApkBean>() {
                    @Override
                    public void onSuccess(ApkBean apk) {
                        int oldVersion = ApkVersionUtils.getApkVersion(CheckApkVersionService.this);
                        if (apk.getVersion() > oldVersion) {
                            //启动下载的Service
                         Log.i("main", "有新版本啦!");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.i("main", error);

                    }
                });
        return super.onStartCommand(intent, flags, startId);
    }
}
