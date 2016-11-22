package hljpolice.pahlj.com.hljpoliceapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import hljpolice.pahlj.com.hljpoliceapp.utils.MFGT;
import hljpolice.pahlj.com.hljpoliceapp.views.SlidingLayout;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlidingLayout rootView = new SlidingLayout(this);
        rootView.bindActivity(this);
        initView();
        initData();
        setListener();

    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();

    /**
     * 点击间隔时间
     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            if (ClickTimeUtils.isFastDoubleClick()) {
//                return true;
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }


    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
