package model;

import util.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2017/7/3.
 */

public class AirPortMapModel {
    private List<Point> points = new ArrayList<Point>();
    private Point trackBeginPoints = new Point();
    private Point trackEndPoints = new Point();


    public AirPortMapModel() {
    }

    public AirPortMapModel(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setTrackBeginPoints(Point trackBeginPoints) {
        this.trackBeginPoints = trackBeginPoints;
    }

    public void setTrackEndPoints(Point trackEndPoints) {
        this.trackEndPoints = trackEndPoints;
    }

    public Point getTrackBeginPoints() {
        return trackBeginPoints;
    }

    public Point getTrackEndPoints() {
        return trackEndPoints;
    }

    public void readPoints(){
        points.forEach(point -> {
            System.out.println("x: " + point.getX() + " y: " + point.getY());
        });
    }

    public void readBeginAndEnd() {
        System.out.println("x: " + trackBeginPoints.getX() + " y: " + trackBeginPoints.getY());
        System.out.println("x: " + trackEndPoints.getX() + " y: " + trackEndPoints.getY());
    }
}
/*
10
0 1
2 3
4 0
7 4
9 1
10 8
7 6
5 9
3 5
1 8

10
0 1
2 2
4 0
7 4
9 1
9 8
8 7
5 9
3 6
0 7

7
0 20
40 0
40 20
70 50
50 70
30 50
0 50

4
0 0
0 1
1 1
1 0
*/