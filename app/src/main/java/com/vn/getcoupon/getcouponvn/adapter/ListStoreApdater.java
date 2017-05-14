package com.vn.getcoupon.getcouponvn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.model.json_model.JSONStoreItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linhdq on 5/14/17.
 */

public class ListStoreApdater extends RecyclerView.Adapter<ListStoreApdater.ViewHolder> {

    private List<JSONStoreItem> list;
    private LayoutInflater inflater;
    private Context context;
    private JSONStoreItem model;

    public ListStoreApdater(Context context, List<JSONStoreItem> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
        }
        this.context = context;
        this.inflater = LayoutInflater.from(context);
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
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imvLogo;
        private TextView txtName;
        private LinearLayout itemFollow;

        public ViewHolder(View itemView) {
            super(itemView);
            //view
            imvLogo = (ImageView) itemView.findViewById(R.id.imv_logo);
            txtName = (TextView) itemView.findViewById(R.id.txt_store_name);
            itemFollow = (LinearLayout) itemView.findViewById(R.id.item_follow);
            //
            itemFollow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_follow:

                    break;
                default:
                    break;
            }
        }
    }

    public void swap(List<JSONStoreItem> list) {
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }
}
