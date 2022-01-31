package com.fisei.athanasiaapp.objects;

public class ShopCartItem {
    public int Id;
    public String Name;
    public String ImageURL;
    public int Quantity;
    public double UnitPrice;

    public ShopCartItem(int id, String name, String icon, int qty, double unitPrice){
        this.Id = id;
        this.Name = name;
        this.ImageURL = icon;
        this.Quantity = qty;
        this.UnitPrice = unitPrice;
    }
}