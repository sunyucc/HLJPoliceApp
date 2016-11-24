package hljpolice.pahlj.com.hljpoliceapp.webutils;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Gn_WebChromeClient extends WebChromeClient {
    private ProgressBar pBar;
    private TextView textView;


    public Gn_WebChromeClient(ProgressBar bar,TextView txtTilte) {
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
    public void onReceivedTitle(WebView view, String title) {
        textView.setText(title);
        super.onReceivedTitle(view, title);
    }
}