package polygon.convex;

import util.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2017/7/3.
 */

public class AirPortMapData {
    private List<Point> points = new ArrayList<Point>();
    private double[] distances = new double[9700];

    public AirPortMapData() {
    }

    public AirPortMapData(List<Point> points) {
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

    public void calculateEveryDistance(){
        for(int i = 0; i < points.size(); i++){
            for (int j = 0; j < points.size(); j++){
                distances[i] = points.get(i).calculateDistance(points.get(j));
                System.out.println(distances[i]);
            }
        }
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

*/