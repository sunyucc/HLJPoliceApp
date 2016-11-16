package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import hlpolice.pahlj.com.hljpoliceapp.R;
import hlpolice.pahlj.com.hljpoliceapp.widget.WelcomeWizardView;

/**
 * 首次安装时进入的欢迎界面
 *
 * @author kymjs
 *
 */
public class NewLeadActivity extends Activity implements
        WelcomeWizardView.OnViewChangeListener,OnClickListener {
    private LinearLayout pointLayout;
    private WelcomeWizardView scrollLayout;
    private Button mBtnStart;
    private int count;
    private ImageView[] imgs;
    private int currentItem;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initWidget();
    }

    public void initWidget() {
        setContentView(R.layout.activity_new_lead);
        pointLayout = (LinearLayout) findViewById(R.id.pointLayout);
        scrollLayout = (WelcomeWizardView) findViewById(R.id.scrollLayout);
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