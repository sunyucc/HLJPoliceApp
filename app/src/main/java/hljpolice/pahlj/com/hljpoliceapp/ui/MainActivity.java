package hljpolice.pahlj.com.hljpoliceapp.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import hljpolice.pahlj.com.hljpoliceapp.fragment.ShiXiangFragment;
import hljpolice.pahlj.com.hljpoliceapp.fragment.ShouyeFragment;
import hljpolice.pahlj.com.hljpoliceapp.service.DownloadNewVersionApkService;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;
import hljpolice.pahlj.com.hljpoliceapp.utils.NavResourceIcon;
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
    ShiXiangFragment mFunctionFragment2;
    GongNengFragment mFunctionFragment3;
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.rb_center)
    RadioButton mRbPersonCenter;
    @BindView(R.id.menu)
    RadioGroup menu;
    @BindView(R.id.iv_update)
    ImageView mIvUpdate;
    private NavResourceIcon nri;

    private String fileName;
    private UpdateCartReceiver mReceiver;
    private long firstTime = 0;
    RelativeLayout mRlTitle;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        super.onCreate(savedInstanceState);
        checkVersion();

    }

    private void checkVersion() {
        NetDao.updateApp(HLJPoliceApplication.application, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String json) {
                L.e(TAG, "json:" + json);
                if (json != null) {
                    Gson gson = new Gson();
                    try {
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
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "获取版本信息失败,请稍后再试!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    /**
     * 更新版本对话框
     * @param version
     */
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
                startService(intent);       // 隐式意图启动服务

            }

        }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮


            @Override

            public void onClick(DialogInterface dialog, int which) {//点击返回按钮取消下载关闭对话框
            }

        }).show();//在按键响应事件中
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        mFragments = new Fragment[4];
        mHomePageFragment = new ShouyeFragment();
        mFunctionFragment1 = new GongNengFragment();
        mFunctionFragment2 = new ShiXiangFragment();
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

    /**
     * 初始化控件
     */
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
        mRlTitle = (RelativeLayout) findViewById(R.id.tl_title);

    }

    @Override
    protected void initData() {
        initFragment();
    }

    @Override
    protected void setListener() {

    }

    /**
     * 按钮的点击事件
     * @param view
     */
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
//                    mFunctionFragment2.setUrl(rbShiXing.getTag().toString());
                    index = 2;
                break;
            case R.id.rb_center:
                    mFunctionFragment3.setUrl(mRbPersonCenter.getTag().toString());
                    index=3;
                break;
        }
        setFragment();
        if (index == 0) {
            mRlTitle.setVisibility(View.VISIBLE);
            txtTitle.setText("平安黑龙江");
        } else {
            mRlTitle.setVisibility(View.GONE);
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
        if (nri==null) {
            nri = new NavResourceIcon(this, menu);
            nri.setOnImageChanageListener(imageChangedListener);
        }
        for (FunctionBean func : funcList) {
            if ("01".equals(func.getMklb())) {
                mFunctionFragment1.setTxtTitle(func.getData().get(0).getMkmc());        //设置第二页的标题
                rbZixun.setText(func.getData().get(0).getMkmc());       //设置第二个按钮的文字
                rbZixun.setTag(func.getData().get(0).getQqdz());        //设置第二页加载的网址
                rbZixun.setEnabled(true);                               //当rbzixun设置tag以后使控件可用，否则会出现空指针
                nri.setImageUrl(func.getData().get(0).getTbdz(), 2);    //设置按钮图片的url
            } else if ("02".equals(func.getMklb())) {

//                mFunctionFragment2.setTxtTitle(func.getData().get(0).getMkmc());
                rbShiXing.setText(func.getData().get(0).getMkmc());
                rbShiXing.setTag(func.getData().get(0).getQqdz());
                rbShiXing.setEnabled(true);
                nri.setImageUrl(func.getData().get(0).getTbdz(), 3);
            } else if ("03".equals(func.getMklb())) {

                mFunctionFragment3.setTxtTitle(func.getData().get(0).getMkmc());
                mRbPersonCenter.setText(func.getData().get(0).getMkmc());
                mRbPersonCenter.setTag(func.getData().get(0).getQqdz());
                mRbPersonCenter.setEnabled(true);
                nri.setImageUrl(func.getData().get(0).getTbdz(), 4);
            }
        }
        setRadioButtonDrawableTop(rbShouye, 1, false);      // 设置第一个按钮的图片
        rbShouye.setTextColor(Color.rgb(58, 97, 173));      // 设置第一个按钮的颜色
    }

    /**
     * 设置按钮的图片
     *
     * @param rb
     * @param x
     * @param isgray
     */
    private void setRadioButtonDrawableTop(RadioButton rb, int x, boolean isgray) {
        Drawable drawableTop;
        if (isgray) { //是否将图片置成灰色
            drawableTop = nri.getGrayNavIcon(x);// new BitmapDrawable(null, nri.getGrayNavIcon(x));
        } else {
            drawableTop = nri.getNavicon(x); // new BitmapDrawable(null, nri.getNavicon(x));
        }

        rb.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null, null);

    }

    private NavResourceIcon.OnImageChangedListener imageChangedListener = new NavResourceIcon.OnImageChangedListener() {
        @Override
        public void isDown(int x) {
            switch (x) {
                case 2:
                    setRadioButtonDrawableTop(rbZixun,x,true);  //设置第二个按钮的图片
                    break;
                case 3:
                    setRadioButtonDrawableTop(rbShiXing,x,true);//设置第三个按钮的图片
                    break;
                case 4:
                    setRadioButtonDrawableTop(mRbPersonCenter,x,true);//设置第四个按钮的图片
            }
        }
    };

    /**
     * 用于接收安装apk的广播
     */
    private class UpdateCartReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            fileName = intent.getStringExtra(I.FILE_NAME); //通过广播得到要安装apk的文件名
            L.e(TAG, "file:" + fileName);
            installAPK(fileName);
        }
    }

    /**
     * 安装apk
     * @param filename
     */
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
        this.unregisterReceiver(mReceiver);//注销广播
        super.onDestroy();
    }

}