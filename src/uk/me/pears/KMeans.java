package uk.me.pears;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class KMeans {

    private boolean clustersMoved = false;
    private int noAttempts = 0;
    private final ArrayList<Double> dunnIndexHistory = new ArrayList<>();

    private ArrayList<Coordinate> dataSet;
    private ClusterSet clusterSet = new ClusterSet();

    public void generate(String dataSetURL, int noClusters, double acceptableDunnIndex) {
        // Method called to start generating a clustered dataset from a CSV file

        // Read csv to dataSet variable
        CSVReader csv = new CSVReader();
        dataSet = csv.read(dataSetURL);

        // Repeat the algorithm until the acceptable dunn index is met (max's out at 10 attempts)
        do {
            noAttempts++;

            // Generate initial clusters with random centroids, then populate them with dataset, if an clusters empty, run again
            do {
                clusterSet = new ClusterSet();
                createInitialClusters(noClusters);
                clusterSet.populateClusters(dataSet);
            } while (clusterSet.emptyClustersExist());

            // Until no clusters have moved, calculate new centroids and re-populate
            do {
                calculateNewCentroids();
                clusterSet.resetClusterPoints();
                clusterSet.populateClusters(dataSet);
            } while (clustersMoved);

            // Calculate dunn index and bind to cluster set
            DunnIndex dunnIndex = new DunnIndex();
            clusterSet.setDunnIndex(dunnIndex.calculate(clusterSet.getClusters()));
            System.out.println("\nCluster set's Dunn Index Measure: " + clusterSet.getDunnIndex());

            dunnIndexHistory.add(clusterSet.getDunnIndex());

        } while (clusterSet.getDunnIndex() < acceptableDunnIndex && noAttempts <= 10);

        System.out.println("No Attempts: " + noAttempts);

        // Generate JSON data to display in HTML page
        JSONGenerator jsonGenerator = new JSONGenerator();
        try {
            jsonGenerator.generate(clusterSet, dunnIndexHistory, acceptableDunnIndex, dataSet.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createInitialClusters(int noClusters) {
        // Generates 'noClusters' number of initial clusters, with randomized centroids
        for (int i = 0; i < noClusters; i++){
            Cluster cluster = new Cluster();
            cluster.setCentroid(generateRandomCentroids());
            clusterSet.addToClusterSet(cluster);
            System.out.println("Cluster generated with centroid: " + cluster.getCentroid().toString());
        }
    }

    private Coordinate generateRandomCentroids() {
        // Generates random centroids for cluster initialisation
        Random random = new Random();
        Coordinate maxCentroids = getMaxDataSetCoords();
        Coordinate centroid = new Coordinate();
        centroid.setX(random.nextInt((int)maxCentroids.getX()));
        centroid.setY(random.nextInt((int)maxCentroids.getY()));
        return centroid;
    }

    private Coordinate getMaxDataSetCoords() {
        // Get maximum x and y values of the dataset in order to generate random centroids
        Coordinate maxCoordinate = new Coordinate();
        for (Coordinate i: dataSet) {
            if (i.getX() > maxCoordinate.getX()){
                maxCoordinate.setX(i.getX());
            }
            if (i.getY() > maxCoordinate.getY()){
                maxCoordinate.setY(i.getY());
            }
        }
        return maxCoordinate;
    }

    private void calculateNewCentroids() {
        System.out.println("\nRecalculating cluster centroids");
        clustersMoved = false;
        for (Cluster cluster: clusterSet.getClusters()) {
            Coordinate clusterAvg = calculateClusterAverage(cluster);

            // Check if centroids have moved, if not then terminate
            if (cluster.getCentroid().equals(clusterAvg)) {
                System.out.println("    Cluster centroid not moving, staying at " + cluster.getCentroid() + " - Points: " + cluster.getPoints().size());
            }
            else {
                System.out.println("    Cluster centroid moving from " + cluster.getCentroid().toString() + " to " + clusterAvg.toString()+ " - Points: " + cluster.getPoints().size());
                cluster.setCentroid(clusterAvg);
                clustersMoved = true;
            }
        }
    }

    private Coordinate calculateClusterAverage(Cluster cluster) {
        // Get sum of all x and y coordinates
        Coordinate clusterSum = new Coordinate();
        for (Coordinate point: cluster.getPoints()) {
            clusterSum.appendX(point.getX());
            clusterSum.appendY(point.getY());
        }

        // Divide by size to get average
        Coordinate clusterAvg = new Coordinate();
        int size = cluster.getPoints().size();
        clusterAvg.setX(clusterSum.getX() / size);
        clusterAvg.setY(clusterSum.getY() / size);

        return clusterAvg;
    }

}
