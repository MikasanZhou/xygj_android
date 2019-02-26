package com.xygj.app.jinrirong.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.xygj.app.R;
import com.xygj.app.jinrirong.activity.product.CreditCardDetailActivity;
import com.xygj.app.jinrirong.model.CreditCard;

public class CreditCardListAdapter extends RecyclerView.Adapter<CreditCardListAdapter.Holder> {

    private Context mContext;
    private List<CreditCard> mCreditCards;

    public CreditCardListAdapter(Context context, List<CreditCard> creditCards) {
        mContext = context;
        mCreditCards = creditCards;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_bank_card2, parent, false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final CreditCard card = mCreditCards.get(position);
        Glide.with(mContext).load(card.getLogurl()).into(holder.cover);
        holder.title.setText(card.getName());
        holder.bank.setText(card.getBankName());
        holder.people.setText(card.getAppNumbs());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(CreditCardDetailActivity.getIntent(mContext, card.getID()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCreditCards.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView title;
        TextView bank;
        TextView people;

        public Holder(View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.iv_cover);
            title = itemView.findViewById(R.id.tv_title);
            bank = itemView.findViewById(R.id.tv_bank);
            people = itemView.findViewById(R.id.tv_people);
        }
    }
}
