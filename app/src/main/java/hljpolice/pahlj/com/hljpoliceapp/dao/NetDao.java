package hljpolice.pahlj.com.hljpoliceapp.dao;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Map;

import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hljpolice.pahlj.com.hljpoliceapp.bean.NewsBean;
import hljpolice.pahlj.com.hljpoliceapp.bean.ShiXiangBean;
import hljpolice.pahlj.com.hljpoliceapp.bean.ShiXiangModuleBean;
import hljpolice.pahlj.com.hljpoliceapp.utils.GetHttpImage;
import hljpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;

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

    public static void updateApp(Context mContext, OkHttpUtils.OnCompleteListener<String> listener) {

        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.VERSION_INFO)
                .targetClass(String.class)
                .execute(listener);
    }
    public static void downShiXiang(Context mContext, int pageIndex, Map<String,String> data, OkHttpUtils.OnCompleteListener<ShiXiangBean> listener) {

        OkHttpUtils<ShiXiangBean> utils = new OkHttpUtils<>(mContext);
        Gson gson = new Gson();
        String json = "{}";
        if (data!=null) {
            json = gson.toJson(data);
        }
        utils.setRequestUrl(I.SHIXIANG_SEARCH)
                .post()
                .addParam(I.PAGE_INDEX,String.valueOf(pageIndex))
                .addParam(I.PAGE_SIZE,String.valueOf(20))
                .addParam("json",json)
                .targetClass(ShiXiangBean.class)
                .execute(listener);
    }
 public static void downShiXiangModule(Context mContext, OkHttpUtils.OnCompleteListener<ShiXiangModuleBean[]> listener) {

        OkHttpUtils<ShiXiangModuleBean[]> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.SHIXIANG_MODULE_NAME)
                .targetClass(ShiXiangModuleBean[].class)
                .execute(listener);
    }


}
