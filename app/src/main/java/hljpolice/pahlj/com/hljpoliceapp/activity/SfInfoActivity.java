package hljpolice.pahlj.com.hljpoliceapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.utils.CheckoutUtils;

public class SfInfoActivity extends AppCompatActivity {

    @BindView(R.id.et_yzm)
    EditText etYzm;
    @BindView(R.id.et_yzm2)
    EditText etYzm2;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.tv_tishi3)
    TextView tvTishi3;
    @BindView(R.id.tv_tishi4)
    TextView tvTishi4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_info);
        ButterKnife.bind(this);
        Log.e("getContentAsString",getIntent().getStringExtra("phone")+getIntent().getStringExtra("yzm"));
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {

        if (etYzm2.getText() != null && etYzm2.getText().length() > 0) {
            if (CheckoutUtils.isIDCard(etYzm2.getText().toString())) {
                Intent intent = new Intent(SfInfoActivity.this, CTIDMainActivity.class);
                intent.putExtra("name", etYzm.getText().toString());
                intent.putExtra("sfzh", etYzm2.getText().toString());
                intent.putExtra("phone", getIntent().getStringExtra("phone"));
                intent.putExtra("yzm", getIntent().getStringExtra("yzm"));
                startActivity(intent);
                finish();
            } else {
                tvTishi4.setText("请输入正确的证件号码！");
            }

        } else {
            tvTishi4.setText("证件号码不能为空！");
        }

    }

}
