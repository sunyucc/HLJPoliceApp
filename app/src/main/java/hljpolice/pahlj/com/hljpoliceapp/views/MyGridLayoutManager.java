package hljpolice.pahlj.com.hljpoliceapp.views;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Author:    ZhuWenWu
 * Version    V1.0
 * Date:      2015/2/26  14:14.
 * Description:
 * Modification  History:
 * Date             Author                Version            Description
 * -----------------------------------------------------------------------------------
 */
public class MyGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled = false;
    public MyGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public MyGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}