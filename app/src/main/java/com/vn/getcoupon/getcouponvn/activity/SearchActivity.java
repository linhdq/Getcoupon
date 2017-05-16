package com.vn.getcoupon.getcouponvn.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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

import java.util.List;

public class SearchActivity extends AppCompatActivity implements OnItemRecyclerViewClicked, View.OnClickListener {
    //view
    private RecyclerView recyclerView;
    private EditText editText;
    private TextView txtError;
    private Dialog dialog;
    private ImageView imvLogo;
    private ImageView imvClose;
    private TextView txtTitle;
    private TextView txtCode;
    private RelativeLayout itemCopy;
    private LinearLayout itemWebsite;
    private TextView txtNumberUsed;
    private Toast toast;
    //
    private DBContext dbContext;
    private JSONCouponItem model;
    //
    private ListCouponAdapter adapter;
    //
    private List<JSONCouponItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //
        //show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tìm kiếm");
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
        editText = (EditText) findViewById(R.id.edt_search);
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
    }

    private void configRecyclerView() {
        list = dbContext.getAllCoupons();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        adapter = new ListCouponAdapter(list, this, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
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

    private void showToast(String mess) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, mess, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void addListener() {
        imvClose.setOnClickListener(this);
        itemCopy.setOnClickListener(this);
        itemWebsite.setOnClickListener(this);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list = dbContext.getCouponsSearch(s.toString().trim());
                adapter.swap(list);
                Log.d("hello", "afterTextChanged: " + list.size());
            }
        });
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
