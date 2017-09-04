package model;

import controller.Controller;
import util.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static util.Point.*;

/**
 * Created by think on 2017/8/22.
 */
public class CalculateModel {
    Controller controller;
    private List<Point> points = new ArrayList<Point>();
    private double[] distances = new double[9700];
    int N;

    public CalculateModel(Controller controller) {
        this.controller = controller;
        for (int i = 0; i < controller.airPortMapModel.getPoints().size(); i++) {
            util.Point point = new Point(controller.airPortMapModel.getPoints().get(i).getX(), controller.airPortMapModel.getPoints().get(i).getY());
            points.add(point);
        }
        N = points.size();
    }

    public void calculateEveryDistance() {//计算凸多边形的直径
        makeCCLOrder();
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                distances[i] = points.get(i).calculateDistance(points.get(j));
                System.out.println(distances[i]);
            }
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
                            crossP(minusPoint(b, a), minusPoint(p1, a)) > 0 ||
                            crossP(minusPoint(b, a), minusPoint(p1, a)) == 0 &&
                                    crossP(minusPoint(b, a), minusPoint(p0, a)) > 0) {
                        System.out.println(">0");
                        Point ip = Intersection(p1, p2, a, b);
                        System.out.println(" Intersection alen ip: " + ip.getX() + "," + ip.getY());
                        alen = min(alen, dotP(minusPoint(a, b), minusPoint(ip, a)));

                    } else if (crossP(minusPoint(p2, p1), minusPoint(a, p1)) >= 0 &&
                            crossP(minusPoint(b, a), minusPoint(p2, a)) > 0 &&
                            crossP(minusPoint(b, a), minusPoint(p1, a)) < 0 ||
                            crossP(minusPoint(b, a), minusPoint(p1, a)) == 0 &&
                                    crossP(minusPoint(b, a), minusPoint(p0, a)) < 0) {
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
        System.out.printf("" + ret);
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

}
