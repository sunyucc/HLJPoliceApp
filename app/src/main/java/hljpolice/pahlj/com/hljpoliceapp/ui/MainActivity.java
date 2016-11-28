package hljpolice.pahlj.com.hljpoliceapp.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import hljpolice.pahlj.com.hljpoliceapp.download.DownloadApkFileService;
import hljpolice.pahlj.com.hljpoliceapp.download.DownloadBinder;
import hljpolice.pahlj.com.hljpoliceapp.fragment.GongNengFragment;
import hljpolice.pahlj.com.hljpoliceapp.fragment.ShiXiangFragment;
import hljpolice.pahlj.com.hljpoliceapp.fragment.ShouyeFragment;
import hljpolice.pahlj.com.hljpoliceapp.listener.ProgressListener;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;
import hljpolice.pahlj.com.hljpoliceapp.utils.NavResourceIcon;
import hljpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;


@SuppressWarnings("ALL")
public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.rb_shouye)
    RadioButton mRbShouYe;
    @BindView(R.id.rb_zixun)
    RadioButton mRbZiXun;
    @BindView(R.id.rb_shixiang)
    RadioButton mRbShiXing;

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
    @BindView(R.id.ll_jindu)
    LinearLayout mLinearlayoutJinDu;
    @BindView(R.id.down_pb)
    ProgressBar downPb;
    private NavResourceIcon nri;
    private boolean isGeRenClicked = true;
    private boolean isZiXunClicked = true;
    private String fileName;
    private UpdateCartReceiver mReceiver;
    RelativeLayout mRlTitle;
    private int fileProgress;
    private DownloadBinder binder ;
    private Version version ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        super.onCreate(savedInstanceState);
        checkVersion();

    }
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            L.e("onServiceDisconnected");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (DownloadBinder) service;
            binder.setDownloadFile(version.getApkname());
            binder.setProgressListener(new ProgressListener() {
                @Override
                public void progressValue(int value) {
                    downPb.setProgress(value);
                }

                @Override
                public void downloadComplete() {
//                    MainActivity.this.unbindService(serviceConnection);
                    installAPK(version.getApkname());
                }
            });
            binder.startDownload();
        }
    };

    private void checkVersion() {
        NetDao.updateApp(HLJPoliceApplication.application, listener);
    }

    private OkHttpUtils.OnCompleteListener listener = new OkHttpUtils.OnCompleteListener<String>() {
        @Override
        public void onSuccess(String json) {
            L.e(TAG, "json:" + json);
            if (json != null) {
                Gson gson = new Gson();
                try {
                    version = gson.fromJson(json, Version.class);
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
    };

    /**
     * 更新版本对话框
     *
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
                mLinearlayoutJinDu.setVisibility(View.VISIBLE);
                mIvUpdate.setVisibility(View.GONE);
                Intent intent = new Intent(MainActivity.this,DownloadApkFileService.class);
                MainActivity.this.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

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
        filter.addAction("hljpolice.pahlj.com.hljpoliceapp.service.DownloadNewVersionApkService");
        this.registerReceiver(mReceiver, filter);
        mRb = new RadioButton[]{mRbShouYe, mRbZiXun, mRbShiXing, mRbPersonCenter};
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(I.MENU_TITLE);
        txtLeft.setVisibility(View.GONE);
        mRbShouYe.setEnabled(false);
        mRbZiXun.setEnabled(false);
        mRbShiXing.setEnabled(false);
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
     *
     * @param view
     */
    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.rb_shouye:
                index = 0;
                isZiXunClicked = true;
                isGeRenClicked = true;
                break;
            case R.id.rb_zixun:
                if (isZiXunClicked) {
                    isZiXunClicked = false;
                    mFunctionFragment1.setUrl(mRbZiXun.getTag().toString());
                    index = 1;
                }
                isGeRenClicked = true;
                break;
            case R.id.rb_shixiang:
                index = 2;
                isGeRenClicked = true;
                isZiXunClicked = true;
                break;
            case R.id.rb_center:
                if (isGeRenClicked) {
                    mFunctionFragment3.setUrl(mRbPersonCenter.getTag().toString());
                    index = 3;
                    isGeRenClicked = false;
                }
                isZiXunClicked = true;
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
            ft.hide(mFragments[currentIndex]);              // 隐藏前一个Fragment
            if (!mFragments[index].isAdded()) {
                ft.add(R.id.fragment_con, mFragments[index]);

            }
            ft.show(mFragments[index]).commit();
        }
        setRadioButtonStatus();    //   设置按钮的状态
        currentIndex = index;
    }

    private void setRadioButtonStatus() {
        for (int i = 0; i < mRb.length; i++) {
            if (i == index) {
                setRadioButtonDrawableTop(mRb[i], i + 1, false);        //设置RadioButton的图片
                mRb[i].setTextColor(Color.rgb(0, 90, 181));             //设置RadioButton的文字颜色
            } else {
                setRadioButtonDrawableTop(mRb[i], i + 1, true);
                mRb[i].setTextColor(Color.rgb(180, 180, 180));
            }
        }
    }


    /**
     * 动态设置首页下方按钮的图片文字
     *
     * @param funcList
     */
    public void setExtFuncData(List<FunctionBean> funcList) {
        if (nri == null) {
            nri = new NavResourceIcon(this, menu);
            nri.setOnImageChanageListener(imageChangedListener);
        }
        for (FunctionBean func : funcList) {
            if ("01".equals(func.getMklb())) {
                mFunctionFragment1.setTxtTitle(func.getData().get(0).getMkmc());        //设置第二页的标题
                mRbZiXun.setText(func.getData().get(0).getMkmc());       //设置第二个按钮的文字
                mRbZiXun.setTag(func.getData().get(0).getQqdz());        //设置第二页加载的网址
                mRbZiXun.setEnabled(true);                               //当mRbZiXun设置tag以后使控件可用，否则会出现空指针
                nri.setImageUrl(func.getData().get(0).getTbdz(), 2);    //设置按钮图片的url
            } else if ("02".equals(func.getMklb())) {

                mRbShiXing.setText(func.getData().get(0).getMkmc());
//                mRbShiXing.setTag(func.getData().get(0).getQqdz());
                mRbShiXing.setEnabled(true);
                mFunctionFragment2.setUrl(func.getData().get(0).getQqdz());
                nri.setImageUrl(func.getData().get(0).getTbdz(), 3);
            } else if ("03".equals(func.getMklb())) {
                mFunctionFragment3.setTxtTitle(func.getData().get(0).getMkmc());
                mRbPersonCenter.setText(func.getData().get(0).getMkmc());
                mRbPersonCenter.setTag(func.getData().get(0).getQqdz());
                mRbPersonCenter.setEnabled(true);
                nri.setImageUrl(func.getData().get(0).getTbdz(), 4);
                L.e("nri-a"+func.getData().get(0).getQqdz());
            }
        }
        mRbShouYe.setEnabled(true);
        setRadioButtonDrawableTop(mRbShouYe, 1, false);      // 设置第一个按钮的图片
        mRbShouYe.setTextColor(Color.rgb(58, 97, 173));      // 设置第一个按钮的颜色
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
                    setRadioButtonDrawableTop(mRbZiXun, x, true);  //设置第二个按钮的图片
                    break;
                case 3:
                    setRadioButtonDrawableTop(mRbShiXing, x, true);//设置第三个按钮的图片
                    break;
                case 4:
                    setRadioButtonDrawableTop(mRbPersonCenter, x, true);//设置第四个按钮的图片
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
     *
     * @param filename
     */
    private void installAPK(String filename) {
        // TODO Auto-generated method stub
        // 安装程序的apk文件路径
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        L.e("dir==="+dir.toString());
        fileName = dir+"/"+filename;
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
        mLinearlayoutJinDu.setVisibility(View.GONE);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mReceiver);     //注销广播
        super.onDestroy();
    }
}