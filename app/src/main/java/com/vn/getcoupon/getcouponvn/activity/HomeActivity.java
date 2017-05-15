package com.vn.getcoupon.getcouponvn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.adapter.ListItemDrawerAdapter;
import com.vn.getcoupon.getcouponvn.database.DBContext;
import com.vn.getcoupon.getcouponvn.fragment.BrandFragment;
import com.vn.getcoupon.getcouponvn.fragment.CategoryFragment;
import com.vn.getcoupon.getcouponvn.fragment.SaleCodeFragment;
import com.vn.getcoupon.getcouponvn.model.FollowListModel;
import com.vn.getcoupon.getcouponvn.services.NotificationService;

import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements View.OnClickListener {
    //view
    private RelativeLayout itemInfo;
    private DrawerLayout drawer;
    private ListView listViewDrawer;
    private TextView txtNothing;
    private ViewPager viewPager;
    private SmartTabLayout smartTabLayout;
    //adapter
    public ListItemDrawerAdapter itemDrawerAdapter;
    private FragmentPagerItemAdapter adapter;
    //
    private List<FollowListModel> followDrawerModelList;
    //
    private DBContext dbContext;

    // Service
    private Intent intentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //
        init();
        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //
        fillDataToDrawerList();
        fillDataToViewPager();
    }

    private void init() {
        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //
        itemInfo = (RelativeLayout) findViewById(R.id.item_info);
        listViewDrawer = (ListView) findViewById(R.id.list_item_follow);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        smartTabLayout = (SmartTabLayout) findViewById(R.id.view_pager_tab);
        txtNothing = (TextView) findViewById(R.id.txt_nothing);
        //
        dbContext = DBContext.getInst();

        // Notification Service
        intentService = new Intent(this, NotificationService.class);
        startService(intentService);
    }

    public void checkHasData(int visible) {
        txtNothing.setVisibility(visible);
    }

    private void addListener() {
        itemInfo.setOnClickListener(this);
    }

    private void fillDataToDrawerList() {
        if (itemDrawerAdapter == null) {
            followDrawerModelList = dbContext.getAllFollowModel();
            //
            itemDrawerAdapter = new ListItemDrawerAdapter(followDrawerModelList, this);
            //
            listViewDrawer.setAdapter(itemDrawerAdapter);
            //
            if (followDrawerModelList.size() != 0) {
                checkHasData(View.GONE);
            } else {
                checkHasData(View.VISIBLE);
            }
        }
    }

    private void fillDataToViewPager() {
        if (adapter == null) {
            adapter = new FragmentPagerItemAdapter(
                    getSupportFragmentManager(), FragmentPagerItems.with(this)
                    .add(R.string.brand_name, BrandFragment.class)
                    .add(R.string.category, CategoryFragment.class)
                    .add(R.string.sale_code, SaleCodeFragment.class)
                    .create());
            viewPager.setAdapter(adapter);
            smartTabLayout.setViewPager(viewPager);
            viewPager.setOffscreenPageLimit(3);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_info:
                Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                drawer.closeDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
    }
}
