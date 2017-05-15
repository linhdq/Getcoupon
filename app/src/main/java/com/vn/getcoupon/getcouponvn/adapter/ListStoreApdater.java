package com.vn.getcoupon.getcouponvn.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.activity.HomeActivity;
import com.vn.getcoupon.getcouponvn.activity.ListCouponsActivity;
import com.vn.getcoupon.getcouponvn.intef.OnItemFollowClicked;
import com.vn.getcoupon.getcouponvn.model.json_model.JSONStoreItem;
import com.vn.getcoupon.getcouponvn.utilities.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linhdq on 5/14/17.
 */

public class ListStoreApdater extends RecyclerView.Adapter<ListStoreApdater.ViewHolder> {

    private List<JSONStoreItem> list;
    private List<String> listFollow;
    private LayoutInflater inflater;
    private Context context;
    private JSONStoreItem model;
    private OnItemFollowClicked listener;

    public ListStoreApdater(Context context, List<JSONStoreItem> list, List<String> listFollow, OnItemFollowClicked listener) {
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
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_on_list_store, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (list.size() > position) {
            model = list.get(position);
            if (model != null) {
                Glide.with(context).load(model.getLogoUrl()).into(holder.imvLogo);
                holder.txtName.setText(model.getName());
                holder.position = position;
                if (!listFollow.contains(model.getId())) {
                    holder.imvFollow.setRotation(0.0f);
                    holder.imvFollow.setColorFilter(context.getResources().getColor(R.color.teal_primary));
                    holder.txtFollow.setText("Theo dõi");
                } else {
                    holder.imvFollow.setRotation(45.0f);
                    holder.imvFollow.setColorFilter(context.getResources().getColor(R.color.red_primary));
                    holder.txtFollow.setText("Bỏ theo dõi");
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imvLogo;
        private ImageView imvFollow;
        private TextView txtName;
        private TextView txtFollow;
        private LinearLayout itemFollow;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            //view
            imvLogo = (ImageView) itemView.findViewById(R.id.imv_logo);
            imvFollow = (ImageView) itemView.findViewById(R.id.imv_follow);
            txtName = (TextView) itemView.findViewById(R.id.txt_store_name);
            txtFollow = (TextView) itemView.findViewById(R.id.txt_follow);
            itemFollow = (LinearLayout) itemView.findViewById(R.id.item_follow);
            //
            itemFollow.setOnClickListener(this);
            txtName.setOnClickListener(this);
            imvLogo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_follow:
                    listener.onItemFollowClicked(position);
                    break;
                case R.id.imv_logo:
                case R.id.txt_store_name:
                    Intent intent = new Intent(context, ListCouponsActivity.class);
                    intent.putExtra(Constant.STORE_NAME, list.get(position).getName());
                    intent.putExtra(Constant.STORE_ID, list.get(position).getId());
                    intent.putExtra(Constant.CATEGORY_ID, "-1");
                    context.startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
}
