package com.xygj.app.jinrirong.fragment.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xygj.app.R;
import com.xygj.app.common.widget.RoundImageView;
import com.xygj.app.jinrirong.model.HomeBanner;

import java.util.List;

/**
 * Created by Administrator on 2019/3/30 0030.
 */

public class SpreadAdapter extends BaseQuickAdapter<HomeBanner, BaseViewHolder> {
    private Context context;

    public SpreadAdapter(Context context, int layoutResId, @Nullable List<HomeBanner> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeBanner homeBanner) {
        int adapterPosition = baseViewHolder.getAdapterPosition();
        RelativeLayout roundImageView = baseViewHolder.getView(R.id.rl_spread);
        baseViewHolder.setText(R.id.tv_small, homeBanner.getName());
        if (adapterPosition == 0) {
            roundImageView.setBackgroundResource(R.drawable.bg_spread_first);
            baseViewHolder.setText(R.id.tv_small_num, "1000-3000");
        } else if (adapterPosition == 1) {
            baseViewHolder.setText(R.id.tv_small_num, "3000-10000");
            roundImageView.setBackgroundResource(R.drawable.bg_spread_second);
        } else {
            baseViewHolder.setText(R.id.tv_small_num, "10000-50000");
            roundImageView.setBackgroundResource(R.drawable.bg_spread_thrid);
        }
    }
}
