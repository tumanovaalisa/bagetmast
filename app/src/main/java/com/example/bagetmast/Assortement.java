package com.example.bagetmast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class Assortement {

    private String idProduct;
    private String nameProduct;
    private String descriptionProduct;
    private int countProduct;
    private int weightProduct;
    private double costProduct;

    private String imageProduct;

    public Assortement() {
        this.idProduct = "";
        this.nameProduct = "";
        this.descriptionProduct = "";
        this.countProduct = 0;
        this.weightProduct = 0;
        this.costProduct = 0;
        this.imageProduct = "";
    }

    public Assortement(String idProduct, String nameProduct, String descriptionProduct,
                       int countProduct, int weightProduct, double costProduct, String imageProduct) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.descriptionProduct = descriptionProduct;
        this.countProduct = countProduct;
        this.weightProduct = weightProduct;
        this.costProduct = costProduct;
        this.imageProduct = imageProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public int getCountProduct() {
        return countProduct;
    }

    public void setCountProduct(int countProduct) {
        this.countProduct = countProduct;
    }

    public int getWeightProduct() {
        return weightProduct;
    }

    public void setWeightProduct(int weightProduct) {
        this.weightProduct = weightProduct;
    }

    public double getCostProduct() {
        return costProduct;
    }

    public void setCostProduct(double costProduct) {
        this.costProduct = costProduct;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }
    public String getIdProduct() { return idProduct; }

    public void setIdProduct(String idProduct) { this.idProduct = idProduct; }

    public static ArrayList<Assortement> createAssList(FirebaseFirestore fb){
        ArrayList<Assortement> fassortements = new ArrayList<>();
        fb.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Assortement assortement = new Assortement();
                                String id = document.getId().toString();
                                assortement.setIdProduct(id);
                                for(Map.Entry<String,Object> docs : document.getData().entrySet()){
                                    if (docs.getKey().equals("Name")) assortement.setNameProduct(docs.getValue().toString());
                                    if (docs.getKey().equals("Cost")) assortement.setCostProduct(Double.parseDouble(docs.getValue().toString()));
                                    if (docs.getKey().equals("Count")) assortement.setCountProduct(Integer.parseInt(docs.getValue().toString()));
                                    if (docs.getKey().equals("Weight")) assortement.setWeightProduct(Integer.parseInt(docs.getValue().toString()));
                                    if (docs.getKey().equals("Image")) assortement.setImageProduct(docs.getValue().toString());
                                    if (docs.getKey().equals("Description")) assortement.setDescriptionProduct(docs.getValue().toString());
                                }
                                fassortements.add(assortement);
                            }
                        }
                    }
                });
        return fassortements;
    }
}
