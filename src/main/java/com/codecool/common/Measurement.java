package com.codecool.common;

import org.w3c.dom.Document;

import java.io.Serializable;

public class Measurement implements Serializable {
    private int id;
    private long time;
    private int value;
    private String type;

    public Measurement(int id, int value, String type) {
        this.id = id;
        this.time = System.currentTimeMillis();
        this.value = value;
        this.type = type;
    }

    public Measurement(int id, long time, int value, String type) {
        this.id = id;
        this.time = time;
        this.value = value;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public int getValue() {
        return value;
    }

    private String getType() {
        return type;
    }
}
