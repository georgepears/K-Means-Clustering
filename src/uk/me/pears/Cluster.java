package uk.me.pears;

import java.util.ArrayList;

public class Cluster {

    // Each Cluster has an array of points and a centroid
    private final ArrayList<Coordinate> points = new ArrayList<>();
    private Coordinate centroid;

    public Coordinate getCentroid() {
        return centroid;
    }

    public void setCentroid(Coordinate centroid) {
        this.centroid = centroid;
    }

    public void addPoint(Coordinate point){
        points.add(point);
    }

    public ArrayList<Coordinate> getPoints() {
        return points;
    }

    public void removeAllPoints() {
        points.clear();
    }
}