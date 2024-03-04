package com.example.bagetmast;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BasketAdapter extends
        RecyclerView.Adapter<BasketAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name_TV;
        public TextView weight_TV;
        public TextView cost_TV;
        public TextView n_ET;
        public ImageView img_IV;


        public ViewHolder(View itemView) {
            super(itemView);

            name_TV = (TextView) itemView.findViewById(R.id.name1_TV);
            weight_TV = (TextView) itemView.findViewById(R.id.weight1_TV);
            n_ET = (TextView) itemView.findViewById(R.id.nB_ET);
            cost_TV = (TextView) itemView.findViewById(R.id.cost1_TV);
            img_IV = (ImageView) itemView.findViewById(R.id.img1_IV);
        }
    }

    private List<Assortement> mAssortement;

    public BasketAdapter(List<Assortement> assortements) {
        mAssortement = assortements;
    }

    @NonNull
    @Override
    public BasketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.basket_item, parent, false);

        ViewHolder viewHolder = new BasketAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.ViewHolder holder, int position) {
        Assortement assortement = mAssortement.get(position);
        TextView textView1 = holder.name_TV;
        textView1.setText(assortement.getNameProduct());
        TextView textView2 = holder.weight_TV;
        textView2.setText(Integer.toString(assortement.getWeightProduct()) + " г");
        TextView textView3 = holder.cost_TV;
        textView3.setText(Double.toString(assortement.getCostProduct()) + " руб.");
        ImageView imageView = holder.img_IV;
        Picasso.get()
                .load(assortement.getImageProduct())
                .resize(450, 300)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);

        TextView textView = holder.n_ET;
        if (CurrentUser.order.containsKey(assortement)){
            textView.setText(CurrentUser.order.get(assortement).toString());
        }
    }

    @Override
    public int getItemCount() {
        return mAssortement.size();
    }
}