package com.han.teamuzoo.model;

import java.util.List;

public class MyPetList {

    private String result;
    private int count;
    private List<MyPet> items;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MyPet> getItems() {
        return items;
    }

    public void setItems(List<MyPet> items) {
        this.items = items;
    }
}
