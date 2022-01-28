package com.fisei.athanasiaapp.objects;

public class ShopCartItem {
    public String Name;
    public String ImageURL;
    public int Quantity;
    public double UnitPrice;

    public ShopCartItem(String name, String icon, int qty, int unitPrice){
        this.Name = name;
        this.ImageURL = icon;
        this.Quantity = qty;
        this.UnitPrice = unitPrice;
    }
}