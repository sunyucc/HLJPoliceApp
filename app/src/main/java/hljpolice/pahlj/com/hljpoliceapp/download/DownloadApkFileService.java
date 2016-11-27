package hljpolice.pahlj.com.hljpoliceapp.download;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import hljpolice.pahlj.com.hljpoliceapp.utils.L;

/**
 * Created by Carklote on 2016/11/26.
 */

public class DownloadApkFileService extends Service {

    private DownloadBinder binder;

    @Override
    public void onCreate() {
        L.e("@@@@@:" + "建立服务");
        binder = new DownloadBinder(getApplicationContext());
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        L.e("@@@@@@: " + "销毁服务");
//        binder.close();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

}
