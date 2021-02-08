package uk.me.pears;

import java.util.ArrayList;

public class ClusterSet {

    // ClusterSet is an ArrayList of Clusters, containing all the points in the dataset, with each point assigned to a cluster
    private final ArrayList<Cluster> clusters = new ArrayList<>();
    private double dunnIndex;

    public void addToClusterSet(Cluster cluster) {
        clusters.add(cluster);
    }

    public ArrayList<Cluster> getClusters() {
        return clusters;
    }

    public double getDunnIndex() {
        return dunnIndex;
    }

    public void setDunnIndex(double dunnIndex) {
        this.dunnIndex = dunnIndex;
    }

    public void populateClusters(ArrayList<Coordinate> dataSet) {
        // Assign all points in dataset given to clusters based on their centroids

        // Iterate through points in dataset
        for (Coordinate point: dataSet) {
            // Initialise variable for closest cluster, iterate through clusters
            Cluster closestCluster = new Cluster();
            double shortestDistance = -1;
            for (Cluster cluster: clusters) {
                // Calculate Euc distance between point and cluster centroid, set shortest
                EuclideanDistance euclideanDistance = new EuclideanDistance();
                double distance = euclideanDistance.calculate(point, cluster.getCentroid());
                if (shortestDistance == -1 || distance < shortestDistance) {
                    shortestDistance = distance;
                    closestCluster = cluster;
                }
            }
            // Add point to closest cluster
            closestCluster.addPoint(point);
        }
    }

    public void resetClusterPoints() {
        for (Cluster cluster: clusters) {
            cluster.removeAllPoints();
        }
    }

    public boolean emptyClustersExist(){
        boolean emptyClustersExist = false;
        for (Cluster cluster: clusters) {
            if (cluster.getPoints().isEmpty()) {
                System.out.println("A cluster is empty! Regenerating clusters...\n");
                emptyClustersExist = true;
                break;
            }
        }
        return emptyClustersExist;
    }

}
