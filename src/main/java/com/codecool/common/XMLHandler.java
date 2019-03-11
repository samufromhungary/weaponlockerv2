package com.codecool.common;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLHandler {

    private Document doc;
    private List<Measurement> measurements;

    public XMLHandler() throws NullPointerException{
        DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try{
            dBuilder = dFactory.newDocumentBuilder();
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }
        this.doc = dBuilder.newDocument();
        this.measurements = new ArrayList<>();
    }

    public void handle(Measurement measurement){
        if (getFiles(".").contains(measurement.getId() + ".xml")){
            for (String name : getFiles(".")){
                if(name.equals(measurement.getId() + ".xml")){
                    load(measurement.getId() + ".xml");
                    Element rootNode = doc.getDocumentElement();
                    List<Element> nodes = getElements(rootNode);
                    addMeasurement(nodes, rootNode);
                }
            }
        }
        measurements.add(measurement);
        write(measurement);
    }

    private void write(Measurement measurement){
        DocumentBuilder dBuilder;
        Element rootElement = null;

        try{
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();
            rootElement = doc.createElement("measurements");
            doc.appendChild(rootElement);

            doc.createAttribute("id");
            rootElement.setAttribute("id", String.valueOf(measurement.getId()));
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }

        for (Measurement tempMeasurement : measurements){
            if(rootElement != null){
                writeNodes(tempMeasurement, rootElement);
            }
        }

    }

    private void load(String file){
        try{
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
            InputStream is = new FileInputStream(file);
            this.doc = dBuilder.parse(is);
            this.doc.getDocumentElement().normalize();
        }catch (ParserConfigurationException | IOException |SAXException e){
            e.printStackTrace();
        }

    }

    private void addMeasurement(List<Element> nodes, Element rootNode){
        int id = Integer.valueOf(rootNode.getAttribute("id"));
        for(Element node : nodes){
            List<Element> ls = getElements(node);
            long time = Long.valueOf(getString(ls, "time"));
            int value = Integer.valueOf(getString(ls, "value"));
            String type = getString(ls, "type").toUpperCase();
            Measurement measurement = new Measurement(id, time, value, type);
            measurements.add(measurement);
        }


    }

    private void writeNodes(Measurement measurement, Element rootElement){
        try {
            Element tempMeasurement = doc.createElement("measurement");

            rootElement.appendChild(tempMeasurement);

            Element time = doc.createElement("time");
            time.appendChild(doc.createTextNode(String.valueOf(measurement.getTime())));
            tempMeasurement.appendChild(time);

            Element value = doc.createElement("value");
            value.appendChild(doc.createTextNode(String.valueOf(measurement.getValue())));
            tempMeasurement.appendChild(value);

            Element type = doc.createElement("type");
            type.appendChild(doc.createTextNode(measurement.getType()));
            tempMeasurement.appendChild(type);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(measurement.getId() + ".xml"));

            transformer.transform(source, result);

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    private String getString(List<Element> elements, String name){
        for (Element element : elements) {
            if (element.getTagName().equals(name)) {
                return element.getTextContent();
            }
        }
        throw new IllegalStateException();
    }

    private List<Element> getElements(Element parentNode){
        ArrayList<Element> elements = new ArrayList<>();
        for (int i = 0; i < parentNode.getChildNodes().getLength(); i++) {
            Node childNode = parentNode.getChildNodes().item(i);
            if (childNode instanceof Element) {
                elements.add((Element) childNode);
            }

        }
        return elements;

    }

    private List<String> getFiles(String path) throws NullPointerException{
        List<String> fileNames = new ArrayList<>();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".xml"));

        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getName());
                fileNames.add(file.getName());
            }
        }
        return fileNames;
    }



}
