package hljpolice.pahlj.com.hljpoliceapp.fragment;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
import hljpolice.pahlj.com.hljpoliceapp.activity.MainActivity;
import hljpolice.pahlj.com.hljpoliceapp.activity.PhoneActivity;
import hljpolice.pahlj.com.hljpoliceapp.listener.OnWebPageChangedListener;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;
import hljpolice.pahlj.com.hljpoliceapp.webutils.CustomWebViewClient;
import hljpolice.pahlj.com.hljpoliceapp.webutils.Gn_WebChromeClient;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * 第二个和第四个Fragment
 * A simple {@link Fragment} subclass.
 */
public class GongNengFragment extends Fragment {
    WebView webView;
    MainActivity mContext;
    String defaultUrl;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    private boolean isLogined = false;
    private String mPageName = "GongNengFragment";
    private CustomWebViewClient webViewClient;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
    private Uri imageUri;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_function, container, false);
        mContext = (MainActivity) getActivity();
        ButterKnife.bind(this, layout);
        initView(layout);
        return layout;
    }


    /**
     * 初试化webveiw
     *
     * @param layout
     */
    private void initView(View layout) {
        ProgressBar bar = (ProgressBar) layout.findViewById(R.id.myProgressBar);
        webView = (WebView) layout.findViewById(R.id.wv_fragment);
        WebSettings settings = webView.getSettings();
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
        // 启用地理位置
        settings.setGeolocationEnabled(true);
        webView.addJavascriptInterface(new AndroidAndJSInterface(),"Android");
        webView.setVerticalScrollBarEnabled(true);
        txtTitle.setVisibility(View.VISIBLE);
        webViewClient = new CustomWebViewClient(getContext(), webPageChangedListener);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(new Gn_WebChromeClient(getContext(), bar, txtTitle) {
            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {

                mUploadCallbackAboveL = filePathCallback;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    L.e("onShowFileChooser==" + fileChooserParams.getAcceptTypes()[0].toString());
                }
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
    }

    private OnWebPageChangedListener webPageChangedListener = new OnWebPageChangedListener() {
        @Override
        public void pageCount(int count) {
            L.e("pageCount: " + count);
            switch (count) {
                case 0:
                    rlBack.setVisibility(View.GONE);
                    break;
                default:
                    rlBack.setVisibility(View.VISIBLE);
                    break;
            }
        }

        @Override
        public void finished() {
            getActivity().finish();
        }
    };

    /**
     * 设置webview加载的url
     *
     * @param url
     */
    public void setUrl(String url) {
        defaultUrl = url;
        webViewClient.setDefaultUrl(url);
//        webView.loadUrl("http://file.hljga.gov.cn:8081/mobile/index.html");
        webView.loadUrl(url);
    }

    /**
     * 设置标题名称
     *
     * @param titleName
     */
    public void setTxtTitle(String titleName) {
        txtTitle.setText(titleName);
    }

    /**
     * 返回键监听
     *
     * @param view
     */
    @OnClick(R.id.rl_back)
    public void onClick(View view) {
        if (webView.canGoBack()) {
            webView.goBack();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(mPageName);
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
     * 在webview中调用系统相机
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
        final PackageManager packageManager = getActivity().getPackageManager();
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
        getActivity().startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }

    /**
     * 调用文件管理，上传文件
     */
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.e("onActivityResult"+requestCode+resultCode);
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
        getActivity().overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }


    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {

        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == RESULT_OK) {

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

    class AndroidAndJSInterface{
        @JavascriptInterface
        public void gotoRz(){
            Intent intent = new Intent(getContext(), PhoneActivity.class);
            startActivity(intent);
        }
    }
}

