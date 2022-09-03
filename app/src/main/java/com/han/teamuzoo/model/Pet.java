package com.han.teamuzoo.model;

public class Pet {

    private String petName;
    private String Spiecies;
    private int Cost;

    private int id;

    public Pet(String petName, String Spiecies, int Cost) {
        this.petName = petName;
        this.Spiecies = Spiecies;
        this.Cost = Cost;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getSpiecies() {
        return Spiecies;
    }

    public void setSpiecies(String spiecies) {
        Spiecies = spiecies;
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    //    todo : ShopListActivity 에 연동할 동물 Model 완성하기

}
