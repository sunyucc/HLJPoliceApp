package hljpolice.pahlj.com.hljpoliceapp.download;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;

import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.listener.ProgressListener;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载更新文件
 * Created by Carklote on 2016/11/26.
 */

public class DownloadFile {
    private ProgressListener listener;
    private int totalCount;
    private int startCount ;
    private String downloadFilename;

    public void setProgressListener(ProgressListener listener) {
        this.listener = listener;
    }
    public void setFileName(String fileName){
        downloadFilename = fileName;
    }
    public void download(){

        // 启动更新下载文件
//        final Version bean = (Version) intent.getSerializableExtra("app");    //获取版本信息
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(I.VERSION_SERVER+downloadFilename).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Toast.makeText(DownloadNewVersionApkService.this, "版本更新失败，请稍后重试", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Intent intent = new Intent(I.UPDATE_APP);
                InputStream in = response.body().byteStream();
                totalCount = (int) response.body().contentLength();
                L.e("totalCount==="+totalCount);
                FileOutputStream fos = null;
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(dir, downloadFilename);
//                L.e(TAG, "file:" + file);
                fos = new FileOutputStream(file);
                int len;
                byte[] bArr = new byte[1024 * 4];
                while ((len = in.read(bArr)) != -1) {
                    fos.write(bArr, 0, len);
                    startCount += len ;
                    int value = getPercentage(startCount, totalCount);
                    if (value>0) {
                        listener.progressValue(value);
                    }
                }
//                L.e(TAG, "下载完成");
                    listener.downloadComplete();
//                intent.putExtra(I.FILE_NAME, file.toString());
//                sendBroadcast(intent);
                if (in != null) {
                    in.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
        });
    }

    /**
     * 设置进度值，最大为100
     * @param sCount
     * @param mValue
     * @return
     */
    private int getPercentage(int sCount,int mValue) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        float percent = (float) sCount /(float) mValue * 100;
        L.e("precent: " + percent);
//        if (percent % 1 ==0) {
        return Integer.valueOf(numberFormat.format(percent));
//        } else {
//            return 0;
//        }
    }
}
