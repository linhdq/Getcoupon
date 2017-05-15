package com.vn.getcoupon.getcouponvn.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vn.getcoupon.getcouponvn.R;
import com.vn.getcoupon.getcouponvn.activity.ListCouponsActivity;
import com.vn.getcoupon.getcouponvn.intef.OnItemRecyclerViewClicked;
import com.vn.getcoupon.getcouponvn.model.json_model.JSONCouponItem;
import com.vn.getcoupon.getcouponvn.utilities.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linhdq on 5/15/17.
 */

public class ListCouponAdapter extends RecyclerView.Adapter<ListCouponAdapter.ViewHolder> {
    //
    private List<JSONCouponItem> list;
    private Context context;
    private LayoutInflater inflater;
    private JSONCouponItem model;
    private OnItemRecyclerViewClicked listener;

    public ListCouponAdapter(List<JSONCouponItem> list, Context context, OnItemRecyclerViewClicked listener) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
        }
        this.context = context;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_on_list_coupon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (list.size() > position) {
            model = list.get(position);
            if (model != null) {
                if (model.getCouponType().equalsIgnoreCase("code")) {
                    Glide.with(context).load(R.mipmap.ico_coupon).into(holder.imvType);
                    Glide.with(context).load(R.mipmap.btn_copy_ma).into(holder.imvButton);
                    holder.txtCode.setText(model.getCode());
                    holder.txtButtonLabel.setText("Lấy mã");
                } else {
                    Glide.with(context).load(R.mipmap.ico_sale).into(holder.imvType);
                    Glide.with(context).load(R.mipmap.xem_ngay).into(holder.imvButton);
                    holder.txtButtonLabel.setText("    Xem ngay");
                }
                Glide.with(context).load(model.getLogoUrl()).into(holder.imvLogo);
                holder.txtTitle.setText(model.getTitle());
                holder.txtExpireDate.setText("Hạn: " + model.getExpiresDate());
                holder.txtPercentOk.setText(model.getPersenSuccess() + "% OK");
                holder.txtContent.setText(model.getContent().replace("\n", "").replace("\r", "").trim());
                holder.position = position;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imvType;
        private ImageView imvLogo;
        private TextView txtTitle;
        private TextView txtExpireDate;
        private TextView txtPercentOk;
        private TextView txtContent;
        private TextView txtCode;
        private TextView txtButtonLabel;
        private ImageView imvButton;
        private RelativeLayout layoutButton;
        //
        private int position;
        //
        private Animation animation;

        public ViewHolder(View itemView) {
            super(itemView);
            //view
            imvType = (ImageView) itemView.findViewById(R.id.imv_type);
            imvLogo = (ImageView) itemView.findViewById(R.id.imv_logo);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            txtExpireDate = (TextView) itemView.findViewById(R.id.txt_expire_date);
            txtPercentOk = (TextView) itemView.findViewById(R.id.txt_percent_ok);
            txtContent = (TextView) itemView.findViewById(R.id.txt_content);
            txtCode = (TextView) itemView.findViewById(R.id.txt_code);
            txtButtonLabel = (TextView) itemView.findViewById(R.id.txt_button_type_label);
            imvButton = (ImageView) itemView.findViewById(R.id.imv_button_type);
            layoutButton = (RelativeLayout) itemView.findViewById(R.id.layout_button);
            //
            animation = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
            //
            layoutButton.setOnClickListener(this);
            imvLogo.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.layout_button:
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            listener.onItemRecyclerViewClicked(position);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    layoutButton.startAnimation(animation);
                    break;
                case R.id.imv_logo:
                    Intent intent = new Intent(context, ListCouponsActivity.class);
                    intent.putExtra(Constant.STORE_NAME, list.get(position).getStoreName());
                    intent.putExtra(Constant.STORE_ID, list.get(position).getStoreId());
                    intent.putExtra(Constant.CATEGORY_ID, "-1");
                    context.startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
}
