package com.example.bagetmast;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BasketFragment extends Fragment {
    ArrayList<Assortement> basket = new ArrayList<>();
    public BasketFragment(){super(R.layout.basket_fragment);}

    public static BasketFragment newInstance(){
        return new BasketFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.basket_fragment, container, false);
        Button getOrder = view.findViewById(R.id.getOrder_Btn);
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.basket_list);

        for (Assortement assortement : CurrentAssortement.cAssortements){
            if (CurrentUser.order.containsKey(assortement)) basket.add(assortement);
        }

        BasketAdapter adapter = new BasketAdapter(basket);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        List<Assortement> keys = new ArrayList<Assortement>(CurrentUser.order.keySet());
        double price = 0;
        for (int i = 0; i < keys.size(); i ++) {
            Assortement key = keys.get(i);
            int n = CurrentUser.order.get(key);
            price += key.getCostProduct() * n;
        }

        double finalPrice = (price * (100 - CurrentUser.sale))/100;
        TextView textView = view.findViewById(R.id.price_TV);
        textView.setText("Сумма заказа: " + Double.toString(finalPrice) + " руб.");
        getOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalPrice >= 2000){
                    intent.putExtra("finalCost", Double.toString(finalPrice));
                    startActivity(intent);
                }
                else Toast.makeText(getContext(), "Минимальная сумма заказа 2000 руб.",
                        Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
