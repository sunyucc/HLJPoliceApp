package hljpolice.pahlj.com.hljpoliceapp.dao;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Map;

import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hljpolice.pahlj.com.hljpoliceapp.bean.NewsBean;
import hljpolice.pahlj.com.hljpoliceapp.bean.Repetition;
import hljpolice.pahlj.com.hljpoliceapp.bean.ShiXiangBean;
import hljpolice.pahlj.com.hljpoliceapp.bean.ShiXiangModuleBean;
import hljpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;

/**
 * Created by sunyu on 2016/11/15.
 */

public class NetDao {
    /**
     * 下载首页模块信息
     *
     * @param context
     * @param listener
     */
    public static void downloadMoudles(Context context, OkHttpUtils.OnCompleteListener<FunctionBean[]> listener) {
        OkHttpUtils<FunctionBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.SERVER_ROOT+I.FUNCTION_MOUDLES)
                .post()
                .targetClass(FunctionBean[].class)
                .post()
                .execute(listener);
    }
    /**
     * 下载首页轮播新闻信息
     *
     * @param context
     * @param listener
     */
    public static void downloadNews(Context context, OkHttpUtils.OnCompleteListener<NewsBean> listener) {
        OkHttpUtils<NewsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.SERVER_ROOT+I.NEWS_MOUDLES)
                .post()
                .targetClass(NewsBean.class)
                .execute(listener);
    }

    /**
     * 更新app，获取版本信息
     *
     * @param mContext
     * @param listener
     */
    public static void updateApp(Context mContext, OkHttpUtils.OnCompleteListener<String> listener) {

        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.VERSION_JIEKOU+I.VERSION_INFO)
                .targetClass(String.class)
                .execute(listener);
    }

    /**
     * 下载事项列表
     *
     * @param mContext
     * @param pageIndex
     * @param data
     * @param listener
     */
    public static void downShiXiang(Context mContext, int pageIndex, Map<String, String> data, OkHttpUtils.OnCompleteListener<ShiXiangBean> listener) {

        OkHttpUtils<ShiXiangBean> utils = new OkHttpUtils<>(mContext);
        Gson gson = new Gson();
        String json = "{}";
        if (data != null) {
            json = gson.toJson(data);
        }
        utils.setRequestUrl(I.SERVER_ROOT+I.SHIXIANG_SEARCH)
                .post()
                .addParam(I.PAGE_INDEX, String.valueOf(pageIndex))
                .addParam(I.PAGE_SIZE, String.valueOf(20))
                .addParam("json", json)
                .targetClass(ShiXiangBean.class)
                .execute(listener);
    }

    /**
     * 下载警种模块列表
     * @param mContext
     * @param listener
     */
    public static void downShiXiangModule(Context mContext, OkHttpUtils.OnCompleteListener<ShiXiangModuleBean[]> listener) {

        OkHttpUtils<ShiXiangModuleBean[]> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.SERVER_ROOT+I.SHIXIANG_MODULE_NAME)
                .post()
                .targetClass(ShiXiangModuleBean[].class)
                .execute(listener);
    }

    public static void djsUpload(Context context,int sxid) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.DJS_SERVER)
                .post()
                .addParam(I.DJS_SXID, String.valueOf(sxid))
                .targetClass(String.class)
                .execute(null);
    }
    public static void gainYzm(Context context,String phoneNum, OkHttpUtils.OnCompleteListener<Repetition> listener){
        OkHttpUtils<Repetition> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.YSRZ.GAIN_YZM)
                .addParam("mobile", phoneNum)
                .targetClass(Repetition.class)
                .execute(listener);
    }
    public static void verify(Context context,String phonenum,String yzm, OkHttpUtils.OnCompleteListener<Repetition> listener){
        OkHttpUtils<Repetition> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.YSRZ.YZMRZ_URL)
                .addParam("phone", phonenum)
                .addParam("yzm", yzm)
                .targetClass(Repetition.class)
                .execute(listener);
    }
    public static void repetition (Context context,String phoneNum, OkHttpUtils.OnCompleteListener<Repetition> listener){
        OkHttpUtils<Repetition> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.YSRZ.REPETITION_URL)
                .addParam("phone", phoneNum)
                .targetClass(Repetition.class)
                .execute(listener);
    }

}
