package hljpolice.pahlj.com.hljpoliceapp.listener;

/**
 * apk文件下载进度监听
 */
public interface ProgressListener {
        void progressValue(int value);
        void downloadComplete();
    }