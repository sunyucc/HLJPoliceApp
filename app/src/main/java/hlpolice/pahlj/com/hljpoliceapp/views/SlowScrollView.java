package hlpolice.pahlj.com.hljpoliceapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class SlowScrollView extends ScrollView {
    
        public SlowScrollView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }
    
        public SlowScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    
        public SlowScrollView(Context context) {
            super(context);
        }
    
        /**
         * 滑动事件
         */
        @Override
        public void fling(int velocityY) {
            super.fling(velocityY / 2);//这里设置滑动的速度
        }
    }