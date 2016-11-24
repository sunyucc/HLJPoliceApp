package hljpolice.pahlj.com.hljpoliceapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hljpolice.pahlj.com.hljpoliceapp.I;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.bean.ShiXiangBean;
import hljpolice.pahlj.com.hljpoliceapp.ui.HtmlActivity;
import hljpolice.pahlj.com.hljpoliceapp.utils.Escape;

/**
 * Created by sunyu on 2016/11/23.
 */

public class ShiXiangAdapter extends RecyclerView.Adapter {
    Context mContext;

    ArrayList<ShiXiangBean.DataBean> mList;
    boolean isMore;//是否有更多的数据可加载

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }
    public void initSXList(ArrayList<ShiXiangBean.DataBean> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 添加新的一页数据
     * @param list
     */
    public void addSXList(ArrayList<ShiXiangBean.DataBean> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }


    public ShiXiangAdapter(Context mContext, ArrayList<ShiXiangBean.DataBean> list) {
        this.mContext = mContext;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        holder = new ShiXiangHolder(View.inflate(mContext, R.layout.item_shi_xiang, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ShiXiangHolder gvh = (ShiXiangHolder) holder;
        ShiXiangBean.DataBean bean = mList.get(position);
        gvh.tvShixiang.setText(bean.getSxmc());
        gvh.tvShixiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sxmc = mList.get(position).getSxmc();
                String url = null;
                    url = I.SHIXIANG_ADDRESS +
                            "?bmid="+ mList.get(position).getZdBsckid() +
                            "&sxid="+mList.get(position).getSxid() +
                            "&zn="+ mList.get(position).getZn() +
                            "&yy="+mList.get(position).getYy() +
                            "&sb=" + mList.get(position).getSb() +
                            "&sxmc="+ Escape.escape(sxmc);
                sxmc =  Escape.escape(sxmc);
                Intent intent = new Intent(mContext, HtmlActivity.class).putExtra("url" , url);
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

    static class ShiXiangHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_shixiang)
        TextView tvShixiang;

        ShiXiangHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}