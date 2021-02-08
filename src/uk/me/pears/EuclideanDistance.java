package uk.me.pears;

public class EuclideanDistance {

    public double calculate(Coordinate q, Coordinate p) {
        return Math.sqrt(((q.getX() - p.getX()) * (q.getX() - p.getX())) + ((q.getY() - p.getY()) * (q.getY() - p.getY())));
    }
}
