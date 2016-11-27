package hljpolice.pahlj.com.hljpoliceapp.utils;


import android.os.Handler;
import android.os.Message;

import hljpolice.pahlj.com.hljpoliceapp.listener.ProgressListener;


public class DownloadFiles implements Runnable {


    private int prograssValue = 0;
    private ProgressListener listener;

    public DownloadFiles(ProgressListener listener) {
        this.listener = listener;

    }

    public void setPrograssValue(int prograssValue) {
        this.prograssValue = prograssValue;
        ConnectionManager.getInstance().push(this);
    }


    @Override
    public void run() {
        L.e("Runnable_Run");
        Message message = Message.obtain(handler, prograssValue, listener);
        handler.sendMessage(message);
        ConnectionManager.getInstance().didComplete(this);
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            ProgressListener listener = (ProgressListener) message.obj;
            L.e("Handle_Message");
            if (listener != null) {
                int value = message.what;
                listener.progressValue(value);
                if (value == 100) {
                    listener.downloadComplete();
                }
            }
        }
    };
}
