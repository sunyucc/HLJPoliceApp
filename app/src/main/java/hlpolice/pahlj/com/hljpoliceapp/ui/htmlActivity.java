package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hlpolice.pahlj.com.hljpoliceapp.R;
import hlpolice.pahlj.com.hljpoliceapp.utils.L;
import hlpolice.pahlj.com.hljpoliceapp.utils.MFGT;

import static hlpolice.pahlj.com.hljpoliceapp.R.id.webView;

/**
 * 用于显示点击Html的跳转
 */
public class HtmlActivity extends AppCompatActivity {
    private static final String TAG = HtmlActivity.class.getSimpleName();
    WebView mWebView;
    @BindView(R.id.img_back)
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        ButterKnife.bind(this);
       initVeiw();

    }

    private void initVeiw() {
        imgBack.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        L.e(TAG, "url===" + url);
        mWebView = (WebView) findViewById(webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
//        mWebView.loadData(url,"text/html","UTF-8");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
//                view.loadData(url,"text/html","UTF-8");
                return super.shouldOverrideUrlLoading(view, url);
            }
            });
        }

    @OnClick(R.id.img_back)
    public void onClick() {
        MFGT.finish(this);
    }
}
