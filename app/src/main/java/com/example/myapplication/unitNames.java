package com.example.myapplication;

import java.util.ArrayList;

public class unitNames {
    public static ArrayList<unitNames> unitNamesArrayList;
    private int id;
    private String name;

    public unitNames(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void initUnitNames() {
        unitNamesArrayList = new ArrayList<>();

        unitNames CPU = new unitNames(0, "CPU");
        unitNamesArrayList.add(CPU);

        unitNames NNAPI = new unitNames(0, "TPU");
        unitNamesArrayList.add(NNAPI);
    }

    public static ArrayList<unitNames> getUnitNamesArrayList() {
        return unitNamesArrayList;
    }

    public static String[] units() {
        String[] names = new String[unitNamesArrayList.size()];
        for (int i = 0; i < unitNamesArrayList.size(); i++) {
            names[i] = unitNamesArrayList.get(i).name;
        }
        return names;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
