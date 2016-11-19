package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hlpolice.pahlj.com.hljpoliceapp.I;
import hlpolice.pahlj.com.hljpoliceapp.R;
import hlpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hlpolice.pahlj.com.hljpoliceapp.utils.L;
import hlpolice.pahlj.com.hljpoliceapp.utils.Nav_Resource_Icon;


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
    GeRenFragment mPersonCenterFragment;
    ShouyeFragment mHomePageFragment;
    GongNengFragment mFunctionFragment;
    ShiXiangFragment mSafeFragment;
    FunctionBean extFunction;
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.rb_center)
    RadioButton rbCenter;
    @BindView(R.id.menu)
    LinearLayout menu;
    private Nav_Resource_Icon nri;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity.onCreate");
        nri = new Nav_Resource_Icon(this, 4 ,menu);
        nri.setOnImageChanageListener(imageChangedListener);
        super.onCreate(savedInstanceState);

    }


    private void initFragment() {
        mFragments = new Fragment[4];
        mPersonCenterFragment = new GeRenFragment();
        mHomePageFragment = new ShouyeFragment();
        mFunctionFragment = new GongNengFragment();
        mSafeFragment = new ShiXiangFragment();
        mFragments[0] = mHomePageFragment;
        mFragments[1] = mFunctionFragment;
        mFragments[2] = mSafeFragment;
        mFragments[3] = mPersonCenterFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_con, mHomePageFragment)
                .add(R.id.fragment_con, mFunctionFragment)
                .add(R.id.fragment_con, mSafeFragment)
                .add(R.id.fragment_con, mPersonCenterFragment)
                .hide(mFunctionFragment)
                .hide(mPersonCenterFragment)
                .hide(mSafeFragment)
                .show(mHomePageFragment)
                .commit();
    }

    @Override
    protected void initView() {
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
                setRadioButtonDrawableTop(mRb[i], i+1, false);
            } else {
                setRadioButtonDrawableTop(mRb[i], i+1, true);
            }
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * 动态设置2，3，4按钮的图片文字
     * @param funcList
     */
    public void setExtFuncData(List<FunctionBean> funcList) {
        for (FunctionBean func : funcList) {
            if ("01".equals(func.getMklb())) {
                rbZixun.setText(func.getData().get(0).getMkmc());
                mFunctionFragment.setUrl(func.getData().get(0).getQqdz());
                nri.setImageUrl(func.getData().get(0).getTbdz(), 2);
            } else if ("02".equals(func.getMklb())) {
                rbShiXing.setText(func.getData().get(0).getMkmc());
                mSafeFragment.setUrl(func.getData().get(0).getQqdz());
                nri.setImageUrl(func.getData().get(0).getTbdz(), 3);
            } else if ("03".equals(func.getMklb())) {
                rbCenter.setText(func.getData().get(0).getMkmc());
                mPersonCenterFragment.setUrl(func.getData().get(0).getQqdz());
                nri.setImageUrl(func.getData().get(0).getTbdz(), 4);
            }
        }
        setRadioButtonDrawableTop(rbShouye, 1, false);
    }
    /**
     * 设置按钮的图片
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

}