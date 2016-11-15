package hlpolice.pahlj.com.hljpoliceapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hlpolice.pahlj.com.hljpoliceapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SafeFragment extends Fragment {


    public SafeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_safe, container, false);
    }

}
