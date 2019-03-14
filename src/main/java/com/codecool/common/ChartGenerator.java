package com.codecool.common;

import com.codecool.Main;
import com.codecool.client.Client;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ChartGenerator {

    public static void generate(String id) throws ParserConfigurationException, IOException, SAXException {
        //String id = "10";
        String path = id + ".xml";
        InputStream is = Main.class.getResourceAsStream("/"+path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(is);
        Element documentElement = document.getDocumentElement();
        List<Element> allCarNode = new ArrayList<>();
        for (int i = 0; i < documentElement.getChildNodes().getLength(); i++) {
            Node node = documentElement.getChildNodes().item(i);
            if(node instanceof  Element){
                allCarNode.add((Element)node);
            }
        }
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        for (int i = 0; i < allCarNode.size(); i++) {

            Element carElement = allCarNode.get(i);
            int level = Integer.parseInt(carElement.getElementsByTagName("value").item(i).getTextContent());
            long rawTime = Long.valueOf(carElement.getElementsByTagName("time").item(i).getTextContent());
            String hour = Client.convertMillisIntoDate(rawTime);
            System.out.println(hour);
            line_chart_dataset.addValue(level, "level", hour);
        }
        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Water level per hour", "Hour",
                "Water level(mm)",
                line_chart_dataset, PlotOrientation.VERTICAL,
                true, true, false);

        int width = 1360;    /* Width of the image */
        int height = 768;   /* Height of the image */
        File lineChart = new File( id + ".png" );
        try {
            ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
