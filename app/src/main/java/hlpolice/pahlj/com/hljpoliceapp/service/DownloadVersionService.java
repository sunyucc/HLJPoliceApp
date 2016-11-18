//package hlpolice.pahlj.com.hljpoliceapp.service;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.Environment;
//import android.os.IBinder;
//import android.os.Message;
//import android.widget.Toast;
//
//import java.io.File;
//import java.io.IOException;
//
//import hlpolice.pahlj.com.hljpoliceapp.HLJPoliceApplication;
//import hlpolice.pahlj.com.hljpoliceapp.bean.ApkBean;
//import hlpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;
//import okhttp3.Callback;
//import okhttp3.Response;
//
//
//public class DownloadVersionService extends Service {
//    public DownloadVersionService() {
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        final OkHttpUtils<ApkBean> utils=new OkHttpUtils<>();
//        utils.url(HLJPoliceApplication.ROOT_URL+HLJPoliceApplication.APP_FILE_NAME)
//                .targetClass(ApkBean.class)
//                .doInBackground(new Callback() {
//                    @Override
//                    public void onFailure(Request request, IOException e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(Response response) throws IOException {
//                        File dir = Environment
//                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//                        File file = new File(dir, MyApplication.APP_FILE_NAME);
//                        utils.downloadFile(response,file);
//                    }
//                }).onPostExecute(new OkHttpUtils.OnCompleteListener<Message>() {
//            @Override
//            public void onSuccess(Message msg) {
//                switch (msg.what) {
//                    case OkHttpUtils.DOWNLOADING_START:
//                        Toast.makeText(DownloadVersionService.this, "开始下载", Toast.LENGTH_SHORT).show();
//                        break;
//                    case OkHttpUtils.DOWNLOADING_FINISH:
//                        Toast.makeText(DownloadVersionService.this, "下载结束", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//        }).execute(null);
//        return super.onStartCommand(intent, flags, startId);
//    }
//}
