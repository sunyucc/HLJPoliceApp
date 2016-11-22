package hljpolice.pahlj.com.hljpoliceapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

import hljpolice.pahlj.com.hljpoliceapp.R;

/**
 * Created by Carklote on 2016/11/18.
 */
public class Nav_Resource_Icon {
    private Map<Integer, Bitmap> iconMap;
    private Context mContext;
    private int maxId;

    private int imageWidth = 100;
    private int imageHeight = 100;

    private OnImageChangedListener listener;
    private LinearLayout mLayout;

    public Nav_Resource_Icon(Context context, int x, LinearLayout layout) {
        this.mContext = context;
        this.maxId = x;
        this.mLayout = layout;
        iconMap = new HashMap<>();
        iconMap.put(1, getResource(R.mipmap.ic_home));
        for (int i = 2; i <= x; i++) {
            iconMap.put(i, getResource(R.mipmap.ic_cloud));
        }
    }

    private Bitmap getResource(int resId) {
        return BitmapFactory.decodeResource(mContext.getResources(), resId);
    }

    public Bitmap getNavicon(int s) {
        if (maxId < s) return null;
        imageHeight = mLayout.getHeight();
        imageWidth = imageHeight;
        return resizeImage(iconMap.get(s), imageWidth, imageHeight);
    }

    public Bitmap getGrayNavIcon(int s) {
        if (maxId < s) {
            return null;
        } else {
            if (getScreenWidth(mContext) < 720) {
                imageHeight = mLayout.getWidth() / 8;
                imageWidth = imageHeight;
            } else {
                imageHeight = mLayout.getHeight();
                imageWidth = imageHeight;

            }
            return resizeImage(grey(iconMap.get(s)), imageWidth, imageHeight);
        }
    }

    public void setImageSize(int width, int height) {
        this.imageWidth = width;
        this.imageHeight = height;
    }

    public void setOnImageChanageListener(OnImageChangedListener listener) {
        this.listener = listener;
    }

    public void setImageUrl(String url, int id) {
        GetHttpImage httpImage = new GetHttpImage(url);
        httpImage.setId(id);
        httpImage.setListener(new GetHttpImage.CallBackListener() {

            @Override
            public void Callback(int x, Bitmap resultBmp) {
                L.e("x==" + x);
                iconMap.put(x, resultBmp);
                if (listener != null) {
                    listener.isDown(x);
                }
            }

            @Override
            public void onError(String err) {

            }
        });
        httpImage.getImage();
    }

    public void setImageUrl(Map<Integer, String> reqData) {
        for (int x : reqData.keySet()) {
            setImageUrl(reqData.get(x), x);
        }
    }

    private Bitmap grey(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap faceIconGreyBitmap = Bitmap
                .createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(faceIconGreyBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return faceIconGreyBitmap;
    }

    private Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;

        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        if (w == 0 || h == 0) {
            newHeight = 180;
            newWidth = 180;
        }
        L.e("kaungao" + width + "   " + height + "   " + w + "   " + h);
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

    public interface OnImageChangedListener {
        void isDown(int x);
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return display.getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return display.getHeight();
    }
}