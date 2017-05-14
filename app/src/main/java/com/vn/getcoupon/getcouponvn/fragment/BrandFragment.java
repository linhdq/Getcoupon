package com.vn.getcoupon.getcouponvn.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.adapter.ListStoreApdater;
import com.vn.getcoupon.getcouponvn.model.json_model.JSONStoreItem;
import com.vn.getcoupon.getcouponvn.model.json_model.JSONStoreList;
import com.vn.getcoupon.getcouponvn.network.GetService;
import com.vn.getcoupon.getcouponvn.network.ServiceFactory;
import com.vn.getcoupon.getcouponvn.utilities.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by linhdq on 4/22/17.
 */

public class BrandFragment extends Fragment {
    //view
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView txtError;
    private Context context;
    //
    private ListStoreApdater apdater;
    //
    private List<JSONStoreItem> list;
    //
    private GetService getService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand, container, false);

        init(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //
        setupRecyclerView();
        //
        if (list.size() == 0) {
            getListStore();
        }
    }

    private void init(View view) {
        //view
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        txtError = (TextView) view.findViewById(R.id.txt_error);
        //network
        getService = ServiceFactory.getInstance().createService(GetService.class);
        //
        context = view.getContext();
        list = new ArrayList<>();
    }

    private void setupRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        apdater = new ListStoreApdater(context, list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(apdater);
    }

    private void getListStore() {
        //
        recyclerView.setVisibility(View.GONE);
        txtError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        //
        Call<JSONStoreList> call = getService.callJsonListStore();
        call.enqueue(new Callback<JSONStoreList>() {
            @Override
            public void onResponse(Call<JSONStoreList> call, Response<JSONStoreList> response) {
                if (response.code() == Constant.OK_STATUS) {
                    Log.d("hello", "onResponse: ok");
                    JSONStoreList jsonStoreList = response.body();
                    List<JSONStoreItem> storeItemList = jsonStoreList.getStoreItemList();
                    if (storeItemList != null && storeItemList.size() != 0) {
                        list.clear();
                        list.addAll(storeItemList);
                        apdater.notifyDataSetChanged();
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                } else {
                    txtError.setVisibility(View.VISIBLE);
                    Log.d("hello", "onResponse: fail "+response.code());
                }
                //hide progress bar
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<JSONStoreList> call, Throwable t) {
                txtError.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Log.d("hello", "onResponse: fail");
            }
        });
    }
}
