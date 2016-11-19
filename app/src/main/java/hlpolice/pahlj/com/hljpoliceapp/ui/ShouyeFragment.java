package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import hlpolice.pahlj.com.hljpoliceapp.utils.MFGT;
import hlpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;
import hlpolice.pahlj.com.hljpoliceapp.views.FlowIndicator;
import hlpolice.pahlj.com.hljpoliceapp.views.MyGridLayoutManager;
import hlpolice.pahlj.com.hljpoliceapp.views.MyItemDecoration;
import hlpolice.pahlj.com.hljpoliceapp.views.SlideAutoLoopView;


public class ShouyeFragment extends Fragment {
    Context mContext;
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
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    DialogInterface mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home_page, null);
        linearLayout = (LinearLayout) layout.findViewById(R.id.linearLayout);
        ButterKnife.bind(this, layout);
        mContext = getContext();
        initView();
        initData(inflater);
        setListener(inflater);
        return layout;
    }

    private void setListener(final LayoutInflater inflater) {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                linearLayout.removeAllViews();
                downloadMoudles(inflater);
            }
        });
    }

    /**
     * 下载模块信息
     */
    private void downloadMoudles(final LayoutInflater inflater) {
        NetDao.downloadMoudles(mContext, new OkHttpUtils.OnCompleteListener<FunctionBean[]>() {
            @Override
            public void onSuccess(FunctionBean[] result) {
                mSrl.setRefreshing(false);
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                if (result != null && result.length > 0) {

                    mViews = new View[result.length - 3];
                    mLists = new ArrayList[result.length - 3];
                    mAdapters = new HomePageAdapter[result.length - 3];
                    mGlms = new MyGridLayoutManager[result.length - 3];

                    int extid = 0;
                    MainActivity m = (MainActivity) getActivity();
                    List<FunctionBean> extList = new ArrayList<>();
                    for (int i = 0; i < result.length; i++) {

                        View funcView = inflater.inflate(R.layout.item_main_function, null);
                        String str = result[i].getMklb();
                        if ("01".equals(str) || "02".equals(str) || "03".equals(str)) {
                            extList.add(result[i]);
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
                                RecyclerView rv = (RecyclerView) mViews[extid].findViewById(R.id.recyclerView);
                                rv.setAdapter(mAdapters[extid]);
                                rv.setLayoutManager(mGlms[extid]);
                                rv.setHasFixedSize(true);
                                rv.setAdapter(mAdapters[extid]);
                                rv.addItemDecoration(new MyItemDecoration());
                                mAdapters[extid].notifyDataSetChanged();
                            linearLayout.addView(mViews[extid]);
                            extid++;
                        }

                    }
                    m.setExtFuncData(extList);
                }
            }

            @Override
            public void onError(String error) {

                    setNetworkMethod(getContext(),inflater);
                L.e("error:" + error);
                mSrl.setRefreshing(false);
            }
        });
    }

    protected void initData(LayoutInflater inflater) {

        downloadNews();
        downloadMoudles(inflater);

    }

    /**
     * 下载新闻信息
     */
    private void downloadNews() {
        L.e("=====downloadNews");
        NetDao.downloadNews(mContext, new OkHttpUtils.OnCompleteListener<NewsBean>() {
            @Override
            public void onSuccess(NewsBean result) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                L.e("reuslt==" + result.toString());
                if (result != null) {

                    List<Map<String, String>> urlList = new ArrayList<>();
                    Map<String, String> urlMap;
                    List<NewsBean.DataBean> newsList = result.getData();
                    L.e("newList==" + newsList.size());
                    for (int i = 0; i < newsList.size(); i++) {
                        urlMap = new HashMap<>();
                        urlMap.put("imgUrl", newsList.get(i).getTplj());
                        urlMap.put("newsUrl", newsList.get(i).getXwdz());
                        urlList.add(urlMap);
                        L.e("urllist==" + urlList);
                    }

                    salv.startPlayLoop(indicator, urlList, newsList.size());

                }
            }

            @Override
            public void onError(String error) {
                L.e("error===" + error);
            }
        });
    }


    protected void initView() {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        loopView.getLayoutParams().height = metrics.heightPixels / 5;
    }

    /**
     * 无网络弹窗
     * @param context
     * @param inflater
     */
    public void setNetworkMethod( Context context, final LayoutInflater inflater){
        //提示对话框
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("请检查网络").setMessage("数据请求失败请重试").setPositiveButton("刷新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialog = dialog;
                initData(inflater);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialog = dialog;
                dialog.dismiss();
                MFGT.finish(getActivity());

            }
        }).show();
    }
}