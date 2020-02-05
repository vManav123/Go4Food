package com.example.go4food.model;

import java.util.ArrayList;

public class Restaurants_Model {
    private String address;
    private String city;
    private String shop_name;
    private String rating;
    private String image;
  private ArrayList<String> cuisines;

    public String getAddress() {
        return address;
    }

    private  ArrayList<String> menu;
    public Restaurants_Model() {
    }

    public Restaurants_Model(String address, String city, String shop_name, String rating, ArrayList<String> cuisines, String ImageUrl, ArrayList<String> menu) {
        this.address = address;
        this.city = city;
        this.shop_name = shop_name;
        this.rating = rating;
        this.cuisines = cuisines;
        this.image=ImageUrl;
        this.menu = menu;
    }

    public String getCity() {
        return city;
    }
    public String getShop_name() {
        return shop_name;
    }
    public String getrating() {
        return rating;
    }
    public String getimage() { return image; }
    public ArrayList<String> getCuisines() { return cuisines; }

    public ArrayList<String> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<String> menu) {
        this.menu = menu;
    }

//    public ArrayList<String> getCuisines() {
//        return cuisines;
//    }
}
