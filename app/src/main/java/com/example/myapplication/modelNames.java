package com.example.myapplication;

import java.util.ArrayList;

public class modelNames {
    public static ArrayList<modelNames> modelNamesArrayList;
    private int id;
    private String name;

    public modelNames(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void initModelNames() {
        modelNamesArrayList = new ArrayList<>();

        // efficientnet
        modelNames efficientnet_b0 = new modelNames(0, "efficientnet_b0");
        modelNamesArrayList.add(efficientnet_b0);
        modelNames efficientnet_b1 = new modelNames(0, "efficientnet_b1");
        modelNamesArrayList.add(efficientnet_b1);
        modelNames efficientnet_b2 = new modelNames(0, "efficientnet_b2");
        modelNamesArrayList.add(efficientnet_b2);
        modelNames efficientnet_b3 = new modelNames(0, "efficientnet_b3");
        modelNamesArrayList.add(efficientnet_b3);
        modelNames efficientnet_b4 = new modelNames(0, "efficientnet_b4");
        modelNamesArrayList.add(efficientnet_b4);
        modelNames efficientnet_b5 = new modelNames(0, "efficientnet_b5");
        modelNamesArrayList.add(efficientnet_b5);
        modelNames efficientnet_b6 = new modelNames(0, "efficientnet_b6");
        modelNamesArrayList.add(efficientnet_b6);
        modelNames efficientnet_b7 = new modelNames(0, "efficientnet_b7");
        modelNamesArrayList.add(efficientnet_b7);
        modelNames efficientnet_v2_s = new modelNames(0, "efficientnet_v2_s");
        modelNamesArrayList.add(efficientnet_v2_s);
        modelNames efficientnet_v2_m = new modelNames(0, "efficientnet_v2_m");
        modelNamesArrayList.add(efficientnet_v2_m);
        modelNames efficientnet_v2_l = new modelNames(0, "efficientnet_v2_l");
        modelNamesArrayList.add(efficientnet_v2_l);

        // MobileOne
        modelNames MobileOne_s0 = new modelNames(0, "MobileOne_s0");
        modelNamesArrayList.add(MobileOne_s0);
        modelNames MobileOne_s1 = new modelNames(0, "MobileOne_s1");
        modelNamesArrayList.add(MobileOne_s1);
        modelNames MobileOne_s2 = new modelNames(0, "MobileOne_s2");
        modelNamesArrayList.add(MobileOne_s2);
        modelNames MobileOne_s3 = new modelNames(0, "MobileOne_s3");
        modelNamesArrayList.add(MobileOne_s3);
        modelNames MobileOne_s4 = new modelNames(0, "MobileOne_s4");
        modelNamesArrayList.add(MobileOne_s4);

        // mobilenet
        modelNames mobilenet_v1 = new modelNames(0, "mobilenet_v1");
        modelNamesArrayList.add(mobilenet_v1);
        modelNames mobilenet_v2 = new modelNames(0, "mobilenet_v2");
        modelNamesArrayList.add(mobilenet_v2);
        modelNames mobilenet_v3_small = new modelNames(0, "mobilenet_v3_small");
        modelNamesArrayList.add(mobilenet_v3_small);
        modelNames mobilenet_v3_large = new modelNames(0, "mobilenet_v3_large");
        modelNamesArrayList.add(mobilenet_v3_large);


        // efficient_former models
        modelNames efficientformer_l1 = new modelNames(0, "efficientformer_l1");
        modelNamesArrayList.add(efficientformer_l1);
        modelNames efficientformer_l3 = new modelNames(0, "efficientformer_l3");
        modelNamesArrayList.add(efficientformer_l3);
        modelNames efficientformer_l7 = new modelNames(0, "efficientformer_l7");
        modelNamesArrayList.add(efficientformer_l7);
        modelNames efficientformerv2_s0 = new modelNames(0, "efficientformerv2_s0");
        modelNamesArrayList.add(efficientformerv2_s0);
        modelNames efficientformerv2_s1 = new modelNames(0, "efficientformerv2_s1");
        modelNamesArrayList.add(efficientformerv2_s1);
        modelNames efficientformerv2_s2 = new modelNames(0, "efficientformerv2_s2");
        modelNamesArrayList.add(efficientformerv2_s2);
        modelNames efficientformerv2_l = new modelNames(0, "efficientformerv2_l");
        modelNamesArrayList.add(efficientformerv2_l);

        // nextvit
        modelNames nextvit_small = new modelNames(0, "nextvit_small_in1k_224");
        modelNamesArrayList.add(nextvit_small);
        modelNames nextvit_base = new modelNames(0, "nextvit_base_in1k_224");
        modelNamesArrayList.add(nextvit_base);
        modelNames nextvit_large = new modelNames(0, "nextvit_large_in1k_224");
        modelNamesArrayList.add(nextvit_large);

        // mobilevit
        modelNames mobilevit_v1_s = new modelNames(0, "mobilevit_v1_s");
        modelNamesArrayList.add(mobilevit_v1_s);
        modelNames mobilevit_v1_xs = new modelNames(0, "mobilevit_v1_xs");
        modelNamesArrayList.add(mobilevit_v1_xs);
        modelNames mobilevit_v1_xxs = new modelNames(0, "mobilevit_v1_xxs");
        modelNamesArrayList.add(mobilevit_v1_xxs);
        modelNames mobilevit_v2_050 = new modelNames(0, "mobilevit_v2_050");
        modelNamesArrayList.add(mobilevit_v2_050);
        modelNames mobilevit_v2_100 = new modelNames(0, "mobilevit_v2_100");
        modelNamesArrayList.add(mobilevit_v2_100);
        modelNames mobilevit_v2_150 = new modelNames(0, "mobilevit_v2_150");
        modelNamesArrayList.add(mobilevit_v2_150);
    }

    public static ArrayList<modelNames> getModelNamesArrayList() {
        return modelNamesArrayList;
    }

    public static String[] models() {
        String[] names = new String[modelNamesArrayList.size()];
        for (int i = 0; i < modelNamesArrayList.size(); i++) {
            names[i] = modelNamesArrayList.get(i).name;
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
