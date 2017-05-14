package com.vn.getcoupon.getcouponvn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.model.ItemFollowDrawerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linhdq on 4/22/17.
 */

public class ListItemDrawerAdapter extends BaseAdapter {
    private List<ItemFollowDrawerModel> list;
    private Context context;
    private LayoutInflater inflater;

    public ListItemDrawerAdapter(List<ItemFollowDrawerModel> list, Context context) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
        }
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_on_list_follow_drawer, parent, false);
        if (view != null) {
            ImageView imageView = (ImageView) view.findViewById(R.id.imv_icon);
            TextView txtTitle = (TextView) view.findViewById(R.id.txt_title);

            if (list.size() > position) {
                Glide.with(context).load(list.get(position).getIcon()).into(imageView);
                txtTitle.setText(list.get(position).getTitle());
            }
        }
        return view;
    }
}
