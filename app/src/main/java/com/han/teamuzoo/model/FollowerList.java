package com.han.teamuzoo.model;

import java.util.List;

public class FollowerList {

    private String result;
    private int count;
    private List<Follower> items;

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

    public List<Follower> getItems() {
        return items;
    }

    public void setItems(List<Follower> items) {
        this.items = items;
    }
}
