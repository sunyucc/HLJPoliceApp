package hlpolice.pahlj.com.hljpoliceapp.utils;

import android.app.Activity;
import android.content.Intent;

import hlpolice.pahlj.com.hljpoliceapp.R;
import hlpolice.pahlj.com.hljpoliceapp.ui.HtmlActivity;
import hlpolice.pahlj.com.hljpoliceapp.ui.MainActivity;
import hlpolice.pahlj.com.hljpoliceapp.ui.NewLeadActivity;


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
        startActivity(context, NewLeadActivity.class);
    }
    public static void gotoHtmlActivity(MainActivity mContext) {
        startActivity(mContext, HtmlActivity.class);
    }
}
