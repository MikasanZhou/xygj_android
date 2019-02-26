package com.xygj.app.jinrirong.adpter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.xygj.app.R;

public class SingleChooseAdapter extends RecyclerView.Adapter<SingleChooseAdapter.Holder> {
    private List<String> mStringList;
    private Context mContext;
    private int mDefaultColor = Color.GRAY;
    private int mChoseColor = Color.BLUE;
    private int mCurChosePosition = -1;

    private OnItemClickListener mOnItemClickListener;

    public SingleChooseAdapter(List<String> stringList, Context context) {
        mStringList = stringList;
        mContext = context;
    }

    public SingleChooseAdapter(List<String> stringList, Context context, int defaultColor, int choseColor) {
        mStringList = stringList;
        mContext = context;
        mDefaultColor = defaultColor;
        mChoseColor = choseColor;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_single_choose, parent, false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        if (position > -1) {
            if (position == mCurChosePosition) {
                holder.mTvTitle.setTextColor(mChoseColor);
                holder.mTvTitle.setBackgroundResource(R.drawable.shape_bg_single_selected);
            } else {
                holder.mTvTitle.setTextColor(mDefaultColor);
                holder.mTvTitle.setBackgroundResource(R.drawable.shape_bg_single_select);
            }
        }
        holder.mTvTitle.setText(mStringList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurChosePosition = holder.getAdapterPosition();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView mTvTitle;

        Holder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}