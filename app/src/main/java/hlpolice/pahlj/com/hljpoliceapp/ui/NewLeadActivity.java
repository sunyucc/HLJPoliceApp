package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import hlpolice.pahlj.com.hljpoliceapp.R;
import hlpolice.pahlj.com.hljpoliceapp.widget.ScrollLayout;

/**
 * 首次安装时进入的欢迎界面
 *
 * @author kymjs
 *
 */
public class NewLeadActivity extends Activity implements
        ScrollLayout.OnViewChangeListener,OnClickListener {
    private LinearLayout pointLayout;
    private ScrollLayout scrollLayout;
    private Button mBtnStart;
    private int count;
    private ImageView[] imgs;
    private int currentItem;

    public void initWidget() {
        setContentView(R.layout.activity_new_lead);
        pointLayout = (LinearLayout) findViewById(R.id.pointLayout);
        scrollLayout = (ScrollLayout) findViewById(R.id.scrollLayout);
        mBtnStart = (Button) findViewById(R.id.startBtn);
        count = scrollLayout.getChildCount();
        imgs = new ImageView[count];
        for (int i = 0; i < count; i++) {
            imgs[i] = (ImageView) pointLayout.getChildAt(i);
            imgs[i].setEnabled(true);
            imgs[i].setTag(i);
        }
        currentItem = 0;
        imgs[currentItem].setEnabled(false);
        scrollLayout.setOnViewChangeLintener(this);
        mBtnStart.setOnClickListener(this);

    }

    // 设置当前点
    @Override
    public void onViewChange(int postion) {
        if (postion < 0 || postion > count - 1 || currentItem == postion) {
            return;
        }
        imgs[currentItem].setEnabled(true);
        imgs[postion].setEnabled(false);
        currentItem = postion;
    }

    public void onClick(View v) {

    }
}