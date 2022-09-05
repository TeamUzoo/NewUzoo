package com.han.teamuzoo.model;

import java.io.Serializable;

public class Follower implements Serializable {

    private int id;
    private String name;
    private int successed;
    private int failed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSuccessed() {
        return successed;
    }

    public void setSuccessed(int successed) {
        successed = successed;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        failed = failed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
