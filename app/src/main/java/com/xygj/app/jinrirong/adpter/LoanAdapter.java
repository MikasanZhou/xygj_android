package com.xygj.app.jinrirong.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.util.List;

import com.xygj.app.R;
import com.xygj.app.jinrirong.activity.product.ProductDetailActivity;
import com.xygj.app.jinrirong.activity.user.LoginActivity;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.LoanProduct;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.ViewHolder> {

    private Context mContext;
    private List<LoanProduct> mLoanProductList;

    public LoanAdapter(Context context, List<LoanProduct> loanProductList) {
        mContext = context;
        mLoanProductList = loanProductList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final LoanProduct product = mLoanProductList.get(position);
        holder.title.setText(product.getName());
        holder.desc.setText(product.getIntro());
        String wan = product.getAppNumbs();
        if (!wan.contains("万")) {
            try {
                int applyNum = Integer.valueOf(wan);
                if (applyNum > 10000)
                    wan = String.format("%s万", new DecimalFormat("0.0").format(applyNum * 1.0f / 10000f));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        holder.apply_num.setText(String.format("%s人", wan));
        if (product.getRatetype() != null && product.getRatetype().equals("2")) {
            holder.fee.setText(product.getMonthfeeRate());
            holder.tv_fee_type.setText("月费率");
        } else {
            holder.fee.setText(product.getDayfeeRate());
            holder.tv_fee_type.setText("日费率");
        }
        holder.money.setText(product.getTypeName());
        holder.days.setText(product.getJkdays());

        Glide.with(mContext).load(product.getLogurl())
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.getInstance().isLogin()) {
                    mContext.startActivity(ProductDetailActivity.getIntent(mContext, product.getID()));
                } else {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLoanProductList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        TextView apply_num;
        TextView money;
        TextView days;
        TextView fee, tv_fee_type;
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);
            tv_fee_type = itemView.findViewById(R.id.tv_fee_type);
            title = itemView.findViewById(R.id.tv_title);
            desc = itemView.findViewById(R.id.tv_desc);
            apply_num = itemView.findViewById(R.id.tv_apply_num);
            money = itemView.findViewById(R.id.tv_money);
            days = itemView.findViewById(R.id.tv_days);
            fee = itemView.findViewById(R.id.tv_fee);
            img = itemView.findViewById(R.id.iv_img);
        }
    }
}