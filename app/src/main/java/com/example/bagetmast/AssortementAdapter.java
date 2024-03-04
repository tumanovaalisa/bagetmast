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

public class AssortementAdapter extends
        RecyclerView.Adapter<AssortementAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name_TV;
        public TextView weight_TV;
        public TextView cost_TV;
        public TextView n_ET;
        public ImageView img_IV;
        public Button plus_Btn;
        public Button minus_Btn;
        public Button about_Btn;


        public ViewHolder(View itemView) {
            super(itemView);

            name_TV = (TextView) itemView.findViewById(R.id.name_TV);
            weight_TV = (TextView) itemView.findViewById(R.id.weight_TV);
            cost_TV = (TextView) itemView.findViewById(R.id.cost_TV);
            img_IV = (ImageView) itemView.findViewById(R.id.img_IV);
            n_ET = (TextView) itemView.findViewById(R.id.n_ET);
            plus_Btn = (Button) itemView.findViewById(R.id.plus_Btn);
            minus_Btn = (Button) itemView.findViewById(R.id.minus_Btn);
            about_Btn = (Button) itemView.findViewById(R.id.about_Btn);
        }
    }

    private List<Assortement> mAssortement;

    public AssortementAdapter(List<Assortement> assortements) {
        mAssortement = assortements;
    }

    @NonNull
    @Override
    public AssortementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.assortement_item, parent, false);

        ViewHolder viewHolder = new AssortementAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        else textView.setText("0");

        holder.plus_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(textView.getText().toString());
                n++;
                CurrentUser.order.put(assortement, n);
                textView.setText(Integer.toString(n));
            }
        });

        holder.minus_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(textView.getText().toString());
                if (n > 1) n--;
                else if (n == 1){
                    CurrentUser.order.remove(assortement);
                    n--;
                }
                textView.setText(Integer.toString(n));
            }
        });

        holder.about_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AssortementItemActivity.class);
                intent.putExtra("selectedName", assortement.getNameProduct());
                intent.putExtra("selectedCost", Double.toString(assortement.getCostProduct()));
                intent.putExtra("selectedCount", Integer.toString(assortement.getCountProduct()));
                intent.putExtra("selectedWeight", Integer.toString(assortement.getWeightProduct()));
                intent.putExtra("selectedDescription", assortement.getDescriptionProduct());
                intent.putExtra("selectedImage", assortement.getImageProduct());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAssortement.size();
    }
}