package com.example.bagetmast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity  extends AppCompatActivity {


    private FirebaseFirestore db;
    private TextView priceView;
    private EditText dateView;
    private EditText timeView;
    private EditText businessView;
    private EditText adressView;
    private EditText commentView;
    double price;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        db = FirebaseFirestore.getInstance();

        price = Double.parseDouble(getIntent().getStringExtra("finalCost"));
        priceView = (TextView) findViewById(R.id.order_TV);
        dateView = (EditText) findViewById(R.id.date_ET);
        timeView = (EditText) findViewById(R.id.time_ET);
        adressView = (EditText) findViewById(R.id.adress_ET);
        businessView = (EditText) findViewById(R.id.business_ET);
        commentView = (EditText) findViewById(R.id.comments_ET);
        priceView.setText("   К оплате будет: " + Double.toString(price) + " руб.");
    }

    public void getOrder(View view) {
        if (!dateView.getText().toString().equals("") && !timeView.getText().toString().equals("") &&
                !businessView.getText().toString().equals("") && !adressView.getText().toString().equals("")) {
            Toast.makeText(this, "Заявка оформлена!",
                    Toast.LENGTH_SHORT).show();

            Map<String, Object> order = new HashMap<>();

            order.put("Adress", adressView.getText().toString());
            order.put("Business", businessView.getText().toString());
            order.put("Comments", commentView.getText().toString());
            order.put("Date", dateView.getText().toString());
            order.put("Time", timeView.getText().toString());
            order.put("IdC", CurrentUser.id);
            order.put("Price", Double.toString(price));
            Map<String, Integer> map = new HashMap<>();
            List<Assortement> keys = new ArrayList<Assortement>(CurrentUser.order.keySet());
            for (int i = 0; i < keys.size(); i ++) {
                Assortement key = keys.get(i);
                int n = CurrentUser.order.get(key);
                map.put(key.getIdProduct(), n);
            }
            order.put("Products", map);

            db.collection("Orders")
                    .add(order)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
            CurrentUser.order.clear();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Введите данные",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}