package com.han.teamuzoo.model;

import android.widget.ImageView;

import java.io.Serializable;

public class Shop implements Serializable {

    private int id;
    private String petName;
    private int cost;
    private int species;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getSpecies() {
        return species;
    }

    public void setSpecies(int species) {
        this.species = species;
    }
}
