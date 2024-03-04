package com.example.bagetmast;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssortementFragment extends Fragment {
    private FirebaseFirestore db;
    private SearchView searchView;
    RecyclerView recyclerView;
    public AssortementFragment(){super(R.layout.assortement_fragment);}

    public static AssortementFragment newInstance(){
        return new AssortementFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assortement_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.assortement_list);

        db = FirebaseFirestore.getInstance();

        if (CurrentAssortement.cAssortements.isEmpty()){
            CurrentAssortement.cAssortements = Assortement.createAssList(db);
        }

        AssortementAdapter adapter = new AssortementAdapter(CurrentAssortement.cAssortements);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchView = (SearchView) view.findViewById(R.id.search_assortement);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });
        return view;
    }

    private void filterList(String text) {
        List<Assortement> filteredList = new ArrayList<>();
        for (Assortement assortement : CurrentAssortement.cAssortements){
            if (text.toLowerCase().isEmpty()){
                filteredList.clear();
            }
            else if (!text.toLowerCase().isEmpty()){
                if (assortement.getNameProduct().toLowerCase().contains(text.toLowerCase())){
                    filteredList.add(assortement);
                }
            }
        }
        if (filteredList.isEmpty()){
            AssortementAdapter adapter = new AssortementAdapter(CurrentAssortement.cAssortements);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            Toast.makeText(getContext(), "Товара с таким названием нет",
                    Toast.LENGTH_SHORT).show();
        }else {
            AssortementAdapter adapter = new AssortementAdapter(filteredList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }
}
