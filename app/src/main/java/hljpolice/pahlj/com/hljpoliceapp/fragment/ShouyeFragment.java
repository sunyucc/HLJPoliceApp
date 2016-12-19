package hljpolice.pahlj.com.hljpoliceapp.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.onekeyshare.OnekeyShare;
import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.adapter.ShouYeAdapter;
import hljpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hljpolice.pahlj.com.hljpoliceapp.bean.NewsBean;
import hljpolice.pahlj.com.hljpoliceapp.dao.NetDao;
import hljpolice.pahlj.com.hljpoliceapp.ui.HtmlActivity;
import hljpolice.pahlj.com.hljpoliceapp.ui.MainActivity;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;
import hljpolice.pahlj.com.hljpoliceapp.utils.OkHttpUtils;
import hljpolice.pahlj.com.hljpoliceapp.views.FlowIndicator;
import hljpolice.pahlj.com.hljpoliceapp.views.MyGridLayoutManager;
import hljpolice.pahlj.com.hljpoliceapp.views.MyItemDecoration;
import hljpolice.pahlj.com.hljpoliceapp.views.SlideAutoLoopView;

/**
 * 首页Fragment
 */
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
    ShouYeAdapter[] mAdapters;
    MyGridLayoutManager[] mGlms;
    LinearLayout linearLayout;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    DialogInterface mDialog;
    @BindView(R.id.tv_yhfx)
    TextView tvYhfx;
    @BindView(R.id.tv_jwzx)
    TextView tvJwzx;
    @BindView(R.id.tv_gawb)
    TextView tvGawb;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    private String mPageName = "ShouyeFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_shou_ye, null);
        linearLayout = (LinearLayout) layout.findViewById(R.id.ll_function);
        ButterKnife.bind(this, layout);     // 相当于findviewbyid
        mContext = getContext();
        initView();
        initData(inflater);
        setListener(inflater);
        return layout;
    }


    /**
     * 下拉刷新模块信息
     *
     * @param inflater
     */
    private void setListener(final LayoutInflater inflater) {
        tvJwzx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HtmlActivity.class);
                intent.putExtra("url", I.JFXW_SERVER);
                getActivity().startActivity(intent);
            }
        });
        tvGawb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HtmlActivity.class);
                intent.putExtra("url", I.SINA_SERVER);
                mContext.startActivity(intent);
            }
        });
        tvYhfx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
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
     * sharesdk分享内容
     */
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("平安龙江");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://gafw.hljga.gov.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("公安移动门户是黑龙江省公安局面向市民唯一的、权威的、官方门户，是黑龙江公安网络媒体和移动新媒体平台高度融合的体现。");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(I.VERSION_SERVER + "fx_launcher.png");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setImagePath("");
        oks.setUrl("http://gafw.hljga.gov.cn");
// 启动分享GUI
        oks.show(getContext());
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
                    mAdapters = new ShouYeAdapter[result.length - 3];
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
                            L.e("json000"+json);
                            mLists[extid] = gson.fromJson(json, new TypeToken<ArrayList<FunctionBean.DataBean>>() {
                            }.getType());
                            L.i("json: " + json);
                            mGlms[extid] = new MyGridLayoutManager(mContext, 3);
                            mViews[extid] = funcView;
                            mAdapters[extid] = new ShouYeAdapter(mContext, mLists[extid]);
                            TextView tv = (TextView) mViews[extid].findViewById(R.id.tv_moudles_name);
                            tv.setText(result[i].getMc());
                            RecyclerView rv = (RecyclerView) mViews[extid].findViewById(R.id.recyclerView);
                            rv.setFocusable(false);
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
                    if (extList != null) {
                        m.setExtFuncData(extList);
                    }
                }
            }

            @Override
            public void onError(String error) {
                setNetworkMethod(getContext(), inflater);
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

    /**
     * 轮播图的高度设为屏幕1/5
     */
    protected void initView() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        loopView.getLayoutParams().height = metrics.heightPixels / 5;
    }

    /**
     * 首页加载失败Dialog提示
     *
     * @param context
     * @param inflater
     */
    public void setNetworkMethod(Context context, final LayoutInflater inflater) {
        //提示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示").setMessage("请检查网络").setPositiveButton("尝试刷新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialog = dialog;
                initData(inflater);
            }
        }).setNegativeButton("离开", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialog = dialog;
                dialog.dismiss();
                getActivity().finish();
            }
        }).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);     //友盟第三方sdk，用于记录错误信息
    }

    @Override
    public void onResume() {
        super.onResume();
//                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
        MobclickAgent.onPageStart(mPageName);
    }

}