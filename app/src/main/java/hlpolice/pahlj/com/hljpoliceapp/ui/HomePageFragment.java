package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hlpolice.pahlj.com.hljpoliceapp.R;
import hlpolice.pahlj.com.hljpoliceapp.adapter.HomePageAdapter;
import hlpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hlpolice.pahlj.com.hljpoliceapp.dao.NetDao;
import hlpolice.pahlj.com.hljpoliceapp.utils.ConvertUtils;
import hlpolice.pahlj.com.hljpoliceapp.utils.L;
import hlpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;
import hlpolice.pahlj.com.hljpoliceapp.views.FlowIndicator;
import hlpolice.pahlj.com.hljpoliceapp.views.SlideAutoLoopView;
import hlpolice.pahlj.com.hljpoliceapp.views.SpaceItemDecoration;


public class HomePageFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRv;
    Context mContext;
    HomePageAdapter mAdapter;
    ArrayList<FunctionBean> mList;
    GridLayoutManager glm;
    @BindView(R.id.salv)
    SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    FlowIndicator indicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, layout);
        mContext = getContext();
        mList = new ArrayList<>();
        mAdapter = new HomePageAdapter(mContext, mList);
        initView();
        initData();
        return layout;
    }

    private void downloadMoudles() {
        NetDao.downloadMoudles(mContext, new OkHttpUtils.OnCompleteListener<FunctionBean[]>() {
            @Override
            public void onSuccess(FunctionBean[] result) {
                if (result != null && result.length > 0) {
                    ArrayList<FunctionBean> list = ConvertUtils.array2List(result);
                    mAdapter.initData(list);
                }
            }

            @Override
            public void onError(String error) {
                L.e("error:" + error);
            }
        });
    }

    protected void initData() {
        downloadMoudles();
        String[] strArr= new String[]{"http://img.ads.csdn.net/2016/201610200947374106.jpg"};
        salv.startPlayLoop(indicator,strArr, 1);
    }

    protected void initView() {
        glm = new GridLayoutManager(mContext, 3);
        mRv.setLayoutManager(glm);
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new SpaceItemDecoration(12));
    }

}
