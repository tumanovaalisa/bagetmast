package com.example.bagetmast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAdapter extends
        RecyclerView.Adapter<AdminAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView business_TV;
        public TextView email_TV;
        public TextView phone_ET;
        public TextView order_TV;
        public TextView adress_TV;
        public TextView date_TV;
        public TextView time_ET;
        public TextView comment_TV;
        public TextView price_TV;


        public ViewHolder(View itemView) {
            super(itemView);

            business_TV = (TextView) itemView.findViewById(R.id.businessA_TV);
            order_TV = (TextView) itemView.findViewById(R.id.productA_TV);
            email_TV = (TextView) itemView.findViewById(R.id.emailA_TV);
            phone_ET = (TextView) itemView.findViewById(R.id.phoneA_TV);
            adress_TV = (TextView) itemView.findViewById(R.id.adressA_TV);
            date_TV = (TextView) itemView.findViewById(R.id.dateA_TV);
            time_ET = (TextView) itemView.findViewById(R.id.timeA_TV);
            comment_TV = (TextView) itemView.findViewById(R.id.commentsA_TV);
            price_TV = (TextView) itemView.findViewById(R.id.priceA_TV);
        }
    }

    private List<AdminInfo> mAdminInfo;

    public AdminAdapter(List<AdminInfo> adminInfos) {
        mAdminInfo = adminInfos;
    }

    @NonNull
    @Override
    public AdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.admin_item, parent, false);

        ViewHolder viewHolder = new AdminAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.ViewHolder holder, int position) {
        AdminInfo adminInfo = mAdminInfo.get(position);
        TextView textView1 = holder.business_TV;
        textView1.setText(adminInfo.getBusiness());
        TextView textView2 = holder.email_TV;
        textView2.setText(adminInfo.getEmail());
        TextView textView3 = holder.phone_ET;
        textView3.setText(adminInfo.getPhone());
        TextView textView4 = holder.order_TV;
        String order = "";
        for (Assortement assortement : CurrentAssortement.cAssortements){
            if (adminInfo.getOrder().containsKey(assortement.getIdProduct())){
                order += assortement.getNameProduct() + "     "
                        + adminInfo.getOrder().get(assortement.getIdProduct()) + " шт.\n";
            }
        }
        textView4.setText(order);
        TextView textView5 = holder.date_TV;
        textView5.setText("Дата:    " + adminInfo.getDate());
        TextView textView6 = holder.time_ET;
        textView6.setText("Часы работы:    " + adminInfo.getTime());
        TextView textView7 = holder.comment_TV;
        textView7.setText(adminInfo.getComment());
        TextView textView8 = holder.price_TV;
        textView8.setText(Double.toString(adminInfo.getPrice()) + " руб.");
        TextView textView9 = holder.adress_TV;
        textView9.setText("Адрес:    " + adminInfo.getAdress());

    }

    @Override
    public int getItemCount() {
        return mAdminInfo.size();
    }
}