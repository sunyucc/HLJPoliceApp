package hljpolice.pahlj.com.hljpoliceapp.dao;

import android.content.Context;

import java.io.File;

import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hljpolice.pahlj.com.hljpoliceapp.bean.NewsBean;
import hljpolice.pahlj.com.hljpoliceapp.utils.GetHttpImage;
import hljpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;
import okhttp3.Response;

/**
 * Created by sunyu on 2016/11/15.
 */

public class NetDao {
    /**
     * 下载模块信息
     * @param context
     * @param listener
     */
    public static void downloadMoudles(Context context,OkHttpUtils.OnCompleteListener<FunctionBean[]> listener){
        OkHttpUtils<FunctionBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.FUNCTION_MOUDLES)
                .targetClass(FunctionBean[].class)
                .post()
                .execute(listener);
    }

    /**
     * 下载新闻信息
     * @param context
     * @param listener
     */
     public static void downloadNews(Context context,OkHttpUtils.OnCompleteListener<NewsBean> listener){
        OkHttpUtils<NewsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.NEWS_MOUDLES)
                .targetClass(NewsBean.class)
                .execute(listener);
    }

    public static void downloadImage(String url,GetHttpImage.CallBackListener listener) {
        GetHttpImage http = new GetHttpImage(url);
        http.setListener(listener);
        http.getImage();
    }

    public static void downVersionInfo(Context context,OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.VERSION_INFO)
                .targetClass(String.class)
                .execute(listener);
    }
    public static void downApkFile(Context context, Response response, File file, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.downloadFile(response,file);
        utils.execute(listener);
    }
    public static void updateApp(Context mContext, OkHttpUtils.OnCompleteListener<String> listener) {

        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.VERSION_INFO)
                .targetClass(String.class)
                .execute(listener);
    }
}
