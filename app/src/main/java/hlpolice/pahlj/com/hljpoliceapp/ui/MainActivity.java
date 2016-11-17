package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import hlpolice.pahlj.com.hljpoliceapp.I;
import hlpolice.pahlj.com.hljpoliceapp.R;
import hlpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hlpolice.pahlj.com.hljpoliceapp.dao.NetDao;
import hlpolice.pahlj.com.hljpoliceapp.utils.GetHttpImage;
import hlpolice.pahlj.com.hljpoliceapp.utils.L;


public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.rb_shouye)
    RadioButton rb_shouye;
    @BindView(R.id.rb_zixun)
    RadioButton mRbZixun;
    @BindView(R.id.rb_shixiang)
    RadioButton rbCategory;

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
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.rb_center)
    RadioButton rbContact;
    @BindView(R.id.menu)
    LinearLayout menu;

    private Map<String,Bitmap> snavImage;

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
        snavImage = new HashMap<>();
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

    public void setExtFuncData(FunctionBean funcData,int x) {
        RadioButton rb = null;
        switch (x) {
            case 1:
                rb = mRbZixun;
                break;
            case 2:
                rb = rbCategory;
                break;
            case 3:
                rb = rbContact;
                break;
        }
        rb.setText(funcData.getData().get(0).getMkmc());
        NetDao.downloadImage(funcData.getData().get(0).getTbdz(),new GetHttpImage.CallBackListener() {
            @Override
            public void Callback(Bitmap resultBmp) {
                snavImage.put("",resultBmp);
                //setRadioButtonDrawableTop(rb,resultBmp,true);
            }
        });
        mFunctionFragment.setUrl(funcData.getData().get(0).getQqdz());

    }

//    public void setExtSxData(FunctionBean funcData) {
//        extFunction = funcData;
//        rbCategory.setText(funcData.getData().get(0).getMkmc());
//        mSafeFragment.setUrl(funcData.getData().get(0).getQqdz());
//        NetDao.downloadImage("http://www.83027110.com/stwx/images/1_07.png", new GetHttpImage.CallBackListener() {
//            @Override
//            public void Callback(Bitmap resultBmp) {
//                //setRadioButtonDrawableTop(rbCategory,resultBmp,true);
//            }
//        });
//
//    }

    public void setRadioButtonDrawableTop(RadioButton rb,Bitmap bmp,boolean isgray) {
        Drawable drawableTop;
        if (isgray) {
            drawableTop = new BitmapDrawable(null, grey(bmp));
        } else {
            drawableTop = new BitmapDrawable(null,bmp);
        }

        rb.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null,null);

    }
//    public void setExtGrData(FunctionBean funcData) {
//        extFunction = funcData;
//        rbContact.setText(funcData.getData().get(0).getMkmc());
//        mPersonCenterFragment.setUrl(funcData.getData().get(0).getQqdz());
//
//    }

    public static final Bitmap grey(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap faceIconGreyBitmap = Bitmap
                .createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(faceIconGreyBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return faceIconGreyBitmap;
    }


}