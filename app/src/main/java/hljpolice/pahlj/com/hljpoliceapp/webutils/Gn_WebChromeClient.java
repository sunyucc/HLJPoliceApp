package hljpolice.pahlj.com.hljpoliceapp.webutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import hljpolice.pahlj.com.hljpoliceapp.utils.L;

public class Gn_WebChromeClient extends WebChromeClient {
    private ProgressBar pBar;
    private TextView textView;
    private Context mContext;

    public Gn_WebChromeClient(Context context,ProgressBar bar,TextView txtTilte) {
        this.mContext = context;
        this.textView = txtTilte;
        pBar = bar;
        pBar.setVisibility(View.GONE);
    }

    public void onProgressChanged(WebView view, int newProgress) {
        //pBar.setVisibility(View.VISIBLE);
        pBar.setProgress(newProgress);
        if (newProgress == 100) {
            pBar.setVisibility(View.GONE);
        } else {
            pBar.setVisibility(View.VISIBLE);
        }
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        L.e(view.getUrl());
        textView.setText(title);
        super.onReceivedTitle(view, title);
    }
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//
//        builder.setTitle("提示")
//                .setMessage(message)
//                .setPositiveButton("确定", null);
//        // 禁止响应按back键的事件
//        builder.setCancelable(false);
//        AlertDialog dialog = builder.create();
//        dialog.show();
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
        return true;
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        L.e("origin"+origin);
        callback.invoke(origin, true, false);
    }
}