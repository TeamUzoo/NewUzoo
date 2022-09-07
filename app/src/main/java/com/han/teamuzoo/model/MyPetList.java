package com.han.teamuzoo.model;

import java.util.List;

public class MyPetList {

    private String reuslt;
    private int count;
    private List<MyPet> pets;

    public String getReuslt() {
        return reuslt;
    }

    public void setReuslt(String reuslt) {
        this.reuslt = reuslt;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MyPet> getPets() {
        return pets;
    }

    public void setPets(List<MyPet> pets) {
        this.pets = pets;
    }
}
