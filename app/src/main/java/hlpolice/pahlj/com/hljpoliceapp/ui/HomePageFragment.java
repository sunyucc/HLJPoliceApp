package hlpolice.pahlj.com.hljpoliceapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hlpolice.pahlj.com.hljpoliceapp.R;
import hlpolice.pahlj.com.hljpoliceapp.adapter.HomePageAdapter;
import hlpolice.pahlj.com.hljpoliceapp.views.SpaceItemDecoration;


public class HomePageFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRv;
    Context mContext;
    HomePageAdapter mAdapter;
    ArrayList<> mList;
    GridLayoutManager glm ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, layout);
        mContext = getContext();
        mList = new ArrayList<>();
        mAdapter = new HomePageAdapter(mContext,mList);
        initView();
        initData();
        return layout;
    }


    protected void initData() {
    }
    protected  void initView() {
        glm= new GridLayoutManager(mContext, 4);
        mRv.setLayoutManager(glm);
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new SpaceItemDecoration(10));
    }

}
