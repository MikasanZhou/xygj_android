package com.xygj.app.jinrirong.adpter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.xygj.app.R;

public class MultiChooseAdapter extends RecyclerView.Adapter<MultiChooseAdapter.Holder> {

    private List<String> mStringList;
    private Context mContext;
    private int mDefaultColor = Color.GRAY;
    private int mChoseColor = Color.BLUE;
    private int mCurChosePosition = 0;

    private List<Boolean> mBooleanList;

    public MultiChooseAdapter(List<String> stringList, Context context) {
        mStringList = stringList;
        mContext = context;
        mBooleanList = new ArrayList<>();
        for (int i = 0; i < mStringList.size(); i++) {
            mBooleanList.add(false);
        }
    }

    public List<Integer> getChosePositionList() {
        List<Integer> positionList = new ArrayList<>();
        for (int i = 0; i < mBooleanList.size(); i++) {
            if (mBooleanList.get(i)) {
                positionList.add(i);
            }
        }
        return positionList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_single_choose2, parent, false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        boolean isSelected = mBooleanList.get(position);
        if (isSelected) {
            holder.mTvTitle.setTextColor(mChoseColor);
            holder.mTvTitle.setBackgroundResource(R.drawable.bg_single_choose_item_selected);
        } else {
            holder.mTvTitle.setTextColor(mDefaultColor);
            holder.mTvTitle.setBackgroundResource(R.drawable.bg_single_choose_item);
        }
        holder.mTvTitle.setText(mStringList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurChosePosition = holder.getAdapterPosition();
                mBooleanList.set(holder.getAdapterPosition(), !mBooleanList.get(holder.getAdapterPosition()));
                notifyDataSetChanged();
            }
        });
    }

    public void resetChoose() {
        for (int i = 0; i < mBooleanList.size(); i++) {
            mBooleanList.set(i, false);
        }
        notifyDataSetChanged();
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
}