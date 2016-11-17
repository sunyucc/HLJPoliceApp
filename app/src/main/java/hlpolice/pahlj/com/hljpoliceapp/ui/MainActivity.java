package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hlpolice.pahlj.com.hljpoliceapp.I;
import hlpolice.pahlj.com.hljpoliceapp.R;
import hlpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hlpolice.pahlj.com.hljpoliceapp.utils.L;


public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.rb_shouye)
    RadioButton rb_shouye;
    @BindView(R.id.rb_zixun)
    RadioButton mRbZixun;
    @BindView(R.id.rbCategory)
    RadioButton rbCategory;
    @BindView(R.id.rbContact)
    RadioButton rbContact;
    Fragment[] mFragments;
    int index = 0;
    int currentIndex;
    RadioButton[] mRb;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    PersonCenterFragment mPersonCenterFragment;
    HomePageFragment mHomePageFragment;
    FunctionFragment mFunctionFragment;
    SafeFragment mSafeFragment;
    FunctionBean extFunction;
    @BindView(R.id.txt_left)
    TextView txtLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity.onCreate");
        super.onCreate(savedInstanceState);

    }


    private void initFragment() {
        mFragments = new Fragment[4];
        mPersonCenterFragment = new PersonCenterFragment();
        mHomePageFragment = new HomePageFragment();
        mFunctionFragment = new FunctionFragment();
        mSafeFragment = new SafeFragment();
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
        mRb = new RadioButton[]{rb_shouye, mRbZixun, rbCategory, rbContact};
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
            case R.id.rbCategory:
                index = 2;
                break;
            case R.id.rbContact:
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
                mRb[i].setChecked(true);
            } else {
                mRb[i].setChecked(false);
            }
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void setExtFuncData(FunctionBean funcData) {
        extFunction = funcData;
        mRbZixun.setText(funcData.getData().get(0).getMkmc());
        mFunctionFragment.setUrl(funcData.getData().get(0).getQqdz());

    }

    public void setExtSxData(FunctionBean funcData) {
        extFunction = funcData;
        rbCategory.setText(funcData.getData().get(0).getMkmc());
        mSafeFragment.setUrl(funcData.getData().get(0).getQqdz());

    }

    public void setExtGrData(FunctionBean funcData) {
        extFunction = funcData;
        rbContact.setText(funcData.getData().get(0).getMkmc());
        mPersonCenterFragment.setUrl(funcData.getData().get(0).getQqdz());

    }
}