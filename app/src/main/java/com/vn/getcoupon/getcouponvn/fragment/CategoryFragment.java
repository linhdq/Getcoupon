package com.vn.getcoupon.getcouponvn.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.activity.HomeActivity;
import com.vn.getcoupon.getcouponvn.adapter.ListCategoriesAdapter;
import com.vn.getcoupon.getcouponvn.database.DBContext;
import com.vn.getcoupon.getcouponvn.intef.OnItemFollowClicked;
import com.vn.getcoupon.getcouponvn.model.CategoryModel;
import com.vn.getcoupon.getcouponvn.model.FollowCategoryListModel;
import com.vn.getcoupon.getcouponvn.model.FollowListModel;

import java.util.List;

/**
 * Created by linhdq on 4/22/17.
 */

public class CategoryFragment extends Fragment implements OnItemFollowClicked {
    //view
    private RecyclerView recyclerView;
    //
    private Context context;
    //
    private List<CategoryModel> list;
    private List<String> listFollow;
    //
    private DBContext dbContext;
    //
    private ListCategoriesAdapter adapter;
    private CategoryModel categoryModel;
    //
    private HomeActivity homeActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        init(view);
        configRecyclerView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //
        initData();
    }

    private void init(View view) {
        //view
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_list_categories);
        //
        context = view.getContext();
        dbContext = DBContext.getInst();
        //
        list = dbContext.getAllCategory();
        listFollow = dbContext.getAllFollowItem(1);
        //
        homeActivity = (HomeActivity) getActivity();
    }

    private void initData() {
        if (list.size() == 0) {
            dbContext.addCategory(CategoryModel.create(51+"", "Hot Coupon", R.mipmap.ic_discount));
            dbContext.addCategory(CategoryModel.create(38+"", "Giáo dục", R.mipmap.ic_mortarboard));
            dbContext.addCategory(CategoryModel.create(36+"", "Sức khoẻ - Làm đẹp", R.mipmap.ic_hair_dryer));
            dbContext.addCategory(CategoryModel.create(35+"", "Sách - Quà tặng", R.mipmap.ic_notebook));
            dbContext.addCategory(CategoryModel.create(33+"", "Giải trí - Du lịch", R.mipmap.ic_beach));
            dbContext.addCategory(CategoryModel.create(32+"", "Gia dụng", R.mipmap.ic_tools));
            dbContext.addCategory(CategoryModel.create(29+"", "Công nghệ", R.mipmap.ic_desktop));
            dbContext.addCategory(CategoryModel.create(44+"", "Tài chính - Ngân hàng", R.mipmap.ic_money_bag));
            dbContext.addCategory(CategoryModel.create(45+"", "Ẩm thực", R.mipmap.ic_restaurant));
            dbContext.addCategory(CategoryModel.create(46+"", "Thể thao", R.mipmap.ic_football));
            dbContext.addCategory(CategoryModel.create(31+"", "Điện tử - Điện lạnh", R.mipmap.ic_refrigerator));
            dbContext.addCategory(CategoryModel.create(37+"", "Thời trang", R.mipmap.ic_shirt));
            dbContext.addCategory(CategoryModel.create(34+"", "Mẹ và bé", R.mipmap.ic_newborn));
            dbContext.addCategory(CategoryModel.create(106+"", "Văn phòng phẩm", R.mipmap.ic_pencil));
            dbContext.addCategory(CategoryModel.create(220+"", "Di chuyển - Vận chuyển", R.mipmap.ic_delivery_truck));
            adapter.notifyDataSetChanged();
        }
    }

    private void configRecyclerView() {
        adapter = new ListCategoriesAdapter(list, listFollow, context, this, calHeightForItem());
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private int calHeightForItem() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(displayMetrics);

        return displayMetrics.widthPixels / 2;
    }

    @Override
    public void onItemFollowClicked(int position) {
        categoryModel = list.get(position);
        if (listFollow.contains(categoryModel.getId())) {
            dbContext.deleteFollowModel(categoryModel.getId());
            listFollow.remove(listFollow.indexOf(categoryModel.getId()));
        } else {
            dbContext.addFollowItem(FollowListModel.create(categoryModel.getId(), categoryModel.getName(), "", categoryModel.getLogo(), 1));
            listFollow.add(categoryModel.getId());
        }
        adapter.notifyDataSetChanged();
        homeActivity.itemDrawerAdapter.notifyDataSetChanged();
        if (listFollow.size() == 0) {
            homeActivity.checkHasData(View.VISIBLE);
        } else {
            homeActivity.checkHasData(View.GONE);
        }
    }
}
