package hljpolice.pahlj.com.hljpoliceapp.utils;

import android.app.Activity;
import android.content.Intent;

import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.activity.MainActivity;
import hljpolice.pahlj.com.hljpoliceapp.activity.XiangDaoActivity;

/**
 * 跳转activity和关闭activity的工具类
 */
public class  MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public static void gotoNewLeadActivity(Activity context) {
        startActivity(context, XiangDaoActivity.class);
    }
}
