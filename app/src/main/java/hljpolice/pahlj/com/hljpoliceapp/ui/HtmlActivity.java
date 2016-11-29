package hljpolice.pahlj.com.hljpoliceapp.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.listener.OnWebPageChangedListener;
import hljpolice.pahlj.com.hljpoliceapp.slidingmenu.BaseSwipeBackActivity;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;
import hljpolice.pahlj.com.hljpoliceapp.utils.MFGT;
import hljpolice.pahlj.com.hljpoliceapp.utils.SysHelper;
import hljpolice.pahlj.com.hljpoliceapp.webutils.Gn_WebChromeClient;
import hljpolice.pahlj.com.hljpoliceapp.webutils.Gn_WebViewClient;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static hljpolice.pahlj.com.hljpoliceapp.R.id.webView;

/**
 * 用于显示点击Html的跳转
 */
@SuppressLint("SetJavaScriptEnabled")
public class HtmlActivity extends BaseSwipeBackActivity {
    private static final String TAG = HtmlActivity.class.getSimpleName();
    @BindView(webView)
    WebView mWebView;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_htmltitle)
    TextView tvHtmltitle;
    @BindView(R.id.progressBar)
    ProgressBar bar;
    @BindView(R.id.rl_layout)
    RelativeLayout rlLayout;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
    private Uri imageUri;
    private String mFailingUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        ButterKnife.bind(this);
        initView();
        initData();

        rlLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                SysHelper sys = new SysHelper(HtmlActivity.this);
                if (rlLayout.getHeight() > sys.getScreenHeight()) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                }
            }
        }, 300);
    }


    private void initData() {
        mWebView.setWebViewClient(new Gn_WebViewClient(this, pageListener));
        mWebView.setWebChromeClient(new Gn_WebChromeClient(bar, tvHtmltitle) {

            @Override

            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                String accept = "image/*";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    accept = fileChooserParams.getAcceptTypes()[0];
                }
                take(getParam(accept));
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                take(getParam("image/*"));
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
//                L.e("acceptType=" + acceptType);
                mUploadMessage = uploadMsg;
                take(getParam(acceptType));
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//                L.e("acceptType==" + acceptType);
                mUploadMessage = uploadMsg;
                take(getParam(acceptType));
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
            }
        });

        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                            v.requestFocusFromTouch();
                        }
                        break;
                }
                return false;
            }
        });
    }

    private Map<String, String> getParam(String accept){
        Map<String,String> fileMap = new HashMap<>();

            fileMap.put("accept", accept);
            if (accept.contains("image")) {
                fileMap.put("fileExt", ".jpg");
                fileMap.put("Storage", Environment.DIRECTORY_PICTURES);
                fileMap.put("MediaStore",MediaStore.ACTION_IMAGE_CAPTURE);
                fileMap.put("title", "上传图片");
            } else if (accept.contains("video")) {
                fileMap.put("fileExt", ".mp4");
                fileMap.put("Storage", Environment.DIRECTORY_MOVIES);
                fileMap.put("MediaStore",MediaStore.ACTION_VIDEO_CAPTURE);
                fileMap.put("title", "上传视频");
            }
        return fileMap;
    };

    private OnWebPageChangedListener pageListener = new OnWebPageChangedListener() {

        @Override
        public void pageCount(int count) {
        }
    };

    private void initView() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setAppCacheEnabled(true);
//        settings.setLoadWithOverviewMode(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true);
        mWebView.setVerticalScrollBarEnabled(true);
        L.e("url+++" + url);
        mWebView.loadUrl(url);
        mWebView.requestFocus();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                Log.e("result", result + "");
                if (result == null) {
//	            		mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;

                    Log.e("imageUri", imageUri + "");
                } else {
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                }
            }
        }
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }


    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {

        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {

            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        } else {
            mUploadCallbackAboveL.onReceiveValue(null);
            return;
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }
        return;
    }

    /**
     * 在webview中调用系统相机
     */
    private void take(Map<String ,String > param) {
//        fileMap.put("fileExt", ".jpg");
//        fileMap.put("Storage", Environment.DIRECTORY_PICTURES);
//        fileMap.put("MediaStore",MediaStore.ACTION_IMAGE_CAPTURE);
//        fileMap.put("title", "上传图片");
        File imageStorageDir = new File(getExternalStoragePublicDirectory(param.get("Storage")), "MyApp");
        // Create the storage directory if it does not exist
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + param.get("fileExt"));
        final Intent captureIntent = new Intent(param.get("MediaStore"));
        imageUri = Uri.fromFile(file);
        final List<Intent> cameraIntents = new ArrayList<>();
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);

        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType(param.get("accept"));

        Intent chooserIntent = Intent.createChooser(i, param.get("title"));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        HtmlActivity.this.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }


    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }


    protected boolean isSupportSwipeBack() {
        return true;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 返回键监听 点击返回如果有上一页面不会马上退出
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.removeAllViews();
    }

    @OnClick(R.id.iv_close)
    public void onClick() {
        MFGT.finish(this);
    }
}