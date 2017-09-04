package controller;

import model.AirPortMapModel;
import model.CalculateModel;
import util.Point;
import view.ControllerView;
import view.MainView;

import java.util.Scanner;

/**
 * Created by think on 2017/7/3.
 */
public class Controller {
    public AirPortMapModel airPortMapModel;
    public CalculateModel calculateModel;
    public MainView mainView;
    public ControllerView controllerView;

    public void action() {
        int n;

        airPortMapModel = new AirPortMapModel();


        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            Point p = new Point(x, y);
            airPortMapModel.getPoints().add(p);
        }
        mainView = new MainView(this);
        controllerView = new ControllerView(this);

        calculateModel = new CalculateModel(this);
        //calculateModel.calculateEveryDistance();
        calculateModel.calculateAll();

        //airPortMapData.readPoints();
    }

    public static void main(String[] args) {
        new Controller().action();
    }
}
