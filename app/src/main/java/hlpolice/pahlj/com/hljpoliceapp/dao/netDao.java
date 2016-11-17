package hlpolice.pahlj.com.hljpoliceapp.dao;

import android.content.Context;

import hlpolice.pahlj.com.hljpoliceapp.I;
import hlpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hlpolice.pahlj.com.hljpoliceapp.bean.NewsBean;
import hlpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;

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

}
