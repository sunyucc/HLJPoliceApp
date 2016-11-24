package hljpolice.pahlj.com.hljpoliceapp.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.ui.MainActivity;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;

/**
 * 咨询中心的Fragment
 * A simple {@link Fragment} subclass.
 */
public class GongNengFragment extends Fragment {
    WebView webView;
    MainActivity mContext;
    boolean isLoad = false;
    String defaultUrl;
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;

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
        final ProgressBar bar = (ProgressBar) layout.findViewById(R.id.myProgressBar);
        webView = (WebView) layout.findViewById(R.id.wv_fragment);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        txtTitle.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (!url.equals(defaultUrl) && url.contains("index.html")) {
//                    view.loadUrl(defaultUrl);
//                }
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                bar.setVisibility(View.GONE);
                boolean isLogin = url.contains("login.html");   //判断是否为登录页
                if (isLogin || url.equals(defaultUrl)) {       //如果是登录页或者是默认页
                    rlBack.setVisibility(View.GONE);            //不显示返回按钮
                } else {
                    if (view.canGoBack() && !url.contains("index.html")) {  //如果不是首页并且包含上一页
                        rlBack.setVisibility(View.VISIBLE);     //显示返回按钮
                    }
                }

                //在这里与JS代码交互
                if (url.contains("login.html") | url.contains("grzx/index.html")) {      //登录页
                    view.loadUrl(I.CHANGE_APPTYPE); //修改apptype
                }

                super.onPageFinished(view, url);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.loadUrl("file:///android_asset/error.html");
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                bar.setVisibility(View.VISIBLE);
                if (View.INVISIBLE == bar.getVisibility()) {
                    bar.setVisibility(View.VISIBLE);
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }


        });
    }

    /**
     * 设置webview加载的url
     *
     * @param url
     */
    public void setUrl(String url) {

        defaultUrl = url;
        L.e("defaultUrl" + defaultUrl);
        if (!isLoad) {
            webView.loadUrl(url);
            isLoad = true;
        }
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
}

