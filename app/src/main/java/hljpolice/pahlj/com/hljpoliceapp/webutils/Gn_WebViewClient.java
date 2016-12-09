package hljpolice.pahlj.com.hljpoliceapp.webutils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebBackForwardList;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.listener.OnWebPageChangedListener;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;


/**
 * Created by Carklote on 2016/11/24.
 */

public class Gn_WebViewClient extends WebViewClient {
    private String defaultUrl="";
    private OnWebPageChangedListener listener;
    private Activity mContext;

    private CookieManager cookieManager;
    public Gn_WebViewClient(Activity context, OnWebPageChangedListener listener) {
        mContext = context;
        this.listener = listener;
    }

    public void setDefaultUrl(String defUrl) {
        this.defaultUrl = defUrl;
    }

    public void setOnWebPageChangedListener(OnWebPageChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (view.copyBackForwardList().getSize()>0 && url.contains("index.html") && defaultUrl.equals("")) {  //如果是首页，并不是第一页，则关闭
            mContext.finish();
        }
        return false;
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        L.e("onPageStarted: "+url);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        L.e("onPageFinished: "+url);
        super.onPageFinished(view, url);
        cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }

        if (url.contains("login.html") || url.equals(defaultUrl)) { //           L.e("这个页面需要更改appType");
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
        L.e("onReceivedError");
//        super.onReceivedError(view, request, error);
        view.loadUrl("file:///android_asset/error.html");
    }
}