package hljpolice.pahlj.com.hljpoliceapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.Map;

import hljpolice.pahlj.com.hljpoliceapp.R;

/**
 * 设置首页下方按钮图片
 * Created by Carklote on 2016/11/23.
 */

public class NavResourceIcon {
    private Context mContext;
    private Map<Integer,Bitmap> iconMap;
    private RadioGroup radioGroup;
    private OnImageChangedListener listener;

    public NavResourceIcon(Context context, RadioGroup rg) {
        this.iconMap = new HashMap<>();
        this.radioGroup = rg;
        this.mContext = context;
        initData();
    }

    private void initData() {
        iconMap.put(1,resourceToBitmap(R.mipmap.ic_home));
        iconMap.put(2,resourceToBitmap(R.mipmap.ic_cloud));
        iconMap.put(3,resourceToBitmap(R.mipmap.ic_matter));
        iconMap.put(4,resourceToBitmap(R.mipmap.ic_cloud));

    }

    private Bitmap resourceToBitmap(int resId) {     //从资源文件中根据id获取bitmap
        return  BitmapFactory.decodeResource(mContext.getResources(), resId);
    }

    public void setOnImageChanageListener(OnImageChangedListener listener) {        //处理完图标后的通知监听
        this.listener = listener;
    }

    public void setImageUrl(String url, int id) {           //新增自定义图片
        GetHttpImage httpImage = new GetHttpImage(url);
        httpImage.setId(id);
        httpImage.setListener(new GetHttpImage.CallBackListener() {

            @Override
            public void Callback(int x, Bitmap resultBmp) {
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

    public Drawable getNavicon(int s) {           //根据ID返回导航图标
        if (iconMap.size() < s) {
            return null;
        } else {
            return resizeImage(iconMap.get(s));
        }
    }

    public Drawable getGrayNavIcon(int s) {          //根据ID返回导航灰度图标
        if (iconMap.size() < s) {
            return null;
        } else {
            return resizeImage(grey(iconMap.get(s)));
        }
    }
    private Bitmap grey(Bitmap bitmap) {            //图片置灰
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

    private Drawable resizeImage(Bitmap originalBitmap) {
        L.e("resize srcBitmap width: " + originalBitmap.getWidth());
        L.e("resize srcBitmap height: " + originalBitmap.getHeight());
        int originalWidth = originalBitmap.getWidth();
        int originalHeight = originalBitmap.getHeight();

        DisplayMetrics metrics = new DisplayMetrics();
        metrics = mContext.getResources().getDisplayMetrics();
        float newWidth = radioGroup.getLayoutParams().height * 0.40f * metrics.density ;
        float scale = newWidth / originalHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        Bitmap changedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalWidth, originalHeight, matrix, true);

//        L.e("resize changeBitmap width: " + changedBitmap.getWidth());
//        L.e("resize changeBitmap height: " + changedBitmap.getHeight());

//        Drawable drawable = new BitmapDrawable(null,changedBitmap);
//        L.e("resize changeDrawable width: " + drawable.getIntrinsicWidth());
//        L.e("resize changeDrawable height: " + drawable.getIntrinsicHeight());

//        drawable.setBounds(0,0,imageWidth,imageHeight);
//
//        L.e("resize Drawable width: " + drawable.getIntrinsicWidth());
//        L.e("resize Drawable height: " + drawable.getIntrinsicHeight());
        return new BitmapDrawable(null,changedBitmap);
    }

    public interface OnImageChangedListener {
        void isDown(int x);
    }
}
