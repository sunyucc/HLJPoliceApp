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
import hljpolice.pahlj.com.hljpoliceapp.bean.ShiXiangModuleBean;
import hljpolice.pahlj.com.hljpoliceapp.fragment.ShiXiangFragment;

/**
 * 事项中心窗口列表的适配器
 * Created by sunyu on 2016/11/23.
 */

public class PopupWindowAdapter extends RecyclerView.Adapter {
    Context mContext;
    final static int TYPE_HEAD = 0;
    final static int TYPE_ITEM = 1;
    ShiXiangFragment shiXiangFragment;
    ArrayList<ShiXiangModuleBean> mList;
    boolean isMore;//是否有更多的数据可加载
    private OnItemClickListener listener;

    public void initSXList(ArrayList<ShiXiangModuleBean> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 添加新的一页数据
     *
     * @param list
     */
    public void addSXList(ArrayList<ShiXiangModuleBean> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }


    public PopupWindowAdapter(Context mContext, ArrayList<ShiXiangModuleBean> list) {
        this.mContext = mContext;
        this.mList = list;
        shiXiangFragment = new ShiXiangFragment();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        holder = new ShiXiangHolder(View.inflate(mContext, R.layout.item_shi_xiang, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position == TYPE_HEAD) {
            ShiXiangHolder aqb = (ShiXiangHolder) holder;
            aqb.tvShixiang.setText(R.string.quanbu);
            aqb.tvShixiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.itemClickListener(null);
                    }
                }
            });
            return;
        }
        final ShiXiangModuleBean bean = mList.get(position - 1);
        ShiXiangHolder sxh = (ShiXiangHolder) holder;
        sxh.tvShixiang.setText(bean.getMc());
        sxh.tvShixiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.itemClickListener(bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_HEAD) {
            return TYPE_HEAD;
        } else {
            return TYPE_ITEM;
        }
    }

    /**
     * 实行中心窗口的固定器
     */
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
        void itemClickListener(ShiXiangModuleBean bean);
    }
}
