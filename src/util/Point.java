package util;

/**
 * Created by think on 2017/7/3.
 */
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double calculateDistance(Point p2) {
        double d;
        d = Math.sqrt(Math.abs((getX() - p2.getX())
                * (getX() - p2.getX())+(getY() - p2.getY())
                * (getY() - p2.getY())));
        return d;
    }
}
