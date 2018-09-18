package com.example.zunnorain.telcard_khokha.Classes;

/**
 * Created by Zunnorain on 03/06/2018.
 */

public class Item {
    private int id;
    private int comp_logo;
    private String comp_name;
    private int qty;
    private String cat;
    private double price;

    public Item() {
    }

    public Item( int comp_logo, String comp_name, int qty, String cat, double price) {

        this.comp_logo = comp_logo;
        this.comp_name = comp_name;
        this.qty = qty;
        this.cat = cat;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComp_logo() {
        return comp_logo;
    }

    public void setComp_logo(int comp_logo) {
        this.comp_logo = comp_logo;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
