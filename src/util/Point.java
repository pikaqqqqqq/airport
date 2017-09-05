package util;

import static java.lang.Math.hypot;

/**
 * Created by think on 2017/7/3.
 */
public class Point {
    private double x;
    private double y;

    public Point(){
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double calculateDistance(Point p2) {
        double d;
        d = Math.sqrt(Math.abs((getX() - p2.getX())
                * (getX() - p2.getX())+(getY() - p2.getY())
                * (getY() - p2.getY())));
        return d;
    }

    public static Point additionPoint(Point a, Point b) {
        return new Point(a.getX() + b.getX(), a.getY() + b.getY());
    }

    public static Point minusPoint(Point a, Point b) {
        return new Point(a.getX() - b.getX(), a.getY() - b.getY());
    }

    public static Point multiplicationPoint(Point a, double c) {
        return new Point(a.getX() * c, a.getY() * c);
    }

    public static Point divisionOperationPoint(Point a, double c) {
        return new Point(a.getX() / c, a.getY() / c);
    }

    public static double crossP(Point a, Point b) {
        return a.getX() * b.getY() - a.getY() * b.getX();
    }

    public static double dotP(Point a, Point b) {
        return a.getX() * b.getX() + a.getY() * b.getY();
    }

    public static Point Intersection(Point a1, Point a2, Point b1, Point b2) {
        double v1 = crossP(minusPoint(b2, b1), minusPoint(a1, b1));
        double v2 = crossP(minusPoint(b2, b1), minusPoint(a2, b1));
        Point point = minusPoint(multiplicationPoint(a1, v2), multiplicationPoint(a2, v1));
        return divisionOperationPoint(point,(v2 - v1));//(a1*v2 - a2*v1) / (v2 - v1);
    }

    public double len(){
        return hypot(x,y);
    }
}
