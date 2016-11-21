package hljpolice.pahlj.com.hljpoliceapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.gson.Gson;

import hljpolice.pahlj.com.hljpoliceapp.HLJPoliceApplication;
import hljpolice.pahlj.com.hljpoliceapp.bean.Version;
import hljpolice.pahlj.com.hljpoliceapp.dao.NetDao;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;
import hljpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;


public class CheckAppVersionService extends Service {
    private static final String TAG = CheckAppVersionService.class.getSimpleName();

    public CheckAppVersionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NetDao.updateApp(HLJPoliceApplication.application, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String json) {
                L.e(TAG, "json:" + json);
                if (json != null) {
                    Gson gson = new Gson();
                    Version version = gson.fromJson(json, Version.class);
                    L.e(TAG, "Version:" + version);
                    // 大于当前版本应该更新Apk
                        L.e("getvercode"+HLJPoliceApplication.getInstance().getCurrentVersion());
                        L.e("getcurrentVersion"+version.getVerCode());
                    if (Integer.parseInt(version.getVerCode()) > HLJPoliceApplication.getInstance().getCurrentVersion()) {
                        // 启动更新App服务
                        Intent intent = new Intent(CheckAppVersionService.this, DownloadNewVersionApkService.class);
                        intent.putExtra("app",version);
                        startService(intent);
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });

        return super.onStartCommand(intent, flags, startId);
    }
}
