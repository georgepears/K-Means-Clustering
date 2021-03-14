package uk.me.pears;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class JSONGenerator {

    // Colours for graph output
    private final String[] graphColors = new String[]{
            "#f1c40f", // Yellow
            "#e74c3c", // Red
            "#1abc9c", // Teal
            "#f39c12", // Orange
            "#3498db", // Blue
            "#9b59b6", // Purple
            "#34495e", // Grey
            "#2ecc71", // Green
    };

    public void generate(ClusterSet clusterSet, ArrayList<Double> dunnIndexHistory, double acceptableDunnIndex, int dataSetSize) throws IOException {

        // Generate HTML page to display data in scatter chart
        String outputFile = "data/out/data.js";
        FileWriter fileWriter = new FileWriter(outputFile);

        fileWriter.append(convertClusterDataToJSON(clusterSet, dunnIndexHistory, acceptableDunnIndex, dataSetSize));

        fileWriter.flush();
        fileWriter.close();

    }

    public String convertClusterDataToJSON(ClusterSet clusterSet, ArrayList<Double> dunnIndexHistory, double acceptableDunnIndex, int dataSetSize) {
        // Build the JSON data to be displayed on the html / js graph
        StringBuilder jd = new StringBuilder(); // jd is short for jsonData
        jd.append("clusterSets = [ ");

//        Loop through clusters and append data for each
        for (int c = 0; c < clusterSet.getClusters().size(); c++) {
            // Set cluster name, colour, etc. for graph display
            jd.append(String.format("{ label: 'Cluster %s', ", (c+1)));
            jd.append(String.format("centroid: '%s', ", clusterSet.getClusters().get(c).getCentroid().toString()));
            jd.append(String.format("size: '%s', ", clusterSet.getClusters().get(c).getPoints().size()));
            jd.append(String.format("percentage: '%s', ", ((clusterSet.getClusters().get(c).getPoints().size() * 100) / dataSetSize)));
            jd.append(String.format("borderColor: '%s', ", graphColors[(c + 1)]));
            jd.append(String.format("backgroundColor: '%s', ", graphColors[(c + 1)]));
            jd.append("radius: '4', hoverRadius: '5', data: [ ");

            // Iterate through points in this cluster and append
            for (Coordinate point: clusterSet.getClusters().get(c).getPoints()) {
                jd.append(String.format("{ x: %s, y: %s }, ", point.getX(), point.getY()));
            }
            jd.append(" ]}, ");
        }

        // Append centroid data to graph
        jd.append("{ label: 'Centroids', ");
        jd.append(String.format("borderColor: '%s', ", graphColors[0]));
        jd.append(String.format("backgroundColor: '%s', ", graphColors[0]));
        jd.append("pointStyle: 'triangle', radius: '7', hoverRadius: '7', data: [ ");

        // Iterate through clusters to get centroids & append coordinate
        for (Cluster cluster: clusterSet.getClusters()) {
            jd.append(String.format("{ x: %s, y: %s }, ", cluster.getCentroid().getX(), cluster.getCentroid().getY()));
        }
        jd.append(" ]}] ");

        // Pass dunn index, attempts, etc. to be displayed in webpage
        NumberFormat formatter = new DecimalFormat("#0.000");
        jd.append(String.format("\ndunnIndex = %s", formatter.format(clusterSet.getDunnIndex())));
        jd.append(String.format("\ndataSetSize = %s", dataSetSize));
        jd.append(String.format("\nacceptableDunnIndex = %s", acceptableDunnIndex));
        jd.append(String.format("\nnoAttempts = %s", dunnIndexHistory.size()));

        // Append history of dunn index measures for previous attempts
        jd.append("\ndunnIndexHistory = { labels: [");

        // Append y axis of dunn index line graph
        for (int i = 0; i < dunnIndexHistory.size(); i++){
            jd.append(String.format("'Attempt %s', ", (i+1)));
        }

        // Append x axis of dunn index line graph
        jd.append("], datasets: [{ label: 'Dunn Index Measure', data: [");
        for (Double index: dunnIndexHistory) {
            jd.append(String.format("'%s', ", index));
        }
        jd.append("\n], fill: false, borderColor: '#e95420', lineTension: 0.1}]}");

        // Return full json string to be saved to file
        return jd.toString();

    }

}
