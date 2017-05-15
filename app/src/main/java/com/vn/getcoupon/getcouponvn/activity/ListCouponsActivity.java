package com.vn.getcoupon.getcouponvn.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.adapter.ListCouponAdapter;
import com.vn.getcoupon.getcouponvn.database.DBContext;
import com.vn.getcoupon.getcouponvn.intef.OnItemRecyclerViewClicked;
import com.vn.getcoupon.getcouponvn.model.json_model.JSONCouponItem;
import com.vn.getcoupon.getcouponvn.utilities.Constant;

import java.util.List;

public class ListCouponsActivity extends AppCompatActivity implements OnItemRecyclerViewClicked {
    //view
    private RecyclerView recyclerView;
    private TextView txtError;
    private Toast toast;
    //
    private DBContext dbContext;
    private JSONCouponItem model;
    //
    private ListCouponAdapter adapter;
    //
    private List<JSONCouponItem> list;
    //
    private String storeName;
    private String storeId;
    private String categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_coupons);
        //
        init();
        configRecyclerView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        //view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        txtError = (TextView) findViewById(R.id.txt_error);
        //
        dbContext = DBContext.getInst();
        //
        storeName = getIntent().getStringExtra(Constant.STORE_NAME);
        storeId = getIntent().getStringExtra(Constant.STORE_ID);
        categoryId = getIntent().getStringExtra(Constant.CATEGORY_ID);
        //show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(storeName);
    }

    private void showToast(String mess) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, mess, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void configRecyclerView() {
        if (categoryId.equalsIgnoreCase("-1")) {
            list = dbContext.getCouponsByStoreId(storeId);
        } else {
            list = dbContext.getCouponsByCategoryId(categoryId);
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        adapter = new ListCouponAdapter(list, this, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Log.d("hello", "configRecyclerView: " + list.size());
        if (list.size() == 0) {
            txtError.setVisibility(View.VISIBLE);
        } else {
            txtError.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemRecyclerViewClicked(int position) {
        model = list.get(position);
        if (model.getCouponType().equalsIgnoreCase("code")) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Đã copy mã", model.getCode());
            clipboard.setPrimaryClip(clip);
            showToast("Đã copy mã!");
        } else {
            Intent intentWeb = new Intent(Intent.ACTION_VIEW);
            intentWeb.setData(Uri.parse(model.getDestinationUrl()));
            startActivity(intentWeb);
        }
    }
}
