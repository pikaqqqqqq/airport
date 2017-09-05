package view;

import controller.Controller;
import util.Point;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by think on 2017/8/22.
 */
public class MainView extends Frame {
    Controller controller;
    public static final int HEIGHT = 800;
    public static final int WIDTH = 800;
    int perLength = WIDTH / 20;//40

    Map<Integer, Integer> yMap = new HashMap<Integer, Integer>();
    Image offScreenImage = null;

    private List<util.Point> points = new ArrayList<util.Point>();
    private Point trackBeginPoints = new Point();
    private Point trackEndPoints = new Point();


    public MainView(Controller controller, Point trackBeginPoints, Point trackEndPoints) {
        this.controller = controller;
        for (int i = 0; i < controller.airPortMapModel.getPoints().size(); i++) {
            util.Point point = new Point(controller.airPortMapModel.getPoints().get(i).getX(), controller.airPortMapModel.getPoints().get(i).getY());
            points.add(point);
        }
        this.trackBeginPoints = trackBeginPoints;
        this.trackEndPoints = trackEndPoints;

        disposeData();
        disposeRunwayData();
        launchFrame();
    }

    public void updateMapData() {
        points.clear();
        for (int i = 0; i < controller.airPortMapModel.getPoints().size(); i++) {
            util.Point point = new Point(controller.airPortMapModel.getPoints().get(i).getX(), controller.airPortMapModel.getPoints().get(i).getY());
            points.add(point);
        }
        disposeData();
    }

    public void updateRunwayData(Point trackBeginPoints, Point trackEndPoints) {
        this.trackBeginPoints = trackBeginPoints;
        this.trackEndPoints = trackEndPoints;
        disposeRunwayData();
    }

    public void launchFrame() {
        setLocation(200, 100);
        setSize(WIDTH, HEIGHT);
        //0.2设置窗口属性
        setTitle("AirPortMap");
        //setResizable(false);//不让窗口改变大小
        this.setBackground(Color.white);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
        new Thread(new PaintTread()).start();
    }

    public void paintCoordinateAxis(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.black);

        int xString = -10;
        int yString = 10;
        //X轴
        g.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2);
        for (int i = 0; i < 20; i++) {
            g.drawLine(perLength * i, HEIGHT / 2 - 5, perLength * i, HEIGHT / 2);
            if (i > 0)
                if (xString + i != 0)
                    g.drawString(xString + i + "", perLength * i - 2, HEIGHT / 2 + 15);
        }
        //y轴
        g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
        for (int i = 0; i < 20; i++) {
            g.drawLine(WIDTH / 2, perLength * i, WIDTH / 2 + 5, perLength * i);
            if (i > 0)
                if (yString - i != 0) {
                    g.drawString(yString - i + "", WIDTH / 2 - 20, perLength * i + 4);
                }
        }
        g.setColor(c);//不要改变原来的前景色
    }

    public void disposeData() {
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).getX() > 10 || points.get(i).getY() > 10) {
                for (int j = 0; j < points.size(); j++) {
                    points.set(j, util.Point.divisionOperationPoint(points.get(j), 10));
                }
                break;
            }
        }
        for (int i = 0; i < 20; i++) {
            yMap.put(10 - i, perLength * i);

        }
        for (int i = 0; i < points.size(); i++) {
            for (int j = -10; j <= 10; j++) {
                if (points.get(i).getY() == j) {
                    int value = yMap.get(j);
                    points.get(i).setY(value);
                }
            }
        }

        points.forEach(point -> {
            System.out.println("x: " + point.getX() + " y: " + point.getY());
        });
    }

    public void disposeRunwayData() {
        if (controller.airPortMapModel.getTrackBeginPoints().getX() > 10 ||
                controller.airPortMapModel.getTrackBeginPoints().getY() > 10 ||
                controller.airPortMapModel.getTrackEndPoints().getX() > 10 ||
                controller.airPortMapModel.getTrackEndPoints().getX() > 10) {
            trackBeginPoints.setX(controller.airPortMapModel.getTrackBeginPoints().getX() / 10);
            trackBeginPoints.setY(controller.airPortMapModel.getTrackBeginPoints().getY() / 10);
            trackEndPoints.setX(controller.airPortMapModel.getTrackEndPoints().getX() / 10);
            trackEndPoints.setY(controller.airPortMapModel.getTrackEndPoints().getY() / 10);
        } else {
            trackBeginPoints.setX(controller.airPortMapModel.getTrackBeginPoints().getX());
            trackBeginPoints.setY(controller.airPortMapModel.getTrackBeginPoints().getY());
            trackEndPoints.setX(controller.airPortMapModel.getTrackEndPoints().getX());
            trackEndPoints.setY(controller.airPortMapModel.getTrackEndPoints().getY());
        }
        trackBeginPoints.setX((trackBeginPoints.getX() + 10) * perLength);
        trackEndPoints.setX((trackEndPoints.getX() + 10) * perLength);
        trackBeginPoints.setY((10 - trackBeginPoints.getY()) * perLength);
        trackEndPoints.setY((10 - trackEndPoints.getY()) * perLength);
    }


    public void paintData(Graphics g) {
        if (controller.isPaintMap) {
            Color c = g.getColor();
            g.setColor(Color.black);
            for (int i = 0; i < points.size() - 1; i++) {
                int ax = (int) ((points.get(i).getX() + 10) * perLength);
                int ay = (int) ((points.get(i).getY()));
                int bx = (int) ((points.get(i + 1).getX() + 10) * perLength);
                int by = (int) ((points.get(i + 1).getY()));
                g.drawLine(ax, ay, bx, by);
                if (i == 0) {
                    bx = (int) ((points.get(points.size() - 1).getX() + 10) * perLength);
                    by = (int) ((points.get(points.size() - 1).getY()));
                    g.drawLine(ax, ay, bx, by);
                }
            }

            g.setColor(c);//不要改变原来的前景色
        }
    }

    public void paintRunway(Graphics g) {
        if (controller.isPaintRunway) {
            int ax = (int) trackBeginPoints.getX();
            int ay = (int) trackBeginPoints.getY();
            int bx = (int) trackEndPoints.getX();
            int by = (int) trackEndPoints.getY();

            Color c = g.getColor();
            g.setColor(Color.black);
            g.drawLine(ax, ay, bx, by);
            g.setColor(c);//不要改变原来的前景色
        }
    }

    @Override
    public void paint(Graphics g) {
        paintCoordinateAxis(g);
        paintData(g);
        paintRunway(g);
    }

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(WIDTH, HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();

        //擦除原画
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.white);
        gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);
        gOffScreen.setColor(c);

        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    private class PaintTread implements Runnable {

        @Override
        public void run() {
            while (true) {
                repaint();//外部包装类的repaint();方法
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
