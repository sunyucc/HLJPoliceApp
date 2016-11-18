package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;

import hlpolice.pahlj.com.hljpoliceapp.R;
import hlpolice.pahlj.com.hljpoliceapp.utils.L;
import hlpolice.pahlj.com.hljpoliceapp.utils.MFGT;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity .class.getSimpleName();

    private final long sleepTime = 2000;
    SplashActivity mContext;
    SharedPreferences preferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        preferences =getSharedPreferences("user", Context.MODE_PRIVATE);
       String Imei = ((TelephonyManager)mContext.getSystemService(TELEPHONY_SERVICE))
                .getDeviceId();
        L.e("Imer=" + Imei);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferences.getBoolean("isOpened", false)) {
                    MFGT.gotoMainActivity(mContext);
                    MFGT.finish(mContext);
                } else {
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putBoolean("isOpened", true);
                    editor.commit();
                    MFGT.gotoNewLeadActivity(mContext);
                    MFGT.finish(mContext);
                }
            }
        },sleepTime);
    }
}