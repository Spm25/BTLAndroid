package com.example.btlandroid;

import java.util.Date;

public class Invoice {
    private int id;
    private Date date;
    private double price;
    private String creator;

    public Invoice(int id, Date date, double price, String creator) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
