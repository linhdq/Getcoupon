package com.vn.getcoupon.getcouponvn.activity;

import android.app.Dialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.adapter.ListCouponAdapter;
import com.vn.getcoupon.getcouponvn.database.DBContext;
import com.vn.getcoupon.getcouponvn.intef.OnItemRecyclerViewClicked;
import com.vn.getcoupon.getcouponvn.model.json_model.JSONCouponItem;
import com.vn.getcoupon.getcouponvn.utilities.Constant;

import java.util.List;

public class ListCouponsActivity extends AppCompatActivity implements OnItemRecyclerViewClicked, View.OnClickListener {
    //view
    private RecyclerView recyclerView;
    private TextView txtError;
    private Toast toast;
    private Dialog dialog;
    private ImageView imvLogo;
    private ImageView imvClose;
    private TextView txtTitle;
    private TextView txtCode;
    private RelativeLayout itemCopy;
    private LinearLayout itemWebsite;
    private TextView txtNumberUsed;
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
        addListener();
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
        dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        //
        imvLogo = (ImageView) dialog.findViewById(R.id.imv_logo);
        imvClose = (ImageView) dialog.findViewById(R.id.imv_close);
        txtTitle = (TextView) dialog.findViewById(R.id.txt_title);
        txtCode = (TextView) dialog.findViewById(R.id.txt_code);
        itemCopy = (RelativeLayout) dialog.findViewById(R.id.item_copy);
        itemWebsite = (LinearLayout) dialog.findViewById(R.id.item_website);
        txtNumberUsed = (TextView) dialog.findViewById(R.id.txt_number_used);
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

    private void addListener() {
        imvClose.setOnClickListener(this);
        itemCopy.setOnClickListener(this);
        itemWebsite.setOnClickListener(this);
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

    private void fillDataAndShowDialog(JSONCouponItem model) {
        if (model != null) {
            Glide.with(this).load(model.getLogoUrl()).into(imvLogo);
            txtTitle.setText(model.getTitle().trim());
            txtCode.setText(model.getCode());
            txtNumberUsed.setText(model.getUsed() + " lượt đã sử dụng.");
            dialog.show();
        }
    }

    @Override
    public void onItemRecyclerViewClicked(int position) {
        model = list.get(position);
        if (model.getCouponType().equalsIgnoreCase("code")) {
            fillDataAndShowDialog(model);
        } else {
            Intent intentWeb = new Intent(Intent.ACTION_VIEW);
            intentWeb.setData(Uri.parse(model.getDestinationUrl()));
            startActivity(intentWeb);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_close:
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                break;
            case R.id.item_copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Đã copy mã", model.getCode());
                clipboard.setPrimaryClip(clip);
                showToast("Đã copy mã!");
                break;
            case R.id.item_website:
                Intent intentWeb = new Intent(Intent.ACTION_VIEW);
                intentWeb.setData(Uri.parse(model.getDestinationUrl()));
                startActivity(intentWeb);
                break;
            default:
                break;
        }
    }
}
