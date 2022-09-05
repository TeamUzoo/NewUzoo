package com.han.teamuzoo.model;

public class ResultRes {


    private String result;
    private int successed_count;
    private int failed_count;

    public int getFailed_count() {
        return failed_count;
    }

    public void setFailed_count(int failed_count) {
        this.failed_count = failed_count;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getSuccessed_count() {
        return successed_count;
    }

    public void setSuccessed_count(int successed_count) {
        this.successed_count = successed_count;
    }


}
