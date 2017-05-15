package com.vn.getcoupon.getcouponvn.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.getcoupon.getcouponvn.R;

/**
 * Created by linhdq on 4/22/17.
 */

public class CategoryFragment extends Fragment {

    private RecyclerView rcvListCategories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);


        return view;
    }

    private void init(View view) {
        //view
        rcvListCategories = (RecyclerView) view.findViewById(R.id.rcv_list_categories);
    }
}
