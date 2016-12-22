package hljpolice.pahlj.com.hljpoliceapp.webutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.LocationManager;
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

    public Gn_WebChromeClient(Context context, ProgressBar bar, TextView txtTilte) {
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
        callback.invoke(origin, true, false);
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }
    public final boolean isOPen() {
        LocationManager locationManager
                = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }
}