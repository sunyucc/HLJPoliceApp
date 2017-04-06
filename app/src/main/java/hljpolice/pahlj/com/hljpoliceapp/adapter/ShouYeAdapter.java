package hljpolice.pahlj.com.hljpoliceapp.adapter;

/**
 * Created by sunyu on 2016/11/15.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hljpolice.pahlj.com.hljpoliceapp.activity.HtmlActivity;
import hljpolice.pahlj.com.hljpoliceapp.utils.Escape;
import hljpolice.pahlj.com.hljpoliceapp.utils.ImageLoader;
import hljpolice.pahlj.com.hljpoliceapp.utils.L;


/**
 * 首页列表的适配器
 * Created by sunyu on 2016/11/15
 */

public class ShouYeAdapter extends RecyclerView.Adapter {
    Context mContext;
    private ArrayList<FunctionBean.DataBean> mList;
    private String url = null;

    public ShouYeAdapter(Context mContext, ArrayList mList) {
        this.mContext = mContext;
        this.mList = mList;
        L.e("mlist==" + mList.toString());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;

        holder = new HomePageViewHolder(View.inflate(mContext, R.layout.item_page_home, null));

        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HomePageViewHolder gvh = (HomePageViewHolder) holder;

        final FunctionBean.DataBean bean = mList.get(position);
        L.e("bean====" + bean.toString());
        ImageLoader.downloadImg(mContext, gvh.imageView, bean.getTbdz(), true);
        gvh.textView.setText(bean.getMkmc());
        gvh.recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = mList.get(position).getQqdz();
                if (url.contains("info.html")) {
                    url = url + "?"+ I.TITLE +"="+ Escape.escape(bean.getMkmc()) + "&" +I.TARGET + "=" + bean.getYydz();
                }
                Intent intent = new Intent()
                        .putExtra("url", url)

                        .putExtra("moudlesname", mList.get(position).getMkmc());
                intent.setClass(mContext, HtmlActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    static class HomePageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView)
        TextView textView;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.recyclerView)
        RelativeLayout recyclerView;

        HomePageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
