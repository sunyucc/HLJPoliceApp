package hljpolice.pahlj.com.hljpoliceapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.internet.AjaxCallBack;
import com.android.pc.ioc.internet.FastHttp;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.android.pc.util.Handler_Json;
import com.hisign.CTID.facelivedetection.CTIDLiveDetectActivity;
import com.hisign.CTID.utilty.SharedPreferencesCTID;
import com.hisign.CTID.utilty.ToolsUtilty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.bean.RenZheng;
import hljpolice.pahlj.com.hljpoliceapp.utils.CustomDialog;

import static android.R.attr.path;

/**
 * 主类
 *
 * @ClassName: MainActivity
 * @Description:TODO
 * @date: Jul 15, 2015 9:35:58 AM
 */
public class CTIDMainActivity extends Activity implements OnClickListener {
    MediaPlayer mediaPlayerfail;
    /**
     * 设置使用前置还是后置摄像头0前置1后置
     */
    private String mIsFornt = "0";
    private boolean flag = false;
    private String mIsUpDown = "0";
    private byte[] mSuiJiShuShuJu;
    final static int STATUS_REQUEST = 100;
    SharedPreferences sp;
    LinearLayout on_ll, off_ll, on_ll_updown, off_ll_updown, ll_up, ll_down;
    ImageView on_iv, off_iv, on_iv_updown, off_iv_updown;
    View view1, view2, view3, view4, view5, view6, view7;
    File mFile = null;
    String mName;
    String mSfzh;
    ProgressDialog dialog2;
    String mPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ToolsUtilty.getResIdByTypeAndName(this, "layout", "ctid_activity_main"));
        sp =getSharedPreferences("user", Context.MODE_PRIVATE);
        mName = getIntent().getStringExtra("name");
        mSfzh = getIntent().getStringExtra("sfzh");
        mediaPlayerfail = MediaPlayer.create(getApplicationContext(),
                ToolsUtilty.getResIdByTypeAndName(getApplicationContext(), "raw", "ctidfail"));
        on_ll = (LinearLayout) findViewById(R.id.on_ll);
        off_ll = (LinearLayout) findViewById(R.id.off_ll);
        on_ll_updown = (LinearLayout) findViewById(R.id.on_ll_updown);
        off_ll_updown = (LinearLayout) findViewById(R.id.off_ll_updown);
        off_iv = (ImageView) findViewById(R.id.off_iv);
        on_iv = (ImageView) findViewById(R.id.on_iv);
        on_iv_updown = (ImageView) findViewById(R.id.on_iv_updown);
        off_iv_updown = (ImageView) findViewById(R.id.off_iv_updown);
        ll_up = (LinearLayout) findViewById(R.id.ll_up);
        ll_down = (LinearLayout) findViewById(R.id.ll_down);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);
        view5 = findViewById(R.id.view5);
        view6 = findViewById(R.id.view6);
        view7 = findViewById(R.id.view7);

        imgLogo = (ImageView) findViewById(R.id.img_logo);
        img_success = (ImageView) findViewById(R.id.img_success);
        img_fail = (ImageView) findViewById(R.id.img_fail);
        img_logo1 = (ImageView) findViewById(R.id.img_logo1);
        txtMessage = (TextView) findViewById(R.id.txt_message);
        mProductcodeTv = (TextView) findViewById(R.id.productcode);
        txtMessagesucee = (TextView) findViewById(R.id.sucessd_tv);
        txtMessagefail = (TextView) findViewById(R.id.fail_tv);
        vercode = (TextView) findViewById(R.id.vercode);

        vercode.setText(ToolsUtilty.getApplicationVersion(this));
        mProductcodeTv.setText(CTIDLiveDetectActivity.getProduct());
        infotest = (TextView) findViewById(R.id.infotest);
        btnStartCheck = (Button) findViewById(R.id.btn_start);
        mInfoNoPassreazionTv = (TextView) findViewById(R.id.infotestrezion);
        mInfoNoPassreazionTv2 = (TextView) findViewById(R.id.infotestrezion2);
        mInfoNoPassreazionTv3 = (TextView) findViewById(R.id.infotestrezion3);
        mInfoNoPassreazionTv4 = (TextView) findViewById(R.id.infotestrezion4);

        txtMessagefail.setText("");

        txtMessagesucee.setText("");

        on_ll.setOnClickListener(this);
        off_ll.setOnClickListener(this);
        on_ll_updown.setOnClickListener(this);
        off_ll_updown.setOnClickListener(this);
        btnStartCheck.setOnClickListener(this);


        if (SharedPreferencesCTID.getHeaderPath(getApplicationContext(), "mIsUpDown") != null
                && SharedPreferencesCTID.getHeaderPath(getApplicationContext(), "mIsUpDown").equalsIgnoreCase("0")) {

            on_iv_updown.setBackgroundResource(R.drawable.music_list_edit_checkbox_pressed);
            off_iv_updown.setBackgroundResource(R.drawable.music_list_edit_checkbox_normal);

            mIsUpDown = "0";
            SharedPreferencesCTID.setHeaderPath(getApplicationContext(), "mIsUpDown", "0");

        } else if (SharedPreferencesCTID.getHeaderPath(getApplicationContext(), "mIsUpDown") != null
                && SharedPreferencesCTID.getHeaderPath(getApplicationContext(), "mIsUpDown").equalsIgnoreCase("1")) {

            on_iv_updown.setBackgroundResource(R.drawable.music_list_edit_checkbox_normal);
            off_iv_updown.setBackgroundResource(R.drawable.music_list_edit_checkbox_pressed);
            SharedPreferencesCTID.setHeaderPath(getApplicationContext(), "mIsUpDown", "1");

            mIsUpDown = "1";

        }


        if (SharedPreferencesCTID.getHeaderPath(getApplicationContext(), "mIsFornt") != null
                && SharedPreferencesCTID.getHeaderPath(getApplicationContext(), "mIsFornt").equalsIgnoreCase("0")) {

            on_iv.setBackgroundResource(R.drawable.music_list_edit_checkbox_pressed);
            off_iv.setBackgroundResource(R.drawable.music_list_edit_checkbox_normal);

            mIsFornt = "0";
            SharedPreferencesCTID.setHeaderPath(getApplicationContext(), "mIsFornt", "0");

        } else if (SharedPreferencesCTID.getHeaderPath(getApplicationContext(), "mIsFornt") != null
                && SharedPreferencesCTID.getHeaderPath(getApplicationContext(), "mIsFornt").equalsIgnoreCase("1")) {

            on_iv.setBackgroundResource(R.drawable.music_list_edit_checkbox_normal);
            off_iv.setBackgroundResource(R.drawable.music_list_edit_checkbox_pressed);
            SharedPreferencesCTID.setHeaderPath(getApplicationContext(), "mIsFornt", "1");

            mIsFornt = "1";

        }


    }

    private void startLiveDect() {

        Intent intent = new Intent();
        intent.setClass(CTIDMainActivity.this, CTIDLiveDetectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putByteArray("randomNomber", mSuiJiShuShuJu);
        bundle.putString("mIsFornt", mIsFornt);
        bundle.putString("mIsUpDown", mIsUpDown);
        intent.putExtra("mSet", bundle);
        startActivityForResult(intent, 20);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20) {
            // randomNomber = new byte[1];
            if (resultCode == Activity.RESULT_OK && data != null) {

                Bundle result = data.getBundleExtra("result");
                boolean check_pass = result.getBoolean("check_pass");

                String mBadReason = result.getString("mBadReason");

                if (check_pass) {
                    img_logo1.setVisibility(View.GONE);
                    imgLogo.setVisibility(View.VISIBLE);
                    byte[] pic_thumbnail = result.getByteArray("pic_thumbnail");
                    byte[] pic_result = result.getByteArray("encryption");

                    if (pic_thumbnail != null && pic_result != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(pic_thumbnail, 0, pic_thumbnail.length);
                        imgLogo.setImageBitmap(bitmap);
                        mFile = saveImageToGallery(this, bitmap);
                        infotest.setVisibility(View.VISIBLE);
                        img_success.setVisibility(View.VISIBLE);
                        img_fail.setVisibility(View.GONE);
                        infotest.setText("照片采集成功");
                        miIdSuecc++;

                        ll_up.setVisibility(View.GONE);
                        ll_down.setVisibility(View.GONE);
                        view1.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);
                        view4.setVisibility(View.GONE);
                        view5.setVisibility(View.GONE);
                        view6.setVisibility(View.GONE);
                        view7.setVisibility(View.GONE);
                        btnStartCheck.setText("开始认证");

                        flag = true;
                    } else {
                        miIdfaile++;
                        img_fail.setVisibility(View.VISIBLE);
                        img_success.setVisibility(View.GONE);
                        infotest.setVisibility(View.VISIBLE);

                        infotest.setText("抱歉！您的动作不符合");
                        img_logo1.setVisibility(View.VISIBLE);
                        imgLogo.setVisibility(View.GONE);
                        imgLogo.setImageResource(R.drawable.ctid_forntali_just);

                    }

                } else {
                    miIdfaile++;
                    txtMessagesucee.setText("成功" + miIdSuecc + "次");
                    txtMessagefail.setText("失败" + miIdfaile + "次");
                    img_fail.setVisibility(View.VISIBLE);
                    img_success.setVisibility(View.GONE);
                    infotest.setVisibility(View.VISIBLE);
                    if (mBadReason.equalsIgnoreCase("001")) {

                        infotest.setText("抱歉！请确保人脸始终在屏幕中");

                    } else if (mBadReason.equalsIgnoreCase("002")) {
                        infotest.setText("抱歉！请确保屏幕中只有一张脸");
                    } else if (mBadReason.equalsIgnoreCase("003")) {
                        infotest.setText("抱歉！您的动作不符合");
                    } else if (mBadReason.equalsIgnoreCase("004")) {
                        infotest.setText("抱歉！您的照片损坏太大");
                    } else if (mBadReason.equalsIgnoreCase("005")) {
                        infotest.setText("抱歉！您周围的环境光线过暗");
                    } else if (mBadReason.equalsIgnoreCase("006")) {
                        infotest.setText("抱歉！您周围的环境光线过亮");
                    } else if (mBadReason.equalsIgnoreCase("007")) {
                        infotest.setText("活检受到攻击");
                    } else if (mBadReason.equalsIgnoreCase("008")) {
                        infotest.setText("抱歉！超时");
                    } else if (mBadReason.equalsIgnoreCase("009")) {
                        infotest.setText("获取随机数失败");
                    } else if (mBadReason.equalsIgnoreCase("101")) {
                        infotest.setText("抱歉！请您保持静止不动");
                    } else if (mBadReason.equalsIgnoreCase("010")) {
                        infotest.setText("获取认证证书失败");
                    } else {
                        infotest.setText("抱歉！您的动作不符合");
                    }

                    img_logo1.setVisibility(View.VISIBLE);
                    imgLogo.setVisibility(View.GONE);
                    imgLogo.setImageResource(R.drawable.ctid_forntali_just);

                }

            }

        } else {

            // Toast.makeText(getApplicationContext(), "null2", 0).show();
        }
    }

    private TextView txtTip, txtMessage, txtMessagesucee, txtMessagefail, infotest, vercode, mInfoNoPassreazionTv,
            mInfoNoPassreazionTv2, mInfoNoPassreazionTv3, mInfoNoPassreazionTv4, mProductcodeTv;
    private ImageView imgPhoto, imgLogo, img_success, img_fail, img_logo1;
    private Button btnStartCheck, mReinputBtn;
    private EditText mInputIdCodEdt;

    private int miIdCodStr = 0;
    private static int miIdSuecc = 0;
    private static int miIdfaile = 0;
    private static boolean isNet = false;
    int debug = 1;
    final String FILE_NAME = "/sdtlog.txt";
    File targetFile;
    RandomAccessFile raf;

    private void openfile() {
        if (this.debug == 1) {
            // 获取SD卡目录
            File sdCardDir = Environment.getExternalStorageDirectory();

            try {
                setTargetFile(new File(sdCardDir.getCanonicalPath() + this.FILE_NAME));

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                setFile(new RandomAccessFile(this.targetFile, "rw"));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            writefile("in open file()");
        }
    }

    private void setTargetFile(File f) {
        this.targetFile = f;
    }

    private void setFile(RandomAccessFile raf) {
        this.raf = raf;
    }

    public void writefile(String context) {
        if (this.debug == 1 && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

            try {
                this.raf.seek(this.targetFile.length());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                this.raf.writeChars("\n" + sdf.format(new Date()) + " " + context);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.on_ll:
                on_iv.setBackgroundResource(R.drawable.music_list_edit_checkbox_pressed);
                off_iv.setBackgroundResource(R.drawable.music_list_edit_checkbox_normal);

                mIsFornt = "0";
                SharedPreferencesCTID.setHeaderPath(getApplicationContext(), "mIsFornt", "0");


                break;

            case R.id.off_ll:
                on_iv.setBackgroundResource(R.drawable.music_list_edit_checkbox_normal);
                off_iv.setBackgroundResource(R.drawable.music_list_edit_checkbox_pressed);
                SharedPreferencesCTID.setHeaderPath(getApplicationContext(), "mIsFornt", "1");

                mIsFornt = "1";


                break;
            case R.id.on_ll_updown:
                on_iv_updown.setBackgroundResource(R.drawable.music_list_edit_checkbox_pressed);

                off_iv_updown.setBackgroundResource(R.drawable.music_list_edit_checkbox_normal);

                mIsUpDown = "0";
                SharedPreferencesCTID.setHeaderPath(getApplicationContext(), "mIsUpDown", "0");


                break;
            case R.id.off_ll_updown:
                off_iv_updown.setBackgroundResource(R.drawable.music_list_edit_checkbox_pressed);

                on_iv_updown.setBackgroundResource(R.drawable.music_list_edit_checkbox_normal);

                mIsUpDown = "1";
                SharedPreferencesCTID.setHeaderPath(getApplicationContext(), "mIsUpDown", "1");


                break;
            case R.id.btn_start:
                img_logo1.setVisibility(View.VISIBLE);
                imgLogo.setVisibility(View.INVISIBLE);
                infotest.setVisibility(View.INVISIBLE);
                img_success.setVisibility(View.INVISIBLE);
                img_fail.setVisibility(View.INVISIBLE);
                mInfoNoPassreazionTv.setVisibility(View.INVISIBLE);

                mInfoNoPassreazionTv.setText("");
                mInfoNoPassreazionTv2.setVisibility(View.INVISIBLE);
                mInfoNoPassreazionTv2.setText("");
                mInfoNoPassreazionTv3.setVisibility(View.INVISIBLE);
                mInfoNoPassreazionTv3.setText("");
                mInfoNoPassreazionTv4.setVisibility(View.INVISIBLE);
                mInfoNoPassreazionTv4.setText("");
                if (!flag) {
                    startLiveDect();
                } else {
                    dialog2 = ProgressDialog.show(this, "提示", "正在验证中");
                    if (getIntent().getIntExtra("id", 0) == 1) {
                        yzNet();
                    } else {

                        rzNet();
                    }
                }


                break;
        }

    }

    private void yzNet() {
        InternetConfig config1 = new InternetConfig();
        config1.setTime(5);
        config1.setKey(STATUS_REQUEST);
        HashMap<String, File> fileparams = new HashMap<String, File>();
        fileparams.put("files", mFile);
        LinkedHashMap<String, String> params = new LinkedHashMap();
        params.put(I.YSRZ.NAME, mName);
        params.put(I.YSRZ.IDNUMBER, mSfzh);
        params.put("phone", getIntent().getStringExtra("phone"));
        Log.e("getContentAsString", getIntent().getStringExtra("phone") + getIntent().getStringExtra("yzm"));
        params.put("yzm", getIntent().getStringExtra("yzm"));
        FastHttp.ajaxForm(I.YSRZ.ZHIAN_URL, params, fileparams,
                config1, back1);
    }

    private void rzNet() {
        InternetConfig config1 = new InternetConfig();
        config1.setTime(5);
        config1.setKey(STATUS_REQUEST);
        HashMap<String, File> fileparams = new HashMap<String, File>();
        fileparams.put("files", mFile);
        LinkedHashMap<String, String> params = new LinkedHashMap();
        params.put(I.YSRZ.NAME, mName);
        params.put(I.YSRZ.IDNUMBER, mSfzh);
        params.put("phone", getIntent().getStringExtra("phone"));
        Log.e("getContentAsString", getIntent().getStringExtra("phone") + getIntent().getStringExtra("yzm"));
        params.put("yzm", getIntent().getStringExtra("yzm"));
        FastHttp.ajaxForm(I.YSRZ.UPLOAD_YZ, params, fileparams,
                config1, back);
    }

    public static File saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新

        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        return file;
    }

    AjaxCallBack back = new AjaxCallBack() {
        @Override
        public void callBack(ResponseEntity status) {
            if (dialog2 != null) {
                dialog2.dismiss();
            }
            if (status == null) {
                return;
            } else {
                try {
                    RenZheng renzheng = Handler_Json.JsonToBean(RenZheng.class,
                            status.getContentAsString());
                    if (status.getStatus() == FastHttp.result_net_err) {
                        Toast.makeText(CTIDMainActivity.this, renzheng.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.e("getContentAsString:", status.getContentAsString());

                    if (status.getKey() == STATUS_REQUEST) {
                        Log.e("message :", renzheng.getMessage());
//				showDialogMess(ajaxpic.getMessage());
//				btnStartCheck.setText("认证结果："+ajaxpic.getMessage());
                        String info = renzheng.getMessage();
                        if (info.equals("00XX")) {
                            info = "认证成功";
                        }
                        showAlertDialog(info);
                    }
                } catch (Exception e) {
                    Toast.makeText(CTIDMainActivity.this, "未知错误,请稍后重试！", Toast.LENGTH_SHORT).show();
                }


            }


        }

        @Override
        public boolean stop() {
            if (dialog2 != null) {
                dialog2.dismiss();
            }
            // TODO Auto-generated method stub
            return false;
        }

    };

    public void showAlertDialog(String message) {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("认证结果");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                mIntent.setClass(CTIDMainActivity.this, HtmlActivity.class);

                finish();
            }
        });

        builder.create().show();

    }
    public void showAlertDialog(String message, final RenZheng renZheng) {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("认证结果");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isZhian", true);
                editor.putString("mXm", mName);
                editor.putString("mSjh", getIntent().getStringExtra("phone"));
                editor.putString("mSfzh",mSfzh);
                editor.putString("mtp", renZheng.getOne().getInpicstream());
//                startActivity(mIntent);
                editor.commit();
                finish();
            }
        });

        builder.create().show();

    }

    AjaxCallBack back1 = new AjaxCallBack() {
        @Override
        public void callBack(ResponseEntity status) {
            if (dialog2 != null) {
                dialog2.dismiss();
            }
            if (status == null) {
                return;
            } else {
                try {
                    RenZheng renzheng = Handler_Json.JsonToBean(RenZheng.class,
                            status.getContentAsString());
                    if (status.getStatus() == FastHttp.result_net_err) {
                        Toast.makeText(CTIDMainActivity.this, renzheng.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.e("getContentAsString:", status.getContentAsString());

                    if (status.getKey() == STATUS_REQUEST) {
                        Log.e("message :", renzheng.getMessage());
                        String info = renzheng.getMessage();
                        if (info.equals("00XX")) {
                            info = "认证成功";
                            showAlertDialog(info,renzheng);
                        } else {

                            showAlertDialog(info);
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(CTIDMainActivity.this, "未知错误,请稍后重试！", Toast.LENGTH_SHORT).show();
                }


            }


        }

        @Override
        public boolean stop() {
            if (dialog2 != null) {
                dialog2.dismiss();
            }
            // TODO Auto-generated method stub
            return false;
        }

    };
}
