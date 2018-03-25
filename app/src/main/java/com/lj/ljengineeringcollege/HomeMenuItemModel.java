package com.lj.ljengineeringcollege;

/**
 * Created by anupamchugh on 11/02/17.
 */

public class HomeMenuItemModel {


    public String text;
    public int drawable;
    public String color;

    public HomeMenuItemModel(String t, int d, String c) {
        text = t;
        drawable = d;
        color = c;
    }

    public HomeMenuItemModel(String t, int d) {
        text = t;
        drawable = d;

    }
}
