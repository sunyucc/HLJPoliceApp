package hljpolice.pahlj.com.hljpoliceapp.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hljpolice.pahlj.com.hljpoliceapp.HLJPoliceApplication;
import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hljpolice.pahlj.com.hljpoliceapp.bean.Version;
import hljpolice.pahlj.com.hljpoliceapp.dao.NetDao;
import hljpolice.pahlj.com.hljpoliceapp.fragment.GongNengFragment;
import hljpolice.pahlj.com.hljpoliceapp.fragment.ShouyeFragment;
import hljpolice.pahlj.com.hljpoliceapp.service.DownloadNewVersionApkService;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;
import hljpolice.pahlj.com.hljpoliceapp.utils.Nav_Resource_Icon;
import hljpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;


public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.rb_shouye)
    RadioButton rbShouye;
    @BindView(R.id.rb_zixun)
    RadioButton rbZixun;
    @BindView(R.id.rb_shixiang)
    RadioButton rbShiXing;

    Fragment[] mFragments;
    int index = 0;
    int currentIndex;
    RadioButton[] mRb;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    ShouyeFragment mHomePageFragment;
    GongNengFragment mFunctionFragment1;
    GongNengFragment mFunctionFragment2;
    GongNengFragment mFunctionFragment3;
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.rb_center)
    RadioButton mRbPersonCenter;
    @BindView(R.id.menu)
    LinearLayout menu;
    @BindView(R.id.iv_update)
    ImageView mIvUpdate;
    private Nav_Resource_Icon nri;
    private String fileName;
    private UpdateCartReceiver mReceiver;
    private long firstTime = 0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        nri = new Nav_Resource_Icon(this, 4, menu);
        L.i("MainActivity.onCreate");
        super.onCreate(savedInstanceState);
        nri.setOnImageChanageListener(imageChangedListener);
        checkVersion();

    }

    private void checkVersion() {
        NetDao.updateApp(HLJPoliceApplication.application, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String json) {
                L.e(TAG, "json:" + json);
                if (json != null) {
                    Gson gson = new Gson();
                    final Version version = gson.fromJson(json, Version.class);
                    L.e(TAG, "Version:" + version);
                    // 大于当前版本应该更新Apk
                    L.e("getcurrentVersion" + HLJPoliceApplication.getInstance().getCurrentVersion());
                    L.e("getvercode" + version.getVerCode());
                    if (Integer.parseInt(version.getVerCode()) > HLJPoliceApplication.getInstance().getCurrentVersion()) {
                        // 启动更新App服务
                        mIvUpdate.setVisibility(View.VISIBLE);
                        mIvUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateVersion(version);
                            }
                        });

                    } else {
                        mIvUpdate.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void updateVersion(final Version version) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("有新版本啦!");
        builder.setMessage("是否更新新版本");
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {//添加确定按钮
            @Override

            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, DownloadNewVersionApkService.class);
                intent.putExtra("app", version);
                startService(intent);

            }

        }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮


            @Override

            public void onClick(DialogInterface dialog, int which) {//响应事件

                // TODO Auto-generated method stub


            }

        }).show();//在按键响应事件中
    }


    private void initFragment() {
        mFragments = new Fragment[4];
        mHomePageFragment = new ShouyeFragment();
        mFunctionFragment1 = new GongNengFragment();
        mFunctionFragment2 = new GongNengFragment();
        mFunctionFragment3 = new GongNengFragment();

        mFragments[0] = mHomePageFragment;
        mFragments[1] = mFunctionFragment1;
        mFragments[2] = mFunctionFragment2;
        mFragments[3] = mFunctionFragment3;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_con, mHomePageFragment)
                .add(R.id.fragment_con, mFunctionFragment1)
                .add(R.id.fragment_con, mFunctionFragment2)
                .add(R.id.fragment_con, mFunctionFragment3)
                .hide(mFunctionFragment1)
                .hide(mFunctionFragment2)
                .hide(mFunctionFragment3)
                .show(mHomePageFragment)
                .commit();
    }

    @Override
    protected void initView() {
        IntentFilter filter = new IntentFilter(I.UPDATE_APP);
        mReceiver = new UpdateCartReceiver();
        this.registerReceiver(mReceiver, filter);
        mRb = new RadioButton[]{rbShouye, rbZixun, rbShiXing, mRbPersonCenter};
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(I.MENU_TITLE);
        txtLeft.setVisibility(View.GONE);
        rbZixun.setEnabled(false);
        rbShiXing.setEnabled(false);
        mRbPersonCenter.setEnabled(false);
    }

    @Override
    protected void initData() {
        initFragment();
    }

    @Override
    protected void setListener() {

    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.rb_shouye:
                index = 0;
                break;
            case R.id.rb_zixun:
                    mFunctionFragment1.setUrl(rbZixun.getTag().toString());
                    index = 1;
                break;
            case R.id.rb_shixiang:
                    mFunctionFragment2.setUrl(rbShiXing.getTag().toString());
                    index = 2;
                break;
            case R.id.rb_center:
                    mFunctionFragment3.setUrl(mRbPersonCenter.getTag().toString());
                    index=3;
                break;
        }
        setFragment();
        if (index == 0) {
            txtTitle.setText("平安黑龙江");
        } else {
            txtTitle.setText(mRb[index].getText());
            L.e(TAG, String.valueOf(mRb[index].getText()));
        }
    }

    private void setFragment() {
        L.e(TAG, "index:" + index);
        if (index != currentIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragments[currentIndex]);
            if (!mFragments[index].isAdded()) {
                ft.add(R.id.fragment_con, mFragments[index]);

            }
            ft.show(mFragments[index]).commit();
        }
        setRadioButtonStatus();
        currentIndex = index;
    }

    private void setRadioButtonStatus() {
        for (int i = 0; i < mRb.length; i++) {
            if (i == index) {
                setRadioButtonDrawableTop(mRb[i], i + 1, false);
                mRb[i].setTextColor(Color.rgb(0, 90, 181));
            } else {
                setRadioButtonDrawableTop(mRb[i], i + 1, true);
                mRb[i].setTextColor(Color.rgb(180, 180, 180));
            }
        }
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                                                    //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 动态设置首页下方按钮的图片文字
     *
     * @param funcList
     */
    public void setExtFuncData(List<FunctionBean> funcList) {
        for (FunctionBean func : funcList) {
            if ("01".equals(func.getMklb())) {
                rbZixun.setText(func.getData().get(0).getMkmc());
                rbZixun.setTag(func.getData().get(0).getQqdz());
                rbZixun.setEnabled(true);
//                mFunctionFragment1.setUrl(func.getData().get(0).getQqdz());
                nri.setImageUrl(func.getData().get(0).getTbdz(), 2);
            } else if ("02".equals(func.getMklb())) {
                L.e("url====" + func.getData().get(0).getQqdz());
                rbShiXing.setText(func.getData().get(0).getMkmc());
                rbShiXing.setTag(func.getData().get(0).getQqdz());
                rbShiXing.setEnabled(true);
//                mFunctionFragment2.setUrl(func.getData().get(0).getQqdz());
                nri.setImageUrl(func.getData().get(0).getTbdz(), 3);
            } else if ("03".equals(func.getMklb())) {
                mRbPersonCenter.setText(func.getData().get(0).getMkmc());
                mRbPersonCenter.setTag(func.getData().get(0).getQqdz());
//                mFunctionFragment3.setUrl(func.getData().get(0).getQqdz());
                mRbPersonCenter.setEnabled(true);
                nri.setImageUrl(func.getData().get(0).getTbdz(), 4);
            }
        }
        setRadioButtonDrawableTop(rbShouye, 1, false);
        rbShouye.setTextColor(Color.rgb(58, 97, 173));
    }

    /**
     * 设置按钮的图片
     *
     * @param rb
     * @param x
     * @param isgray
     */
    public void setRadioButtonDrawableTop(RadioButton rb, int x, boolean isgray) {
        Drawable drawableTop;
        if (isgray) {
            drawableTop = new BitmapDrawable(null, nri.getGrayNavIcon(x));
        } else {
            drawableTop = new BitmapDrawable(null, nri.getNavicon(x));
        }

        rb.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null, null);

    }

    private Nav_Resource_Icon.OnImageChangedListener imageChangedListener = new Nav_Resource_Icon.OnImageChangedListener() {
        @Override
        public void isDown(int x) {
            setRadioButtonDrawableTop(rbZixun, 2, true);
            setRadioButtonDrawableTop(rbShiXing, 3, true);
            setRadioButtonDrawableTop(mRbPersonCenter, 4, true);
        }
    };

    private class UpdateCartReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            fileName = intent.getStringExtra(I.FILE_NAME);
            L.e(TAG, "file:" + fileName);
            installAPK(fileName);
        }
    }

    private void installAPK(String filename) {
        // TODO Auto-generated method stub
        // 安装程序的apk文件路径
        String fileName = filename;
        // 创建URI
        Uri uri = Uri.fromFile(new File(fileName));
        // 创建Intent意图
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 设置Uri和类型
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        // 执行意图进行安装
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mReceiver);
        super.onDestroy();
    }

}