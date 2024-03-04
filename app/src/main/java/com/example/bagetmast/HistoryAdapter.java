package com.example.bagetmast;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter  extends
        RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView business_TV;
        public TextView order_TV;
        public TextView adress_TV;
        public TextView date_TV;
        public TextView time_ET;
        public TextView comment_TV;
        public TextView price_TV;


        public ViewHolder(View itemView) {
            super(itemView);

            business_TV = (TextView) itemView.findViewById(R.id.businessH_TV);
            order_TV = (TextView) itemView.findViewById(R.id.productH_TV);
            adress_TV = (TextView) itemView.findViewById(R.id.adressH_TV);
            date_TV = (TextView) itemView.findViewById(R.id.dateH_TV);
            time_ET = (TextView) itemView.findViewById(R.id.timeH_TV);
            comment_TV = (TextView) itemView.findViewById(R.id.commentsH_TV);
            price_TV = (TextView) itemView.findViewById(R.id.priceH_TV);
        }
    }

    private List<History> mHistories;

    public HistoryAdapter(List<History> histories) {
        mHistories = histories;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.history_item, parent, false);

        ViewHolder viewHolder = new HistoryAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        History history = mHistories.get(position);
        TextView textView1 = holder.business_TV;
        textView1.setText(history.getBusiness());
        TextView textView2 = holder.adress_TV;
        textView2.setText(history.getAdress());
        TextView textView4 = holder.order_TV;
        String order = "";
        for (Assortement assortement : CurrentAssortement.cAssortements){
            if (history.getOrder().containsKey(assortement.getIdProduct())){
                order += assortement.getNameProduct() + "     "
                        + history.getOrder().get(assortement.getIdProduct()) + " шт.\n";
            }
        }
        textView4.setText(order);
        TextView textView5 = holder.date_TV;
        textView5.setText("Дата:    " + history.getDate());
        TextView textView6 = holder.time_ET;
        textView6.setText("Часы работы:    " + history.getTime());
        TextView textView7 = holder.comment_TV;
        textView7.setText(history.getComment());
        TextView textView8 = holder.price_TV;
        textView8.setText(Double.toString(history.getPrice()) + " руб.");

    }

    @Override
    public int getItemCount() {
        return mHistories.size();
    }
}