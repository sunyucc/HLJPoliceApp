package hljpolice.pahlj.com.hljpoliceapp.webutils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Message;
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

    /*
    处理webview弹出新窗口
     */
    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(view);    //此webview可以是一般新创建的
        resultMsg.sendToTarget();
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    /*
    重写次方法获取地理位置
     */
    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        if (!isOPen()) {
            openLocation();
        }
        callback.invoke(origin, true, false);
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }

    /*
    判断手机是否开启定位
     */
    public void openLocation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("检测到您的手机未开启定位服务");
        builder.setMessage("是否开启定位服务");
        builder.setCancelable(false);
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {//添加确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                final Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//点击返回按钮取消下载关闭对话框
                isBack();
            }
        }).show();//在按键响应事件中
    }

    public void isBack() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("离开将不能获取您的位置信息");
        builder.setMessage("是否离开");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openLocation();
            }
        }).show();
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