import util.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static util.Point.*;

/**
 * Created by think on 2017/9/5.
 */
public class Arithmetic {
    private List<Point> points = new ArrayList<Point>();
    int N = 0;
    public Arithmetic(){
        input();
        N = points.size();
        calculateAll();
    }

    public void input(){
        int n;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            Point p = new Point(x, y);
            points.add(p);
        }
    }

    public void makeCCLOrder() {
        //顺时针计算会出问题
        double ar = 0.0;
        for (int i = 0; i < points.size(); i++) {
            ar += crossP(points.get(i), points.get((i + 1) % points.size()));
        }
        if (ar < 0) {
            Collections.reverse(points);
            points.forEach(point -> {
                System.out.println("倒序x: " + point.getX() + " y: " + point.getY());
            });
        }
    }

    public void calculateAll() {
        makeCCLOrder();
        double ret = 0.0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                Point a = points.get(i);
                Point b = points.get(j);
                if (crossP(minusPoint(b, a), minusPoint(points.get((i + N - 1) % N), a)) *
                        crossP(minusPoint(b, a), minusPoint(points.get((i + 1) % N), a)) > 0 &&
                        crossP(minusPoint(a, points.get((i + N - 1) % N)), minusPoint(points.get((i + 1) % N), a)) > 0) {

                    continue;
                }
                double alen = 1e50, blen = 1e50;
                System.out.println("a: (" + a.getX() + "," + a.getY() + ") - b: (" + b.getX() + "," + b.getY() + ")");
                for (int k = 0; k < N; k++) {
                    Point p0 = points.get((k + N - 1) % N);
                    Point p1 = points.get(k);
                    Point p2 = points.get((k + 1) % N);
                    Point p3 = points.get((k + 2) % N);
                    if (crossP(minusPoint(b, a), minusPoint(p1, a)) == 0 &&
                            crossP(minusPoint(b, a), minusPoint(p2, a)) == 0) {//平行||共线
                        System.out.println("平行||共线");
                        double dp1 = dotP(minusPoint(b, a), minusPoint(p1, a));
                        double dp2 = dotP(minusPoint(b, a), minusPoint(p2, a));
                        if (dp2 <= 0 && dp2 <= dp1 && crossP(minusPoint(b, a), minusPoint(p3, a)) < 0) {
                            System.out.println(" alen p2: " + p2.getX() + "," + p2.getY());
                            alen = min(alen, dotP(minusPoint(a, b), minusPoint(p2, a)));
                        }
                        if (dp1 <= 0 && dp1 <= dp2 && crossP(minusPoint(b, a), minusPoint(p0, a)) > 0) {
                            System.out.println(" alen p1: " + p1.getX() + "," + p1.getY());
                            alen = min(alen, dotP(minusPoint(a, b), minusPoint(p1, a)));
                        }
                        if (dp2 >= 0 && dp2 >= dp1 && crossP(minusPoint(b, a), minusPoint(p3, a)) > 0) {
                            System.out.println(" blen p2: " + p2.getX() + "," + p2.getY());
                            blen = min(blen, dotP(minusPoint(b, a), minusPoint(p2, a)));
                        }
                        if (dp1 >= 0 && dp1 >= dp2 && crossP(minusPoint(b, a), minusPoint(p0, a)) < 0) {
                            System.out.println(" blen p1: " + p1.getX() + "," + p1.getY());
                            blen = min(blen, dotP(minusPoint(b, a), minusPoint(p1, a)));
                        }
                    } else if (crossP(minusPoint(p2, p1), minusPoint(a, p1)) >= 0 &&
                            crossP(minusPoint(b, a), minusPoint(p2, a)) < 0 &&
                            (crossP(minusPoint(b, a), minusPoint(p1, a)) > 0 ||
                                    crossP(minusPoint(b, a), minusPoint(p1, a)) == 0 &&
                                            crossP(minusPoint(b, a), minusPoint(p0, a)) > 0)) {
                        System.out.println(">0");
                        Point ip = Intersection(p1, p2, a, b);
                        System.out.println(" Intersection alen ip: " + ip.getX() + "," + ip.getY());
                        alen = min(alen, dotP(minusPoint(a, b), minusPoint(ip, a)));

                    } else if (crossP(minusPoint(p2, p1), minusPoint(a, p1)) >= 0 &&
                            crossP(minusPoint(b, a), minusPoint(p2, a)) > 0 &&
                            (crossP(minusPoint(b, a), minusPoint(p1, a)) < 0 ||
                                    crossP(minusPoint(b, a), minusPoint(p1, a)) == 0 &&
                                            crossP(minusPoint(b, a), minusPoint(p0, a)) < 0)) {
                        System.out.println("<0");
                        Point ip = Intersection(p1, p2, a, b);
                        System.out.println(" Intersection alen ip: " + ip.getX() + "," + ip.getY());
                        blen = min(blen, dotP(minusPoint(b, a), minusPoint(ip, a)));
                    }
                }

                System.out.println(" ret: " + (alen + blen) / minusPoint(b, a).len());
                ret = max(ret, (alen + blen) / minusPoint(b, a).len());
                System.out.println("");
            }
            System.out.println("---------------------------------------------------------");
        }
        System.out.println("" + ret);

    }

    public static void main(String[] args) {
        new Arithmetic();
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