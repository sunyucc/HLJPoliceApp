package hljpolice.pahlj.com.hljpoliceapp.webutils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.listener.OnWebPageChangedListener;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;

/**
 * Created by Carklote on 2016/12/7
 */

public class CustomWebViewClient extends WebViewClient {
    private String defaultUrl = "";
    private OnWebPageChangedListener listener;
        Context context;

    public void setDefaultUrl(String defUrl) {
        this.defaultUrl = defUrl;
    }
    public CustomWebViewClient(Context context, OnWebPageChangedListener listener) {
        this.context =context;
        this.listener = listener;
    }
    public void setOnWebPageChangedListener(OnWebPageChangedListener listener) {
        this.listener = listener;
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
       L.e("shouldOverrideUrlLoading: "+url);
        // TODO Auto-generated method stub
        if (view.copyBackForwardList().getSize()>0 && url.contains("index.html")&& defaultUrl.equals("")) {  //如果是首页，并不是第一页，则关闭
            listener.finished();
        }
        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            context.startActivity(intent);
            return true;
        }
//        view.loadUrl(url);
        return false;
    }
    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        L.e("onReceivedError==");
        super.onReceivedError(view, errorCode, description, failingUrl);
        view.loadUrl("file:///android_asset/error.html");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        L.e("onPageFinished: "+url);
        super.onPageFinished(view, url);
        CookieManager cookieManager = CookieManager.getInstance();
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
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
         L.e("onPageStarted: "+url);
        super.onPageStarted(view, url, favicon);
    }
}
