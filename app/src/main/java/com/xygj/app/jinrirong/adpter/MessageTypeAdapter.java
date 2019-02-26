package com.xygj.app.jinrirong.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.xygj.app.R;
import com.xygj.app.jinrirong.model.MessageBean;

/**
 * 消息类型列表适配器
 * Created by Yangli on 2018/3/20.
 */

public class MessageTypeAdapter extends RecyclerView.Adapter<MessageTypeAdapter.ViewHolder> {
    private List<MessageBean> beanList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public MessageTypeAdapter(List<MessageBean> beanList) {
        this.beanList = beanList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_message_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final MessageBean bean = beanList.get(position);
        Glide.with(mContext).load(bean.id).into(holder.ivMessageType);
        holder.tvMessageType.setText(bean.title);
        holder.tvMessageContent.setText(bean.contents);
        holder.tvMessageTime.setText(bean.time);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_message_type)
        ImageView ivMessageType;
        @BindView(R.id.tv_message_type)
        TextView tvMessageType;
        @BindView(R.id.tv_message_content)
        TextView tvMessageContent;
        @BindView(R.id.tv_message_time)
        TextView tvMessageTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
