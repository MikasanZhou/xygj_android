package com.xygj.app.jinrirong.adpter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.xygj.app.R;
import com.xygj.app.jinrirong.model.ProblemBean;

/**
 * 常见问题适配器
 * Created by Yangli on 2018/4/9.
 */

public class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ViewHolder> {
    private List<ProblemBean> beans;

    public ProblemListAdapter(List<ProblemBean> beans) {
        this.beans = beans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_problem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ProblemBean bean = beans.get(position);
        holder.tvTitle.setText(bean.title);
        holder.wvContent.loadDataWithBaseURL(null, bean.content, "text/html", "UTF-8", "");
        holder.llTitle.setSelected(false);
        holder.llTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.llTitle.setSelected(!holder.llTitle.isSelected());
                holder.wvContent.setVisibility(holder.llTitle.isSelected() ?
                        View.VISIBLE : View.GONE);
                holder.ivState.setImageResource(holder.llTitle.isSelected() ?
                        R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_title)
        LinearLayout llTitle;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_state)
        ImageView ivState;
        @BindView(R.id.wv_content)
        WebView wvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
