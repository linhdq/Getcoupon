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
import com.vn.getcoupon.getcouponvn.activity.HomeActivity;
import com.vn.getcoupon.getcouponvn.adapter.ListStoreApdater;
import com.vn.getcoupon.getcouponvn.database.DBContext;
import com.vn.getcoupon.getcouponvn.intef.OnItemFollowClicked;
import com.vn.getcoupon.getcouponvn.model.FollowListModel;
import com.vn.getcoupon.getcouponvn.model.json_model.JSONStoreItem;
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

public class BrandFragment extends Fragment implements OnItemFollowClicked {
    //view
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView txtError;
    private Context context;
    //
    private ListStoreApdater apdater;
    //
    private List<JSONStoreItem> list;
    private List<String> listFollow;
    private JSONStoreItem jsonStoreItem;
    //
    private GetService getService;
    //
    private DBContext dbContext;
    //
    private HomeActivity homeActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand, container, false);

        init(view);
        setupRecyclerView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //
        if (listFollow == null || listFollow.size() == 0) {
            listFollow.clear();
            listFollow.addAll(dbContext.getAllFollowItem(2));
            apdater.notifyDataSetChanged();
        }
        if (list == null || list.size() == 0) {
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
        //
        dbContext = DBContext.getInst();
        //
        homeActivity = (HomeActivity) getActivity();
    }

    private void setupRecyclerView() {
        list = new ArrayList<>();
        listFollow = new ArrayList<>();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        apdater = new ListStoreApdater(context, list, listFollow, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(apdater);
    }

    private void getListStore() {
        //
        recyclerView.setVisibility(View.GONE);
        txtError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        //
        Call<List<JSONStoreItem>> call = getService.callJsonListStore();
        call.enqueue(new Callback<List<JSONStoreItem>>() {
            @Override
            public void onResponse(Call<List<JSONStoreItem>> call, Response<List<JSONStoreItem>> response) {
                if (response.code() == Constant.OK_STATUS) {
                    List<JSONStoreItem> jsonStoreList = response.body();
                    if (jsonStoreList != null && jsonStoreList.size() != 0) {
                        list.clear();
                        list.addAll(jsonStoreList);
                        apdater.notifyDataSetChanged();
                        recyclerView.setVisibility(View.VISIBLE);
                        Log.d("hello", "onResponse: " + list.size());
                    }
                } else {
                    txtError.setVisibility(View.VISIBLE);
                }
                //hide progress bar
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<JSONStoreItem>> call, Throwable t) {
                txtError.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onItemFollowClicked(int position) {
        jsonStoreItem = list.get(position);
        if (listFollow.contains(jsonStoreItem.getId())) {
            dbContext.deleteFollowModel(jsonStoreItem.getId());
            listFollow.remove(listFollow.indexOf(jsonStoreItem.getId()));
        } else {
            dbContext.addFollowItem(FollowListModel.create(jsonStoreItem.getId(), jsonStoreItem.getName(), jsonStoreItem.getLogoUrl(),0,2));
            listFollow.add(jsonStoreItem.getId());
        }
        apdater.notifyDataSetChanged();
        homeActivity.itemDrawerAdapter.notifyDataSetChanged();
        if (listFollow.size() == 0) {
            homeActivity.checkHasData(View.VISIBLE);
        } else {
            homeActivity.checkHasData(View.GONE);
        }
    }
}
