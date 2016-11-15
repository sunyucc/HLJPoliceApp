package hlpolice.pahlj.com.hljpoliceapp.dao;

import android.content.Context;

import hlpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hlpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;

/**
 * Created by sunyu on 2016/11/15.
 */

public class NetDao {
    public static void downloadMoudles(Context context,OkHttpUtils.OnCompleteListener<FunctionBean[]> listener){
        OkHttpUtils<FunctionBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl("/gyqq/htmlfuncApp/searchAll.go")
                .targetClass(FunctionBean[].class)
                .execute(listener);
    }
}
