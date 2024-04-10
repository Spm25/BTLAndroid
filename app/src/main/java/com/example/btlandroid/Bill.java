package com.example.btlandroid;

import java.util.Date;

public class Bill {
    private int id;
    private Date date;
    private int price;
    private String create;

    public Bill(int id, Date date, int price, String create) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.create = create;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }
}
