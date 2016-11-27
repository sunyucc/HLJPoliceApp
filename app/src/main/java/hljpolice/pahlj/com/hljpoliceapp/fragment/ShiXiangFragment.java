package hljpolice.pahlj.com.hljpoliceapp.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.adapter.PopupWindowAdapter;
import hljpolice.pahlj.com.hljpoliceapp.adapter.ShiXiangAdapter;
import hljpolice.pahlj.com.hljpoliceapp.bean.ShiXiangBean;
import hljpolice.pahlj.com.hljpoliceapp.bean.ShiXiangModuleBean;
import hljpolice.pahlj.com.hljpoliceapp.dao.NetDao;
import hljpolice.pahlj.com.hljpoliceapp.ui.HtmlActivity;
import hljpolice.pahlj.com.hljpoliceapp.ui.MainActivity;
import hljpolice.pahlj.com.hljpoliceapp.utils.Escape;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;
import hljpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShiXiangFragment extends Fragment {
    private static final int ACTION_DOWNLOAD = 0;
    private static final int ACTION_PULL_UP = 1;
    @BindView(R.id.tb_quanbu)
    TextView tvQuanBu;
    MainActivity mContext;
    @BindView(R.id.rv_shixiang)
    RecyclerView mRv;
    @BindView(R.id.et_search)
    EditText etSearch;

    ArrayList<ShiXiangBean.DataBean> mList;
    ArrayList<ShiXiangModuleBean> mModuleList;
    ShiXiangAdapter mAdapter;
    PopupWindowAdapter mPopupAdapter;
    LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;
    int mPageId = 1;
    int mNewState;
    PopupWindow window;
    RecyclerView rvjingzhong;
    RadioGroup rg_sxywdl;

    //    private  ShiXiangSearch searchData;
    Map<String, String> searchData;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.srl_shixiang)
    SwipeRefreshLayout srlShixiang;
    private String defaultUrl;
    public ShiXiangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shi_xiang, container, false);
        ButterKnife.bind(this, view);
        mContext = (MainActivity) getActivity();
        initView();
        initData();
        setListener();
        return view;
    }

    private void setListener() {
        srlShixiang.setRefreshing(true);
        srlShixiang.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        mPopupAdapter.setOnItemClickListener(new PopupWindowAdapter.OnItemClickListener() {
            @Override
            public void itemClickListener(ShiXiangModuleBean bean) {
                tvQuanBu.setText(bean.getMc());
                searchData.put("sxywdl", bean.getBm());
                mPageId = 1;
                L.e("search=" + searchData.toString());
                downloadShiXiang(ACTION_DOWNLOAD, mPageId, searchData);
                window.dismiss();
            }
        });
        rg_sxywdl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_quanbu:
                        //searchData.setSxywdl("");
                        searchData.put("sxywlb", "");
                        break;
                    case R.id.rb_geren:
                        searchData.put("sxywlb", "01");
                        //searchData.setSxywdl("01"); //个人字典id，需要在网络上获取
                        break;
                    case R.id.rb_danwei:
                        searchData.put("sxywlb", "02");
                        //searchData.setSxywdl("02"); //单位字典id，需要在网上获取
                }
            }
        });


        mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mNewState = newState;
                //获取最后一个列表项的索引
                int lastPostion = mLayoutManager.findLastVisibleItemPosition();
                if (lastPostion >= mAdapter.getItemCount() - 10 && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mPageId++;//设置准备加载下一页数据
                    downloadShiXiang(ACTION_PULL_UP, mPageId, searchData);
                }
            }
        });
    }

    private void initData() {
        searchData = new HashMap<>();
        mPageId =1 ;
        downloadShiXiang(ACTION_DOWNLOAD, mPageId, searchData);
        downloadSXModule();
    }

    private void downloadSXModule() {
        NetDao.downShiXiangModule(mContext, new OkHttpUtils.OnCompleteListener<ShiXiangModuleBean[]>() {
            @Override
            public void onSuccess(ShiXiangModuleBean[] result) {
                if (result != null) {
                    Gson gson = new Gson();
                    String json = gson.toJson(result);
                    mModuleList = gson.fromJson(json, new TypeToken<ArrayList<ShiXiangModuleBean>>() {
                    }.getType());
                    mPopupAdapter.initSXList(mModuleList);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void downloadShiXiang(final int action, int pageId, Map<String, String> data) {
        NetDao.downShiXiang(mContext, pageId, data, new OkHttpUtils.OnCompleteListener<ShiXiangBean>() {
            @Override
            public void onSuccess(ShiXiangBean result) {
                srlShixiang.setRefreshing(false);
                if (result != null) {
                    Gson gson = new Gson();
                    String json = gson.toJson(result.getData());
                    mList = gson.fromJson(json, new TypeToken<ArrayList<ShiXiangBean.DataBean>>() {
                    }.getType());
                    L.e("mlist=="+mList.toString());
                    if (action == ACTION_DOWNLOAD) {

                        mAdapter.initSXList(mList);
                    } else {
                        mAdapter.addSXList(mList);
                    }

                }
            }

            @Override
            public void onError(String error) {
                srlShixiang.setRefreshing(false);
            }
        });
    }

    private void initView() {
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(I.SHIXIANG_TITLE);
        mList = new ArrayList<>();          //初始化事项中心的Arraylist
        mModuleList = new ArrayList<>();
        mPopupAdapter = new PopupWindowAdapter(mContext, mModuleList);
        mAdapter = new ShiXiangAdapter(mContext, mList);
        mLayoutManager = new LinearLayoutManager(mContext);
        mGridlayoutManager = new GridLayoutManager(mContext, 3);
        mAdapter.setOnItemClickListener(shixiangClickListener);
        mRv.setAdapter(mAdapter);
        mRv.setLayoutManager(mLayoutManager);
        View popupView = mContext.getLayoutInflater().inflate(R.layout.pop_window, null);
        rg_sxywdl = (RadioGroup) popupView.findViewById(R.id.rg_sxywdl);
        //设置popup中recycleview的适配器和布局管理器
        rvjingzhong = (RecyclerView) popupView.findViewById(R.id.rv_popup);
        rvjingzhong.setAdapter(mPopupAdapter);
        rvjingzhong.setLayoutManager(mGridlayoutManager);
        //设置popup的高度
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        window = new PopupWindow(popupView, metrics.widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void showPopupWindow() {
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.update();
        window.showAsDropDown(tvQuanBu, 0, 0);
    }


    @OnClick({R.id.tb_quanbu,R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tb_quanbu:
                showPopupWindow();
                break;
            case R.id.tv_search:
                if (etSearch.getText().toString().length() > 0) {
                    searchData.put("sxmc", etSearch.getText().toString());
                }
                downloadShiXiang(ACTION_DOWNLOAD, mPageId, searchData);
                break;
        }
    }
    private ShiXiangAdapter.OnItemClickListener shixiangClickListener = new ShiXiangAdapter.OnItemClickListener() {
        @Override
        public void itemClickListener(ShiXiangBean.DataBean bean) {
            String sxmc = bean.getSxmc();
            String url = null;
            url = defaultUrl+
                    "?bmid=" + bean.getZdBsckid() +
                    "&sxid=" + bean.getSxid() +
                    "&zn=" + bean.getZn() +
                    "&yy=" + bean.getYy() +
                    "&sb=" + bean.getSb() +
                    "&sxmc=" + Escape.escape(sxmc);
            Intent intent = new Intent(mContext, HtmlActivity.class).putExtra("url", url);
            mContext.startActivity(intent);
        }
    };
    public void setUrl(String url){
        defaultUrl = url;
    }
}
