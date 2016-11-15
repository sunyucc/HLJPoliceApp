package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import hlpolice.pahlj.com.hljpoliceapp.R;

public class HomePageFragment extends Fragment {
    WebView webView ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_home_page, container, false);
        webView = (WebView) layout.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.83027110.com/stwx/index.html");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Intent intent = new Intent(getActivity(),HtmlActivity.class).putExtra("url",url);
                startActivity(intent);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        return layout;
    }
}
