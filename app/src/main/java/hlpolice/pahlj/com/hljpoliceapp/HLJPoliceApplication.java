package hlpolice.pahlj.com.hljpoliceapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by sunyu on 2016/11/15.
 */

public class HLJPoliceApplication extends Application {
    public static Context application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    private static class HLJPoliceInstance{
        static final HLJPoliceApplication instance = new HLJPoliceApplication();
    }
    public static HLJPoliceApplication getApplication() {
        return HLJPoliceInstance.instance;
    }

}
