package hljpolice.pahlj.com.hljpoliceapp.webutils;

import android.graphics.Bitmap;
import android.webkit.WebBackForwardList;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.listener.OnWebPageChangedListener;


/**
 * Created by Carklote on 2016/11/24.
 */

public class Gn_WebViewClient extends WebViewClient {
    private String defaultUrl;
    private OnWebPageChangedListener listener;

    public Gn_WebViewClient(OnWebPageChangedListener listener) {
        this.listener = listener;

    }

    public void setDefaultUrl(String defUrl) {
        defaultUrl = defUrl;
    }

    public void setOnWebPageChangedListener(OnWebPageChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        if (!url.equals(defaultUrl) && url.contains("index.html")) {
//            view.loadUrl(defaultUrl);
//        }
        return false;
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
 //       L.e("current_Url: " + url);
 //       L.e("default_Url: " + defaultUrl);

        if (url.contains("login.html") || url.equals(defaultUrl)) {
 //           L.e("这个页面需要更改appType");
            view.loadUrl(I.CHANGE_APPTYPE);
            view.clearHistory();
        }
        if (listener!=null) {
            WebBackForwardList wbfl = view.copyBackForwardList();
            listener.pageCount(wbfl.getCurrentIndex());
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        view.loadUrl("file:///android_asset/error.html");
    }


}