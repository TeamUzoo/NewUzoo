package com.han.teamuzoo.model;

import java.io.Serializable;

public class Follower implements Serializable {

    private int id;
    private String txtName;
    private int txtSuc;
    private int txtDea;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public int getTxtSuc() {
        return txtSuc;
    }

    public void setTxtSuc(int txtSuc) {
        this.txtSuc = txtSuc;
    }

    public int getTxtDea() {
        return txtDea;
    }

    public void setTxtDea(int txtDea) {
        this.txtDea = txtDea;
    }
}
