package com.han.teamuzoo.model;

import java.io.Serializable;

public class MainTimer{

    private int setTime;

    public MainTimer(int setTime){
        this.setTime = setTime;
    }

    public MainTimer() {

    }

    public int getSetTime() {
        return setTime;
    }

    public void setSetTime(int setTime) {
        this.setTime = setTime;
    }
}
