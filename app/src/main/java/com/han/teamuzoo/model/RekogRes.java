package com.han.teamuzoo.model;

import java.util.ArrayList;

public class RekogRes {
    private ArrayList<String> detected_labels;

    public ArrayList<String> getDetected_labels() {
        return detected_labels;
    }

    public void setDetected_labels(ArrayList<String> detected_labels) {
        this.detected_labels = detected_labels;
    }

    public RekogRes(ArrayList<String> detected_labels) {
        this.detected_labels = detected_labels;
    }
}
