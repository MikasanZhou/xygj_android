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
import com.xygj.app.jinrirong.model.IHaveInfo;

public class SingleChooseAdapter2 extends RecyclerView.Adapter<SingleChooseAdapter2.Holder> {

    private List<IHaveInfo> mStringList;
    private Context mContext;
    private int mDefaultColor = Color.GRAY;
    private int mChoseColor = Color.BLUE;
    private int mCurChosePosition = -1;

    private OnItemClickListener mOnItemClickListener;

    public SingleChooseAdapter2(List<IHaveInfo> stringList, Context context) {
        mStringList = stringList;
        mContext = context;
        mChoseColor = context.getResources().getColor(R.color.colorPrimary);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public int getCurChosePosition() {
        return mCurChosePosition;
    }


    public void setmCurChosePosition(int mCurChosePosition) {
        this.mCurChosePosition = mCurChosePosition;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_single_choose2, parent, false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        if (position > -1) {
            if (mStringList.get(position).getID() == mCurChosePosition) {
                holder.mTvTitle.setTextColor(mChoseColor);
                holder.mTvTitle.setBackgroundResource(R.drawable.bg_single_choose_item_selected);
            } else {
                holder.mTvTitle.setTextColor(mDefaultColor);
                holder.mTvTitle.setBackgroundResource(R.drawable.bg_single_choose_item);
            }

        }
        holder.mTvTitle.setText(mStringList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurChosePosition = mStringList.get(position).getID();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
                notifyDataSetChanged();
            }
        });
    }

    public void resetChoose() {
        mCurChosePosition = 0;
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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}