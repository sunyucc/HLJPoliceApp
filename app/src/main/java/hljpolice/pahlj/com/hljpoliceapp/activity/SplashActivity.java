package hljpolice.pahlj.com.hljpoliceapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import hljpolice.pahlj.com.hljpoliceapp.HLJPoliceApplication;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.bean.Version;
import hljpolice.pahlj.com.hljpoliceapp.dao.NetDao;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;
import hljpolice.pahlj.com.hljpoliceapp.utils.MFGT;
import hljpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;

/**
 * 新手向导
 */
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private final long sleepTime = 2000;        //  闪屏时间2s
    SplashActivity mContext;
    SharedPreferences preferences;             //  用于判断程序是否打开过
    @BindView(R.id.iv_syx)
    ImageView ivSyx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mContext = this;
        initData();
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String Imei = ((TelephonyManager) mContext
                .getSystemService(TELEPHONY_SERVICE)).getDeviceId();     //获取设备的IMEI号
        L.e("Imer=" + Imei);
    }

    private void initData() {
        NetDao.updateApp(mContext, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    Gson gson = new Gson();
                    try {
                        Version version = gson.fromJson(result, Version.class);
                        HLJPoliceApplication.getInstance().setVersion(version);
                        if (Integer.parseInt(version.getComm().getSyx()) != 0) {
                            ivSyx.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        Toast.makeText(SplashActivity.this, "获取版本信息失败,请稍后再试!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String error) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferences.getBoolean("isOpened", false)) {
                    MFGT.gotoMainActivity(mContext);
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isOpened", true);
                    editor.commit();
                    MFGT.gotoNewLeadActivity(mContext);
                }
                MFGT.finish(mContext);
            }
        }, sleepTime);
    }

    @Override
    public void onBackPressed() {
    }
}