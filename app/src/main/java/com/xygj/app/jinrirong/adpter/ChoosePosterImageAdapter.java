package com.xygj.app.jinrirong.adpter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.xygj.app.R;

public class ChoosePosterImageAdapter extends RecyclerView.Adapter<ChoosePosterImageAdapter.Holder> {

    private Context mContext;
    private String[] picUrls;
    private int mCurChosePosition = 0;
    private int mSelectedColor = Color.BLACK;


    private OnItemClickListener mOnItemClickListener;

    public ChoosePosterImageAdapter(Context mContext, String[] picUrls) {
        this.mContext = mContext;
        this.picUrls = picUrls;
        mSelectedColor = ContextCompat.getColor(mContext, R.color.colorPrimary);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_choose_poster_img, parent, false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        if (position == mCurChosePosition) {
            holder.itemView.setBackgroundColor(mSelectedColor);
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#dddddd"));
        }
        Glide.with(mContext).load(picUrls[position]).into(holder.ivPoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurChosePosition = holder.getAdapterPosition();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.getAdapterPosition());
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return picUrls.length;
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_poster)
        ImageView ivPoster;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}