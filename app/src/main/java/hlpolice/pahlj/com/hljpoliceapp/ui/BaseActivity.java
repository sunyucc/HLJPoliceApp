package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import hlpolice.pahlj.com.hljpoliceapp.utils.MFGT;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
        
    }

    protected abstract void initView();
    protected abstract void initData();
    protected abstract void setListener();

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
