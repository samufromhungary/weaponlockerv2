package com.codecool.common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public String getType() {
        return type;
    }

    public Document convertToDocument() {
        XMLHandler handler = new XMLHandler();
        DocumentBuilder db = handler.createDocumentBuilder();
        Document doc = db.newDocument();
        Element root = doc.createElement("measurement");
        doc.appendChild(root);
        root.setAttribute("id", String.valueOf(id));
        handler.createElement(doc, "time", String.valueOf(time), root);
        handler.createElement(doc, "value", String.valueOf(value), root);
        handler.createElement(doc, "type", type.toLowerCase(), root);
        return doc;
    }
}
