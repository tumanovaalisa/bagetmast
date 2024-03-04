package com.example.bagetmast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    ArrayList<AdminInfo> adminInfos = new ArrayList<>();
    Map<String, List<String>> usersInfo = new HashMap<>();
    RecyclerView recyclerView;
    List<String> keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        recyclerView = (RecyclerView) findViewById(R.id.admin_RV);

        db = FirebaseFirestore.getInstance();
        CurrentAssortement.cAssortements = Assortement.createAssList(db);
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List<String> info = new ArrayList<>();
                                String keyEmail = "";
                                info.add(document.getId().toString());
                                for(Map.Entry<String,Object> docs : document.getData().entrySet()){
                                    if (docs.getKey().equals("Email")) keyEmail = docs.getValue().toString();
                                    info.add(docs.getValue().toString());
                                }
                                usersInfo.put(keyEmail, info);
                            }
                            keys = new ArrayList<String>(usersInfo.keySet());
                            getOrders();
                        }
                    }
                });

    }

    public void getOrders() {
        db.collection("Orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AdminInfo adminInfo = new AdminInfo();
                                for (int i = 0; i < keys.size(); i ++){
                                    String key = keys.get(i);
                                    List<String> user = usersInfo.get(key);
                                    for(Map.Entry<String,Object> docs : document.getData().entrySet()){
                                        if (docs.getKey().equals("IdC") && docs.getValue().toString().equals(user.get(0))){
                                            adminInfo.setIdClient(user.get(0));
                                            for (Map.Entry<String,Object> docs1 : document.getData().entrySet()){
                                                if (docs1.getKey().equals("Business")) adminInfo.setBusiness(docs1.getValue().toString());
                                                if (docs1.getKey().equals("Price")) adminInfo.setPrice(Double.parseDouble(docs1.getValue().toString()));
                                                if (docs1.getKey().equals("Comments")) adminInfo.setComment(docs1.getValue().toString());
                                                if (docs1.getKey().equals("Date")) adminInfo.setDate(docs1.getValue().toString());
                                                if (docs1.getKey().equals("Time")) adminInfo.setTime(docs1.getValue().toString());
                                                if (docs1.getKey().equals("Adress")) adminInfo.setAdress(docs1.getValue().toString());
                                                if (docs1.getKey().equals("Products")) {
                                                    Map<String, Integer> map = (Map<String, Integer>) docs1.getValue();
                                                    adminInfo.setOrder(map);
                                                }
                                            }
                                            adminInfo.setEmail(user.get(4));
                                            adminInfo.setPhone(user.get(5));
                                            adminInfos.add(adminInfo);
                                        }
                                    }
                                }
                            }

                            AdminAdapter adapter = new AdminAdapter(adminInfos);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        }
                    }
                });
    }

    public void exitBtn(View view) {
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent);
    }
}