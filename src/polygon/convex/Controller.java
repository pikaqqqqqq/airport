package polygon.convex;

import util.Point;

import java.util.Scanner;

/**
 * Created by think on 2017/7/3.
 */
public class Controller {

    public static void main(String[] args) {
        int n;
        AirPortMapData airPortMapData = new AirPortMapData();
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        //System.out.print(n);
        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            Point p = new Point(x, y);
            airPortMapData.getPoints().add(p);
        }

        airPortMapData.calculateEveryDistance();
        //airPortMapData.readPoints();
    }
}
