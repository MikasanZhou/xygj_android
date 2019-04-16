package com.xygj.app.jinrirong.fragment.adapter;

import android.content.Context;
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
import com.xygj.app.jinrirong.utils.CommonUtils;

import java.util.List;

import cn.finalteam.rxgalleryfinal.imageloader.PicassoImageLoader;

/**
 * Created by Administrator on 2019/3/30 0030.
 */

public class SelectedExplosionAdapter extends BaseQuickAdapter<LoanProduct,BaseViewHolder> {
    private Context context;
    public SelectedExplosionAdapter(Context context,int layoutResId, @Nullable List<LoanProduct> data) {
        super(layoutResId, data);
        this.context =context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final LoanProduct loanProduct) {
        if(loanProduct!=null){
            //贷款名字
            baseViewHolder.setText(R.id.tv_title_name,loanProduct.getName());
            String typeName = loanProduct.getTypeName();
            if (typeName.contains("-")) {
                String[] split = typeName.split("-");
                String amount = CommonUtils.addComma(split[1]);
                baseViewHolder.setText(R.id.tv_balance,amount+"元");
            }else {
                String amount = CommonUtils.addComma("10000");
                baseViewHolder.setText(R.id.tv_balance,amount+"元");
            }
            baseViewHolder.setText(R.id.tv_desc,loanProduct.getIntro());
           ImageView image_lv = baseViewHolder.getView(R.id.iv_logo);
            Glide.with(context).load(loanProduct.getLogurl()).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                    .into(image_lv);
//            baseViewHolder.setText(R.id.tv_balance,loanProduct.get());

            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (UserManager.getInstance().isLogin()) {
                        mContext.startActivity(ProductDetailActivity.getIntent(mContext, loanProduct.getID()));
                    } else {
                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    }
                }
            });
        }
    }
}
