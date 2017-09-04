package view;

import controller.Controller;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by think on 2017/9/4.
 */
public class ControllerView extends Frame{
    Controller controller;
    public static final int HEIGHT = 800;
    public static final int WIDTH = 800;

    public ControllerView(Controller controller) {
        this.controller = controller;
        launchFrame();
    }

    public void launchFrame() {
        setLocation(200, 100);
        setSize(WIDTH, HEIGHT);
        setTitle("AirPortInputView");
        this.setBackground(Color.black);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }
}
