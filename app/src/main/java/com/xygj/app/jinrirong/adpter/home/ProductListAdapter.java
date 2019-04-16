package com.xygj.app.jinrirong.adpter.home;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xygj.app.R;
import com.xygj.app.jinrirong.activity.product.ProductDetailActivity;
import com.xygj.app.jinrirong.activity.user.LoginActivity;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.LoanProduct;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2019/4/16 0016.
 */

public class ProductListAdapter extends BaseQuickAdapter<LoanProduct,BaseViewHolder> {
    public ProductListAdapter(int layoutResId, @Nullable List<LoanProduct> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final LoanProduct product) {
        if(product!=null){
            baseViewHolder.setText(R.id.tv_title_name,product.getName());
            baseViewHolder.setText(R.id.tv_tips, product.getIntro());
            baseViewHolder.setText(R.id.tv_amount_limit, product.getTypeName()+"元");
            baseViewHolder.setText(R.id.tv_time_limit, product.getJkdays());

            String wan = product.getAppNumbs();
            baseViewHolder.setText(R.id.tv_apply_num,String.format("%s人已申请", wan));
            if (product.getRatetype() != null && product.getRatetype().equals("2")) {
                baseViewHolder.setText(R.id.tv_fei_lv,"月费率:"+product.getMonthfeeRate()) ;

            } else {
                baseViewHolder.setText(R.id.tv_fei_lv,"日费率:"+product.getDayfeeRate()) ;
            }
            ImageView imageView = baseViewHolder.getView(R.id.iv_logo);
            Glide.with(mContext).load(product.getLogurl())
                    .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                    .into(imageView);

            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
    }
}
