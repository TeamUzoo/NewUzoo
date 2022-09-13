package com.han.teamuzoo.model;

public class GetCoin {

    private int getCoin;
    private int currency_now;


    public GetCoin(int getCoin) {
        this.getCoin = getCoin;
    }

    public int getGetCoin() {
        return getCoin;
    }

    public void setGetCoin(int getCoin) {
        this.getCoin = getCoin;
    }

    public int getCurrency_now() {
        return currency_now;
    }

    public void setCurrency_now(int currency_now) {
        this.currency_now = currency_now;
    }
}
