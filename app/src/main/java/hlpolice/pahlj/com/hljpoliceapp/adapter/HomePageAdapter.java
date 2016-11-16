package hlpolice.pahlj.com.hljpoliceapp.adapter;

/**
 * Created by sunyu on 2016/11/15.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hlpolice.pahlj.com.hljpoliceapp.R;
import hlpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hlpolice.pahlj.com.hljpoliceapp.utils.ImageLoader;


/**
 * Created by sunyu on 2016/11/15.
 */

public class HomePageAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<FunctionBean> mList;

    public HomePageAdapter(Context mContext, ArrayList mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;

        holder = new HomePageViewHolder(View.inflate(mContext, R.layout.item_page_home, null));

        return holder;
    }

    public void initData(ArrayList<FunctionBean> list) {
        if (mList != null) {
            mList.clear();
        }
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
//                if (Integer.parseInt(list.get(i).getMklb()) == 01) {
//                    mList.add(list.get(i));
//                } else if (Integer.parseInt(list.get(i).getMklb()) == 03) {
//                    mContext.sendBroadcast(new Intent());
//                }
                if ("01".equals(list.get(i).getMklb())) {
                    mList.add(list.get(i));
                } else if ("02".equals(list.get(i).getMklb())) {

                }
            }

        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomePageViewHolder gvh = (HomePageViewHolder) holder;

        FunctionBean bean = mList.get(position);
        ImageLoader.downloadImg(mContext, gvh.imageView, bean.getTbdz(), true);
        gvh.textView.setText(bean.getMkmc());
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

        HomePageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
