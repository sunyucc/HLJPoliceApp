package hljpolice.pahlj.com.hljpoliceapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hljpolice.pahlj.com.hljpoliceapp.HLJPoliceApplication;
import hljpolice.pahlj.com.hljpoliceapp.R;
import hljpolice.pahlj.com.hljpoliceapp.bean.ShiXiangBean;
import hljpolice.pahlj.com.hljpoliceapp.dao.NetDao;

/**
 * 事项中心列表的适配器
 * Created by sunyu on 2016/11/23.
 */

public class ShiXiangAdapter extends RecyclerView.Adapter {
    Context mContext;
    private OnItemClickListener listener;
    ArrayList<ShiXiangBean.DataBean> mList;
    /**
     * 初始化事项中心列表
     *
     * @param list
     */
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
        final ShiXiangHolder gvh = (ShiXiangHolder) holder;
        final ShiXiangBean.DataBean bean = mList.get(position);
        gvh.tvShixiang.setText(bean.getSxmc());
        if (HLJPoliceApplication.getInstance().getVersion() != null) {
            if (Integer.parseInt(HLJPoliceApplication.getInstance().getVersion().getComm().getSxdjs()) == 1) {
                gvh.tvDjs.setText(String.valueOf(bean.getDjs()));
            } else {
                gvh.tvDjs.setVisibility(View.GONE);
            }
        }
        gvh.tvShixiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HLJPoliceApplication.getInstance().getVersion() != null) {
                    gvh.tvDjs.setText(String.valueOf(bean.getDjs() + 1));
                }
               NetDao.djsUpload(mContext,mList.get(position).getSxid());
                listener.itemClickListener(mList.get(position));
//                String sxmc = mList.get(position).getSxmc();
//                String url = null;
//                url = I.SHIXIANG_ADDRESS +
//                        "?bmid=" + mList.get(position).getZdBsckid() +
//                        "&sxid=" + mList.get(position).getSxid() +
//                        "&zn=" + mList.get(position).getZn() +
//                        "&yy=" + mList.get(position).getYy() +
//                        "&sb=" + mList.get(position).getSb() +
//                        "&sxmc=" + Escape.escape(sxmc);
//                Intent intent = new Intent(mContext, HtmlActivity.class).putExtra("url", url);
//                mContext.startActivity(intent);
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
        @BindView(R.id.tv_djs)
        TextView tvDjs;
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
