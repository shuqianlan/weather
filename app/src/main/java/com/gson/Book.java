package com.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName(value = "Book_Author", alternate = {"General_Author", "DaShen_Author"})
    @Expose
    protected String author;
    @Expose
    protected String name;
    @Expose
    protected double price;

    public Book(String author, String name, double price) {
        this.author = author;
        this.name   = name;
        this.price  = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
