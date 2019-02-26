package com.xygj.app.jinrirong.adpter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.xygj.app.R;

/**
 * 购买代理 ：代理等级 adapter
 */
public class ChooseProxyLevelAdapter extends RecyclerView.Adapter<ChooseProxyLevelAdapter.Holder> {

        private List<String> mStringList;
        private Context mContext;
        private int mDefaultColor = Color.GRAY;
        private int mChoseColor = Color.BLUE;
        private int mCurChosePosition = 0;

        private OnItemClickListener mOnItemClickListener;

        private ChooseProxyLevelAdapter(List<String> stringList, Context context) {
            mStringList = stringList;
            mContext = context;
        }

        public ChooseProxyLevelAdapter(List<String> stringList, Context context, int defaultColor, int choseColor) {
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
        public ChooseProxyLevelAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(mContext).inflate(R.layout.item_proxy_level, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull final Holder holder, final int position) {
            if (position == mCurChosePosition) {
                holder.mTvTitle.setTextColor(mChoseColor);
                holder.mIvStatus.setImageResource(R.drawable.ic_radio_button_checked);
            } else {
                holder.mTvTitle.setTextColor(mDefaultColor);
                holder.mIvStatus.setImageResource(R.drawable.ic_radio_button_normal);
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
            ImageView mIvStatus;

            Holder(View itemView) {
                super(itemView);
                mTvTitle = itemView.findViewById(R.id.tv_title);
                mIvStatus = itemView.findViewById(R.id.iv_status);
            }
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }
    }