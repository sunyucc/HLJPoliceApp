package hljpolice.pahlj.com.hljpoliceapp.utils;

/**
 * Created by sunyu on 2016/11/22.
 */

public class ClickTimeUtils {
    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        return timeD <= 500;
    }
}
