package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import hlpolice.pahlj.com.hljpoliceapp.R;

/**
 * Created by sunyu on 2016/11/15.
 */

public class HtmlActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_html);
        getIntent();
    }
}
