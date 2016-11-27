package hljpolice.pahlj.com.hljpoliceapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.bean.ShiXiangBean;

/**
 * 事项中心列表的适配器
 * Created by sunyu on 2016/11/23.
 */

public class ShiXiangAdapter extends RecyclerView.Adapter {
    Context mContext;
    private OnItemClickListener listener;
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
     *
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
        final ShiXiangBean.DataBean bean = mList.get(position);
        gvh.tvShixiang.setText(bean.getSxmc());
        gvh.tvShixiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClickListener(mList.get(position));
//                ≈
//                ≈
//                ≈
//                ≈
//                ≈
//                ≈
//                ≈
//                ≈
//                ≈
//                ≈
//                ≈
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

    static class ShiXiangHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_shixiang)
        TextView tvShixiang;

        ShiXiangHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void itemClickListener(ShiXiangBean.DataBean bean);
    }
}
