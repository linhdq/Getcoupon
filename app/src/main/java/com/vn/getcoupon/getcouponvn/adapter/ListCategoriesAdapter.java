package com.vn.getcoupon.getcouponvn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.intef.OnItemFollowClicked;
import com.vn.getcoupon.getcouponvn.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZYuTernity on 14/05/2017.
 */

public class ListCategoriesAdapter extends RecyclerView.Adapter<ListCategoriesAdapter.ViewHolder> {
    //
    private List<CategoryModel> list;
    private List<String> listFollow;
    private Context context;
    private LayoutInflater inflater;
    private CategoryModel model;
    private OnItemFollowClicked listener;
    private int itemHeight;

    public ListCategoriesAdapter(List<CategoryModel> list, List<String> listFollow, Context context, OnItemFollowClicked listener, int itemHeight) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
        }
        if (listFollow != null) {
            this.listFollow = listFollow;
        } else {
            this.listFollow = new ArrayList<>();
        }
        this.context = context;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
        this.itemHeight = itemHeight;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_on_list_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (list.size() > position) {
            model = list.get(position);
            if (model != null) {
                holder.position = position;
                holder.txtName.setText(model.getName());
                Glide.with(context).load(model.getLogo()).into(holder.imvIcon);
                if (!listFollow.contains(String.valueOf(model.getId()))) {
                    holder.imvAdd.setRotation(0.0f);
                    holder.imvIcon.setColorFilter(context.getResources().getColor(R.color.grey_900));
                } else {
                    holder.imvAdd.setRotation(45.0f);
                    holder.imvIcon.setColorFilter(context.getResources().getColor(R.color.red_primary));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imvIcon;
        private TextView txtName;
        private ImageView imvAdd;
        private RelativeLayout itemFollow;
        private RelativeLayout layout;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            //view
            imvIcon = (ImageView) itemView.findViewById(R.id.imv_icon);
            txtName = (TextView) itemView.findViewById(R.id.txt_category_name);
            imvAdd = (ImageView) itemView.findViewById(R.id.imv_add);
            itemFollow = (RelativeLayout) itemView.findViewById(R.id.layout_bottom);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout_item);
            //
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int margin = 2 * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
            layoutParams.setMargins(margin, margin, margin, margin);
            layout.setLayoutParams(layoutParams);
            //
            itemFollow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_bottom:
                    listener.onItemFollowClicked(position);
                    break;
                default:
                    break;
            }
        }
    }
}
