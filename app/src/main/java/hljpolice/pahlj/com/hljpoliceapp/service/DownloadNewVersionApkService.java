package hljpolice.pahlj.com.hljpoliceapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.bean.Version;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadNewVersionApkService extends Service {
    private static final String TAG = DownloadNewVersionApkService.class.getSimpleName();
    DownloadNewVersionApkService mContext;
    public DownloadNewVersionApkService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 启动更新下载文件
         final Version bean = (Version) intent.getSerializableExtra("app");
        L.e(TAG, "bean:" + bean);
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://gdown.baidu.com/data/wisegame/81cce5bbe49b4301/QQ_422.apk").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in = response.body().byteStream();
                FileOutputStream fos = null;
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(dir, bean.getApkname());
                L.e(TAG, "file:" + file);
                fos = new FileOutputStream(file);
                int len;
                byte[] bArr = new byte[1024*4];
                while ((len=in.read(bArr))!=-1){
                    fos.write(bArr,0,len);
                }
                L.e(TAG,"下载完成");

                Intent intent = new Intent(I.UPDATE_APP);
                intent.putExtra(I.FILE_NAME, file.toString());
                sendBroadcast(intent);
                if(in!=null){
                    in.close();
                }
                if (fos!=null){
                    fos.close();
                }
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

}
