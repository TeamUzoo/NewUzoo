package com.han.teamuzoo.model;

public class RekogRes {
    private String detected_labels;

    public String getDetected_labels() {
        return detected_labels;
    }

    public void setDetected_labels(String detected_labels) {
        this.detected_labels = detected_labels;
    }

    public RekogRes(String detected_labels) {
        this.detected_labels = detected_labels;
    }
}
