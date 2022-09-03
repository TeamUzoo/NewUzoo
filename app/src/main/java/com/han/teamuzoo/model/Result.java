package com.han.teamuzoo.model;

import java.io.Serializable;

public class Result implements Serializable {


    public int id;
    public int successed;
    public int failed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSuccessed() {
        return successed;
    }

    public void setSuccessed(int successed) {
        this.successed = successed;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }
}
