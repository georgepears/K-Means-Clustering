package uk.me.pears;

import java.util.ArrayList;

public class DunnIndex {

    private ArrayList<Cluster> clusters = new ArrayList<>();
    private final EuclideanDistance euclideanDistance = new EuclideanDistance();

    public double calculate(ArrayList<Cluster> clusters) {
        // Calculate Dunn Index Similarity Measure
        this.clusters = clusters;

        return calculateMinCentroidDifference() / calculateMaxClusterDistance();
    }

    private double calculateMinCentroidDifference() {
        // Get 'top half' of Euclidean Distance equation (min distance between all centroids)
        double minCentroidDifference = -1;
        for (var i: clusters) {
            for (var j: clusters) {
                if (i != j) {
                    // Euclidean distance between these centroids
                    double eucDistance = euclideanDistance.calculate(i.getCentroid(), j.getCentroid());

                    // If this is the smallest distance so far, set as minimum
                    if (minCentroidDifference == -1 || eucDistance < minCentroidDifference) {
                        minCentroidDifference = eucDistance;
                    }
                }
            }
        }
        return minCentroidDifference;
    }

    private double calculateMaxClusterDistance() {
        // Get 'bottom half' of Euclidean Distance equation (max difference between intra-cluster pairs)
        double maxClusterDifference = 0;
        // Iterate through clusters, and all of the points within them
        for (var cluster : clusters) {
            for (var i : cluster.getPoints()) {
                for (var j : cluster.getPoints()) {
                    if (i != j) {
                        // Euclidean distance between these points
                        double eucDistance = euclideanDistance.calculate(i, j);

                        // If this is the largest distance so far, set as maximum
                        if (eucDistance > maxClusterDifference) {
                            maxClusterDifference = eucDistance;
                        }
                    }
                }
            }
        }
        return maxClusterDifference;
    }


}
