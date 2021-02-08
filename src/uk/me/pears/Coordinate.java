package uk.me.pears;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Coordinate {

    // Coordinates hold x and y values for points and centroids
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void appendX(double x) {
        this.x += x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void appendY(double y) {
        this.y += y;
    }

    public String toString() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        String xFormat = formatter.format(x);
        String yFormat = formatter.format(y);
        return("(" + xFormat + ", " + yFormat + ")");
    }

    public boolean equals(Coordinate coordinate) {
        return this.getX() == coordinate.getX() && this.getY() == coordinate.getY();
    }
}