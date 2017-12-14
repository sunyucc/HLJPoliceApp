package hljpolice.pahlj.com.hljpoliceapp.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
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
import hljpolice.pahlj.com.hljpoliceapp.webutils.CustomWebViewClient;
import hljpolice.pahlj.com.hljpoliceapp.webutils.Gn_WebChromeClient;

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
    SharedPreferences sp ;
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
        sp =getSharedPreferences("user", Context.MODE_PRIVATE);
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

        mWebView.setWebViewClient(new CustomWebViewClient(this, pageListener));
        mWebView.setWebChromeClient(new Gn_WebChromeClient(this, bar, tvHtmltitle) {

            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {

                mUploadCallbackAboveL = filePathCallback;
                String accept = "";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    accept = fileChooserParams.getAcceptTypes()[0];
                }

                if (accept.equals("")) {
                    openFile();
                } else {
                    take(getParam(accept));
                }
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                if (acceptType.equals("")) {
                    openFile();
                } else {
                    take(getParam(acceptType));
                }
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                L.e("Func: openFileChooser,parma3");
                mUploadMessage = uploadMsg;
                if (acceptType.equals("")) {
                    openFile();
                } else {
                    take(getParam(acceptType));
                }
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
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());
    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private Map<String, String> getParam(String accept) {
        Map<String, String> fileMap = new HashMap<>();
        fileMap.put("accept", accept);
        if (accept.contains("image")) {             //  如果要上传图片类型的数据,执行本方法
            fileMap.put("fileExt", ".jpg");
            fileMap.put("Storage", Environment.DIRECTORY_PICTURES);
            fileMap.put("MediaStore", MediaStore.ACTION_IMAGE_CAPTURE);
            fileMap.put("title", "上传图片");
        } else if (accept.contains("video")) {
            fileMap.put("fileExt", ".mp4");
            fileMap.put("Storage", Environment.DIRECTORY_MOVIES);
            fileMap.put("MediaStore", MediaStore.ACTION_VIDEO_CAPTURE);
            fileMap.put("title", "上传视频");
        }
//        else if (accept.equals("")) {
//            fileMap.put("fileExt", "");
//            fileMap.put("Storage", Environment.DIRECTORY_DOCUMENTS);
//            fileMap.put("title", "上传文件");
//            fileMap.put("accept", "*/*");
//        }
        return fileMap;
    }

    ;

    /**
     * 在webview中调用系统相机、文件管理
     */
    private void take(Map<String, String> param) {

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

    private void openFile() {
//        final List<Intent> cameraIntents = new ArrayList<>();
//        final PackageManager packageManager = getPackageManager();
//        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//        i.addCategory(Intent.CATEGORY_OPENABLE);
//        i.setType("*/*");
//
//        Intent chooserIntent = Intent.createChooser(i, "上传文件");
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
//        HtmlActivity.this.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, FILECHOOSER_RESULTCODE);
    }

    private OnWebPageChangedListener pageListener = new OnWebPageChangedListener() {

        @Override
        public void pageCount(int count) {
        }

        @Override
        public void finished() {
            HtmlActivity.this.finish();
        }
    };


    private void initView() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebSettings settings = mWebView.getSettings();
//        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        settings.setDatabaseEnabled(true);
//        settings.setGeolocationDatabasePath(dir);
        settings.setGeolocationDatabasePath(this.getFilesDir().getPath());
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setAppCacheEnabled(true);
//        settings.setLoadWithOverviewMode(true);
        settings.setSupportMultipleWindows(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true);
        mWebView.addJavascriptInterface(new AndroidAndJSInterface(), "Android");
//        mWebView.setVerticalScrollBarEnabled(true);
        // 启用地理位置
        L.e("url+++" + url);
        mWebView.loadUrl("http://yingyong.hljga.gov.cn/sfzbl/sfzbl_index.html");
//        mWebView.loadUrl("file:///android_asset/error1.html");
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


    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

        if (sp.getBoolean("isZhian",false)) {
            final String xm = sp.getString("mXm","");
            final String sjh = sp.getString("mSjh","");
            final String sfzh =sp.getString("mSfzh","");
            final String tp = sp.getString("mtp","");
            Log.e("onstart", xm + sjh + sfzh + sfzh + tp);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:getResult("+"'" + sjh + "','" + xm + "','" + sfzh + "','" + tp + "'"+")");
                    Log.e("javascript: ", "javascript:getResult(" + "'" + xm + "','" + sjh + "','" + sfzh + "','" + tp + "'" + ")");
                }
            }, 500);
            SharedPreferences.Editor editor = sp.edit();

            editor.clear();
//                startActivity(mIntent);
            editor.commit();
        }
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

    class AndroidAndJSInterface {
        @JavascriptInterface
        public void myGotoRz(String name, String sfzh) {
            Log.e("name", name + sfzh);
//            name = "孙宇";
//            sfzh = "230521199411281912";
            Intent intent = new Intent(HtmlActivity.this, PhoneActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("sfzh", sfzh);
            intent.putExtra("id", 1);
            startActivity(intent);
        }
    }
}