package com.gson;

import com.google.gson.annotations.Expose;

public class SanTiBook extends Book {

    @Expose
    protected float discount = 120;

    public SanTiBook(String author, String name, double price) {
        super(author, name, price);
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "SanTiBook{" +
                "discount=" + discount +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
