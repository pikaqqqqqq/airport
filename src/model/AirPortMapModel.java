package model;

import util.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2017/7/3.
 */

public class AirPortMapModel {
    private List<Point> points = new ArrayList<Point>();

    public AirPortMapModel() {
    }

    public AirPortMapModel(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void readPoints(){
        points.forEach(point -> {
            System.out.println("x: " + point.getX() + " y: " + point.getY());
        });
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