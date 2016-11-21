package hljpolice.pahlj.com.hljpoliceapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.ui.MainActivity;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;

/**
 * 咨询中心
 * A simple {@link Fragment} subclass.
 */
public class GongNengFragment extends Fragment {
    WebView webView ;
    MainActivity mContext;
    boolean isLoad = false;
    String defaultUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_function, container, false);
        mContext = (MainActivity) getActivity();
        initView(layout);
        webView.requestFocus();
        return layout;
    }
    private void initView(View layout) {
        webView = (WebView) layout.findViewById(R.id.wv_fragment);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("index.html")) {
                    L.e("defaultUrl" + defaultUrl);
                    view.loadUrl(defaultUrl);
                } else {

                view.loadUrl(url);
                }
//                if (url != null) {
//                    Intent intent = new Intent(mContext, HtmlActivity.class).putExtra("url", url);
//                    startActivity(intent);
//                }
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains("login.html")) {
                    view.loadUrl(I.CHANGE_APPTYPE);
                }
                super.onPageFinished(view, url);

            }
        });

    }
    public void setUrl(String url){

        defaultUrl = url;
        L.e("defaultUrl"+defaultUrl);
        if (!isLoad) {
        webView.loadUrl(url);
            isLoad = true;
        }
    }
}

