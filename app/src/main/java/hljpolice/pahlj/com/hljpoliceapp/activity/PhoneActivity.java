package hljpolice.pahlj.com.hljpoliceapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.bean.Repetition;
import hljpolice.pahlj.com.hljpoliceapp.dao.NetDao;
import hljpolice.pahlj.com.hljpoliceapp.utils.CheckoutUtils;
import hljpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;

public class PhoneActivity extends AppCompatActivity {

    @BindView(R.id.et_yzm)
    EditText etYzm;
    @BindView(R.id.tv_yzm)
    TextView tvYzm;
    @BindView(R.id.et_yzm2)
    EditText etYzm2;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.tv_tishi1)
    TextView tvTishi1;
    @BindView(R.id.tv_tishi2)
    TextView tvTishi2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phone);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }

    @OnClick({R.id.tv_yzm, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yzm:
//
                if (etYzm.getText() != null && etYzm.length() > 0) {

                    if (CheckoutUtils.isMobileNO(etYzm.getText().toString())) {
                        NetDao.repetition(getApplicationContext(), etYzm.getText().toString(), new OkHttpUtils.OnCompleteListener<Repetition>() {
                            @Override
                            public void onSuccess(Repetition result) {
                                if (result.getInfoKey().equals("error") && getIntent().getIntExtra("id", 0) == 0) {
                                    tvTishi1.setText("手机号重复");
                                } else {

                                    NetDao.gainYzm(getApplicationContext(), etYzm.getText().toString(), new OkHttpUtils.OnCompleteListener<Repetition>() {
                                        @Override
                                        public void onSuccess(Repetition result) {
                                            tvTishi1.setText(result.getInfoName());
                                        }

                                        @Override
                                        public void onError(String error) {
                                            tvTishi1.setText("请稍后重试！");

                                        }
                                    });


                                    new CountDownTimer(60 * 1000, 1000) {

                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            tvYzm.setEnabled(false);
                                            tvYzm.setText(millisUntilFinished / 1000 + "秒后重新发送");
                                        }

                                        @Override
                                        public void onFinish() {
                                            tvYzm.setEnabled(true);
                                            tvYzm.setText("重新发送");
                                        }
                                    }.start();
                                    tvTishi1.setText("");
                                }
                            }

                            @Override
                            public void onError(String error) {
                                tvTishi1.setText("请稍后重试！");
                            }
                        });

                    } else {
                        tvTishi1.setText("请输入正确的手机号！");
                    }

                } else {
                    tvTishi1.setText("手机号码不能为空！");
                }
                break;
            case R.id.btn_confirm:

                if (etYzm.getText() != null && etYzm.getText().length() > 0) {
                    if (CheckoutUtils.isMobileNO(etYzm.getText().toString())) {
                        if (etYzm2.getText() != null && etYzm2.getText().length() > 0 && etYzm2.getText().length() < 5) {
                            tvTishi1.setText("");
                            NetDao.verify(getApplicationContext(), etYzm.getText().toString(), etYzm2.getText().toString(), new OkHttpUtils.OnCompleteListener<Repetition>() {
                                @Override
                                public void onSuccess(Repetition result) {
                                    if (result == null) {
                                        return;
                                    }
                                    if (result.getInfoKey().equals("error")) {
                                        Toast.makeText(PhoneActivity.this, result.getInfoName(), Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(PhoneActivity.this, result.getInfoName(), Toast.LENGTH_SHORT).show();
                                        Intent intent = null;
                                        if (getIntent().getIntExtra("id", 0) == 1) {
                                            intent = new Intent(PhoneActivity.this, CTIDMainActivity.class);
                                            intent.putExtra("name", getIntent().getStringExtra("name"));
                                            intent.putExtra("sfzh", getIntent().getStringExtra("sfzh"));
                                            intent.putExtra("id", 1);
                                        } else {

                                            intent = new Intent(PhoneActivity.this, SfInfoActivity.class);
                                        }
                                        Log.e("getContentAsStri", etYzm.getText().toString());
                                        intent.putExtra("phone", etYzm.getText().toString());
                                        intent.putExtra("yzm", etYzm2.getText().toString());
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                                @Override
                                public void onError(String error) {
                                    Toast.makeText(PhoneActivity.this, "请稍后重试！", Toast.LENGTH_SHORT).show();
                                }
                            });






                        } else {
                            tvTishi2.setText("请输入4位验证码！");
                        }


                    } else {
                        tvTishi1.setText("请输入正确的手机号！");
                    }

                } else {
                    tvTishi1.setText("手机号码不能为空！");
                }



                break;
        }
    }

}
