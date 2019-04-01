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
        if (adapterPosition == 0) {
            roundImageView.setBackgroundResource(R.drawable.bg_spread_first);
        } else if (adapterPosition == 1) {
            roundImageView.setBackgroundResource(R.drawable.bg_spread_second);
        } else {
            roundImageView.setBackgroundResource(R.drawable.bg_spread_thrid);
        }
    }
}
