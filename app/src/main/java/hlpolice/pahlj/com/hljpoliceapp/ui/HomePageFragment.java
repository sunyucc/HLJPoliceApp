package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import hlpolice.pahlj.com.hljpoliceapp.utils.L;
import hlpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;
import hlpolice.pahlj.com.hljpoliceapp.views.FlowIndicator;
import hlpolice.pahlj.com.hljpoliceapp.views.MyGridLayoutManager;
import hlpolice.pahlj.com.hljpoliceapp.views.MyItemDecoration;
import hlpolice.pahlj.com.hljpoliceapp.views.SlideAutoLoopView;


public class HomePageFragment extends Fragment {
    //    @BindView(R.id.recyclerView)
//    RecyclerView mRv;
    Context mContext;
    //    HomePageAdapter mAdapter, mYwfwAdapter;
//    ArrayList<FunctionBean> mList, sList;
//    MyGridLayoutManager glm;
//    MyGridLayoutManager glm1;
    @BindView(R.id.salv)
    SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    FlowIndicator indicator;
    @BindView(R.id.layout_image)
    RelativeLayout loopView;
    View[] mViews;
    ArrayList<FunctionBean.DataBean>[] mLists;
    HomePageAdapter[] mAdapters;
    MyGridLayoutManager[] mGlms;
    LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_home_page, null);
        linearLayout = (LinearLayout) layout.findViewById(R.id.linearLayout);
        ButterKnife.bind(this, layout);
        mContext = getContext();
//        sList = new ArrayList<>();
//        mYwfwAdapter = new HomePageAdapter(mContext, sList);
//        mAdapter = new HomePageAdapter(mContext, mList);
        initView();
        initData(inflater);
        return layout;
    }

    /**
     * 下载模块信息
     */
    private void downloadMoudles(final LayoutInflater inflater) {
        NetDao.downloadMoudles(mContext, new OkHttpUtils.OnCompleteListener<FunctionBean[]>() {
            @Override
            public void onSuccess(FunctionBean[] result) {
                if (result != null && result.length > 0) {

                    mViews = new View[result.length - 3];
                    mLists = new ArrayList[result.length - 3];
                    mAdapters = new HomePageAdapter[result.length - 3];
                    mGlms = new MyGridLayoutManager[result.length - 3];

                    int extid = 0;
                    MainActivity m = (MainActivity) getActivity();

                    for (int i = 0; i < result.length; i++) {

                        View funcView = inflater.inflate(R.layout.item_main_function, null);
                        if ("01".equals(result[i].getMklb())) {
                            m.setExtFuncData(result[i]);
                        } else if ("02".equals(result[i].getMklb())) {
                            m.setExtSxData(result[i]);
                        } else if ("03".equals(result[i].getMklb())) {
                            m.setExtGrData(result[i]);
                        } else {
                            Gson gson = new Gson();
                            String json = gson.toJson(result[i].getData());
                            mLists[extid] = gson.fromJson(json, new TypeToken<ArrayList<FunctionBean.DataBean>>() {
                            }.getType());
                            L.i("json: " + json);
                            mGlms[extid] = new MyGridLayoutManager(mContext, 3);
                            mViews[extid] = funcView;
                            mAdapters[extid] = new HomePageAdapter(mContext, mLists[extid]);
                            TextView tv = (TextView) mViews[extid].findViewById(R.id.tv_moudles_name);
                            tv.setText(result[i].getMc());
                            for (int x = 0; x < result[i].getData().size(); x++) {
                                RecyclerView rv = (RecyclerView) mViews[extid].findViewById(R.id.recyclerView);
                                rv.setAdapter(mAdapters[extid]);
                                rv.setLayoutManager(mGlms[extid]);
                                rv.setHasFixedSize(true);
                                rv.setAdapter(mAdapters[extid]);
                                rv.addItemDecoration(new MyItemDecoration());
                                mAdapters[extid].notifyDataSetChanged();
                            }
                            //mLinearLayout.addView(mViews[extid]);
                            linearLayout.addView(mViews[extid]);
                            extid++;
                        }

                    }


//                    mAdapter.notifyDataSetChanged();
//                    mYwfwAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String error) {
                L.e("error:" + error);
            }
        });
    }

    protected void initData(LayoutInflater inflater) {
        downloadMoudles(inflater);
        downloadNews();

    }

    /**
     * 下载新闻信息
     */
    private void downloadNews() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("加载中...");
        pd.show();
        NetDao.downloadNews(mContext, new OkHttpUtils.OnCompleteListener<NewsBean>() {
            @Override
            public void onSuccess(NewsBean result) {
                if (result != null) {
                    List<Map<String, String>> urlList = new ArrayList<>();
                    Map<String, String> urlMap;
                    List<NewsBean.DataBean> newsList = result.getData();

                    for (int i = 3; i < newsList.size() - 3; i++) {
                        urlMap = new HashMap<>();
                        urlMap.put("imgUrl", newsList.get(i).getTplj());
                        urlMap.put("newsUrl", newsList.get(i).getXwdz());
                        urlList.add(urlMap);
                        L.e("urllist=="+urlList);
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
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        loopView.getLayoutParams().height = metrics.heightPixels / 5;
    }
}