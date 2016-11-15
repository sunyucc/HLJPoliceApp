package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hlpolice.pahlj.com.hljpoliceapp.R;
import hlpolice.pahlj.com.hljpoliceapp.utils.L;
import hlpolice.pahlj.com.hljpoliceapp.utils.MFGT;


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
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.loadUrl(url);
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        MFGT.finish(this);
    }
}
