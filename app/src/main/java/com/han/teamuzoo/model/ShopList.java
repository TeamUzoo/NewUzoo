package com.han.teamuzoo.model;

import java.util.List;

public class ShopList {

    private String result;
    private int count;
    private List<Shop> pets;


    public void setResult(String result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Shop> getItems() {
        return pets;
    }

    public void setItems(List<Shop> items) {
        this.pets = pets;
    }

    public String getResult() {
        return result;
    }
}
