package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import hlpolice.pahlj.com.hljpoliceapp.R;
import hlpolice.pahlj.com.hljpoliceapp.adapter.HomePageAdapter;
import hlpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hlpolice.pahlj.com.hljpoliceapp.bean.NewsBean;
import hlpolice.pahlj.com.hljpoliceapp.dao.NetDao;
import hlpolice.pahlj.com.hljpoliceapp.utils.ConvertUtils;
import hlpolice.pahlj.com.hljpoliceapp.utils.L;
import hlpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;
import hlpolice.pahlj.com.hljpoliceapp.views.FlowIndicator;
import hlpolice.pahlj.com.hljpoliceapp.views.MyGridLayoutManager;
import hlpolice.pahlj.com.hljpoliceapp.views.SlideAutoLoopView;
import hlpolice.pahlj.com.hljpoliceapp.views.SpaceItemDecoration;


public class HomePageFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRv;
    Context mContext;
    HomePageAdapter mAdapter;
    ArrayList<FunctionBean> mList;
    MyGridLayoutManager glm;
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
                    MainActivity m=(MainActivity)getActivity();
                    for (FunctionBean functionBean : list) {
                        if ("03".equals(functionBean.getMklb())) {
                            m.setExtFuncData(functionBean);
                        }
                    }

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

        downloadNews();


    }

    private void downloadNews() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("加载中...");
        pd.show();
        NetDao.downloadNews(mContext, new OkHttpUtils.OnCompleteListener<NewsBean>() {
            @Override
            public void onSuccess(NewsBean result) {
                if (result != null) {
                    List<Map<String,String>> urlList = new ArrayList<>();
                    Map<String,String> urlMap;
                    List<NewsBean.DataBean> newsList = result.getData();

                    for (int i = 0; i < newsList.size(); i++) {
                        urlMap = new HashMap<>();
                        urlMap.put("imgUrl",newsList.get(i).getTplj());
                        urlMap.put("newsUrl",newsList.get(i).getXwdz());
                        urlList.add(urlMap);
                    }

                    salv.startPlayLoop(indicator, urlList, newsList.size());

                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                L.e(error);
            }
        });
    }


    protected void initView() {
        glm = new MyGridLayoutManager(mContext, 3);
        mRv.setLayoutManager(glm);
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new SpaceItemDecoration(12));
    }


}
