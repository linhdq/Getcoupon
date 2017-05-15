package com.vn.getcoupon.getcouponvn.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.adapter.ListCouponAdapter;
import com.vn.getcoupon.getcouponvn.database.DBContext;
import com.vn.getcoupon.getcouponvn.intef.OnItemRecyclerViewClicked;
import com.vn.getcoupon.getcouponvn.model.json_model.JSONCouponItem;
import com.vn.getcoupon.getcouponvn.model.json_model.JSONStoreItem;
import com.vn.getcoupon.getcouponvn.network.GetService;
import com.vn.getcoupon.getcouponvn.network.ServiceFactory;
import com.vn.getcoupon.getcouponvn.utilities.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by linhdq on 4/22/17.
 */

public class SaleCodeFragment extends Fragment implements OnItemRecyclerViewClicked {
    //view
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView txtError;
    private Toast toast;
    //
    private Context context;
    //
    private DBContext dbContext;
    private GetService getService;
    private JSONCouponItem model;
    //
    private ListCouponAdapter adapter;
    //
    private List<JSONCouponItem> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale_code, container, false);

        init(view);
        configRecyclerView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (list.size() == 0) {
            getListCoupon();
        }
    }

    private void init(View view) {
        //view
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        txtError = (TextView) view.findViewById(R.id.txt_error);
        //
        context = view.getContext();
        //
        dbContext = DBContext.getInst();
        //network
        getService = ServiceFactory.getInstance().createService(GetService.class);
    }

    private void showToast(String mess) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, mess, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void configRecyclerView() {
        list = dbContext.getAllCoupons();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        adapter = new ListCouponAdapter(list, context, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Log.d("hello", "configRecyclerView: " + list.size());
    }

    @Override
    public void onItemRecyclerViewClicked(int position) {
        model = list.get(position);
        if (model.getCouponType().equalsIgnoreCase("code")) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Đã copy mã", model.getCode());
            clipboard.setPrimaryClip(clip);
            showToast("Đã copy mã!");
        }else{
            Intent intentWeb = new Intent(Intent.ACTION_VIEW);
            intentWeb.setData(Uri.parse(model.getDestinationUrl()));
            startActivity(intentWeb);
        }
    }

    private void getListCoupon() {
        //
        recyclerView.setVisibility(View.GONE);
        txtError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        //
        Call<List<JSONCouponItem>> call = getService.callJsonListCoupon();
        call.enqueue(new Callback<List<JSONCouponItem>>() {
            @Override
            public void onResponse(Call<List<JSONCouponItem>> call, Response<List<JSONCouponItem>> response) {
                if (response.code() == Constant.OK_STATUS) {
                    List<JSONCouponItem> jsonCouponItems = response.body();
                    if (jsonCouponItems != null && jsonCouponItems.size() != 0) {
                        dbContext.addCoupon(jsonCouponItems);
                        adapter.notifyDataSetChanged();
                        recyclerView.setVisibility(View.VISIBLE);
                        Log.d("hello", "onResponse:coupon " + list.size());
                    }
                } else {
                    txtError.setVisibility(View.VISIBLE);
                }
                //hide progress bar
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<JSONCouponItem>> call, Throwable t) {
                txtError.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }
}
