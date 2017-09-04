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

    private List<util.Point> points = new ArrayList<util.Point>();

    public MainView(Controller controller) {
        this.controller = controller;

        for(int i = 0; i < controller.airPortMapModel.getPoints().size(); i++){
            util.Point point = new Point(controller.airPortMapModel.getPoints().get(i).getX(),controller.airPortMapModel.getPoints().get(i).getY());
            points.add(point);
        }
        points.forEach(point -> {
            System.out.println("x: " + point.getX() + " y: " + point.getY());
        });
        disposeData();
        launchFrame();
    }

    public void launchFrame() {
        setLocation(200, 100);
        setSize(WIDTH, HEIGHT);
        //0.2设置窗口属性
        setTitle("AirPortMap");
        //setResizable(false);//不让窗口改变大小
        this.setBackground(Color.black);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

    public void paintCoordinateAxis(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.white);

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
                if(points.get(i).getY() == j){
                    int value = yMap.get(j);
                    points.get(i).setY(value);
                }
            }
        }
        points.forEach(point -> {
            System.out.println("x: " + point.getX() + " y: " + point.getY());
        });
    }

    public void paintData(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.white);
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

    @Override
    public void paint(Graphics g) {
        paintCoordinateAxis(g);
        paintData(g);
    }


}
