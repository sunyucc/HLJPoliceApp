package hljpolice.pahlj.com.hljpoliceapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.listener.OnWebPageChangedListener;
import hljpolice.pahlj.com.hljpoliceapp.ui.MainActivity;
import hljpolice.pahlj.com.hljpoliceapp.webutils.Gn_WebChromeClient;
import hljpolice.pahlj.com.hljpoliceapp.webutils.Gn_WebViewClient;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;

/**
 * 咨询中心的Fragment
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

    private Gn_WebViewClient webViewClient;
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
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        txtTitle.setVisibility(View.VISIBLE);
        webViewClient = new Gn_WebViewClient(getActivity(),webPageChangedListener);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(new Gn_WebChromeClient(bar,txtTitle));
    }

    private OnWebPageChangedListener webPageChangedListener = new OnWebPageChangedListener() {
        @Override
        public void pageCount(int count) {
            L.e("pageCount: " + count );
            switch (count) {
                case 0:
                    rlBack.setVisibility(View.GONE);
                    break;
                default:
                    rlBack.setVisibility(View.VISIBLE);
                    break;
            }
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
}

