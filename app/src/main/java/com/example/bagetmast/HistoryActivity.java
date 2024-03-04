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
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    ArrayList<History> histories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.history_list);

        db = FirebaseFirestore.getInstance();
        db.collection("Orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                History history = new History();
                                for(Map.Entry<String,Object> docs : document.getData().entrySet()){
                                    if (docs.getKey().equals("IdC") && docs.getValue().toString().equals(CurrentUser.id)){
                                        history.setIdClient(CurrentUser.id);
                                        for (Map.Entry<String,Object> docs1 : document.getData().entrySet()){
                                            if (docs1.getKey().equals("Business")) history.setBusiness(docs1.getValue().toString());
                                            if (docs1.getKey().equals("Price")) history.setPrice(Double.parseDouble(docs1.getValue().toString()));
                                            if (docs1.getKey().equals("Comments")) history.setComment(docs1.getValue().toString());
                                            if (docs1.getKey().equals("Date")) history.setDate(docs1.getValue().toString());
                                            if (docs1.getKey().equals("Time")) history.setTime(docs1.getValue().toString());
                                            if (docs1.getKey().equals("Adress")) history.setAdress(docs1.getValue().toString());
                                            if (docs1.getKey().equals("Products")) {
                                                Map<String, Integer> map = (Map<String, Integer>) docs1.getValue();
                                                history.setOrder(map);
                                            }
                                        }
                                        histories.add(history);
                                    }
                                }
                            }

                            HistoryAdapter adapter = new HistoryAdapter(histories);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        }
                    }
                });
    }

    public void backBtn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}