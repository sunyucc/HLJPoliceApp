package hlpolice.pahlj.com.hljpoliceapp.adapter;

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
import hlpolice.pahlj.com.hljpoliceapp.I;
import hlpolice.pahlj.com.hljpoliceapp.R;
import hlpolice.pahlj.com.hljpoliceapp.bean.FunctionBean;
import hlpolice.pahlj.com.hljpoliceapp.ui.HtmlActivity;
import hlpolice.pahlj.com.hljpoliceapp.utils.ImageLoader;
import hlpolice.pahlj.com.hljpoliceapp.utils.L;


/**
 * Created by sunyu on 2016/11/15.
 */

public class HomePageAdapter extends RecyclerView.Adapter {
    Context mContext;
    private ArrayList<FunctionBean.DataBean> mList;
    private String url = null;

    public HomePageAdapter(Context mContext, ArrayList mList) {
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

    public void initData(ArrayList<FunctionBean.DataBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HomePageViewHolder gvh = (HomePageViewHolder) holder;

        FunctionBean.DataBean bean = mList.get(position);
        L.e("bean====" + bean.toString());
        ImageLoader.downloadImg(mContext, gvh.imageView, bean.getTbdz(), true);
        gvh.textView.setText(bean.getMkmc());
        url = mList.get(position).getQqdz();
        if (url.contains(I.App_OLD_TYPE)) {
            url = mList.get(position).getQqdz().replaceAll(I.App_OLD_TYPE, I.APP_TYPE);
        }
        gvh.recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
