package com.vn.getcoupon.getcouponvn.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.vn.getcoupon.getcouponvn.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    //view
    private LinearLayout itemWebsite;
    private LinearLayout itemFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông tin");
        //
        init();
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
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        //view
        itemWebsite = (LinearLayout) findViewById(R.id.item_website);
        itemFeedback = (LinearLayout) findViewById(R.id.item_feedback);
    }

    private void addListener() {
        itemFeedback.setOnClickListener(this);
        itemWebsite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_feedback:
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("vnd.android.cursor.item/email");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"abc@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Phản hồi từ Android apps.");
                startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
                break;
            case R.id.item_website:
                String url = "https://getcoupon.vn";
                Intent intentWeb = new Intent(Intent.ACTION_VIEW);
                intentWeb.setData(Uri.parse(url));
                startActivity(intentWeb);
                break;
            default:
                break;
        }
    }
}
