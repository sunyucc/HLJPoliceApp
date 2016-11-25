package hljpolice.pahlj.com.hljpoliceapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

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

/**
 * 用于下载apk的服务
 */
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
         final Version bean = (Version) intent.getSerializableExtra("app");    //获取版本信息
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(I.SERVER_ROOT+I.UPDATE_APK).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(DownloadNewVersionApkService.this, "版本更新失败，请稍后重试", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
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
