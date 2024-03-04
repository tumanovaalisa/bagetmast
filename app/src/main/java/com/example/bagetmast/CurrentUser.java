package com.example.bagetmast;

import java.util.HashMap;
import java.util.Map;

public class CurrentUser {
    static String id;
    static String email;
    static String name;
    static String lastname;
    static String patronymic;
    static String phone;
    static String company;
    static int sale;
    static String role;
    static Map<Assortement, Integer> order;


    public static void initialization() {
        id = "";
        email = "";
        name = "";
        lastname = "";
        patronymic = "";
        phone = "";
        company = "";
        sale = 0;
        role = "";
        order = new HashMap<Assortement, Integer>();
    }
    public static void getUser(String e, String ln, String n, String pt,
                               String p, String c, String r, String i, int s) {
        email = e;
        lastname = ln;
        name = n;
        patronymic = pt;
        phone = p;
        company = c;
        role = r;
        id = i;
        sale = s;
    }
}
