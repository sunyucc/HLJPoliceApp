package hljpolice.pahlj.com.hljpoliceapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;
import hljpolice.pahlj.com.hljpoliceapp.utils.Nav_Resource_Icon;



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
    RadioButton rbCenter;
    @BindView(R.id.menu)
    LinearLayout menu;
    private Nav_Resource_Icon nri;
    private String fileName;
    private UpdateCartReceiver mReceiver;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity.onCreate");
        nri = new Nav_Resource_Icon(this, 4, menu);
        nri.setOnImageChanageListener(imageChangedListener);
        super.onCreate(savedInstanceState);

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
        mRb = new RadioButton[]{rbShouye, rbZixun, rbShiXing, rbCenter};
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(I.MENU_TITLE);
        txtLeft.setVisibility(View.GONE);
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
                index = 1;
                break;
            case R.id.rb_shixiang:
                index = 2;
                break;
            case R.id.rb_center:
                index = 3;
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
                setRadioButtonDrawableTop(mRb[i], i +1, false);
                mRb[i].setTextColor(Color.rgb(0,90,181));
            } else {
                setRadioButtonDrawableTop(mRb[i], i + 1, true);
                mRb[i].setTextColor(Color.rgb(180,180,180));
            }
        }
    }


    @Override
    public void onBackPressed() {
        finish();
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
                mFunctionFragment1.setUrl(func.getData().get(0).getQqdz());
                nri.setImageUrl(func.getData().get(0).getTbdz(), 2);
            } else if ("02".equals(func.getMklb())) {
                rbShiXing.setText(func.getData().get(0).getMkmc());
                mFunctionFragment2.setUrl(func.getData().get(0).getQqdz());
                nri.setImageUrl(func.getData().get(0).getTbdz(), 3);
            } else if ("03".equals(func.getMklb())) {
                rbCenter.setText(func.getData().get(0).getMkmc());
                mFunctionFragment3.setUrl(func.getData().get(0).getQqdz());
                nri.setImageUrl(func.getData().get(0).getTbdz(), 4);
            }
        }
        setRadioButtonDrawableTop(rbShouye, 1, false);
        rbShouye.setTextColor(Color.rgb(58,97,173));
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
            setRadioButtonDrawableTop(rbCenter, 4, true);
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
    protected void onDestroy() {
        this.unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}