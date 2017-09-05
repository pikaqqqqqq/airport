package controller;

import model.AirPortMapModel;
import model.CalculateModel;
import view.ControllerView;
import view.MainView;

/**
 * Created by think on 2017/7/3.
 */
public class Controller {
    public boolean isPaintRunway = false;
    public boolean isPaintMap = true;
    public AirPortMapModel airPortMapModel;
    public CalculateModel calculateModel;
    public MainView mainView;
    public ControllerView controllerView;

    public void action() {

        airPortMapModel = new AirPortMapModel();
        controllerView = new ControllerView(this);

        calculateModel = new CalculateModel(this);
        mainView = new MainView(this, airPortMapModel.getTrackBeginPoints(), airPortMapModel.getTrackEndPoints());

    }

    public static void main(String[] args) {
        new Controller().action();
    }
}
