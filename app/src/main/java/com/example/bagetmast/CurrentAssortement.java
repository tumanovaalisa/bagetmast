package com.example.bagetmast;

import java.util.ArrayList;

public class CurrentAssortement {
    static ArrayList<Assortement> cAssortements;

    public static ArrayList<Assortement> getcAssortements() {
        return cAssortements;
    }

    public static void setcAssortements(ArrayList<Assortement> cAssortements) {
        CurrentAssortement.cAssortements = cAssortements;
    }

    public static void initialization() {
        cAssortements = new ArrayList<>();
    }
}
