package com.han.teamuzoo.model;

public class AchiveRes {
private String result;
private int currency_now;

    public AchiveRes(int currency_now) {
        this.currency_now = currency_now;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCurrency_now() {
        return currency_now;
    }

    public void setCurrency_now(int currency_now) {
        this.currency_now = currency_now;
    }
}
