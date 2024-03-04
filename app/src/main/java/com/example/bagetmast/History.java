package com.example.bagetmast;

import java.util.HashMap;
import java.util.Map;

public class History {
    private String idClient;
    private Map<String,Integer> order;
    private String business;
    private String adress;
    private String date;
    private String time;
    private String comment;
    private double price;

    public History() {
        this.idClient = "";
        this.order = new HashMap<>();
        this.business = "";
        this.adress = "";
        this.date = "";
        this.time = "";
        this.comment = "";
        this.price = 0;
    }

    public History( String idClient, Map<String,Integer> order,
                     String business, String adress, String date, String time, String comment, double price) {
        this.idClient = idClient;
        this.order = order;
        this.business = business;
        this.adress = adress;
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.price = price;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public Map<String,Integer> getOrder() {
        return order;
    }

    public void setOrder(Map<String,Integer> order) {
        this.order = order;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
