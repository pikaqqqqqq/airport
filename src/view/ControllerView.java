package view;

import controller.Controller;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import util.Point;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2017/9/4.
 */
public class ControllerView extends Frame {
    Controller controller;
    public static final int HEIGHT = 300;
    public static final int WIDTH = 500;

    private String nString = "请输入坐标总数:";
    private String pointString = "请输入坐标:";
    private String painString = "绘图";
    private String measureString = "测量";
    private String clearString = "清空";

    private JLabel nLab;
    private JLabel pointLab;
    private JTextField nTxf;
    private JTextArea pointArea;
    private JButton painBtn;
    private JButton measureBtn;
    private JButton clearBtn;

    private BtnListener btnListener = new BtnListener();


    public ControllerView(Controller controller) {
        this.controller = controller;
        launchFrame();
    }

    public void launchFrame() {
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();

            UIManager.put("RootPane.setupButtonVisible", false);
            nLab = new JLabel(nString);
            pointLab = new JLabel(pointString);
            nTxf = new JTextField(5);
            pointArea = new JTextArea(3, 4);
            painBtn = new JButton(painString);
            measureBtn = new JButton(measureString);
            clearBtn = new JButton(clearString);
            pointArea.setBorder(new LineBorder(new Color(68, 109, 153), 2, false));

            nTxf.setFocusable(true);
            nTxf.setFont(new Font("微软雅黑", Font.BOLD, 14));
            nLab.setFont(new Font("微软雅黑", Font.BOLD, 14));
            pointLab.setFont(new Font("微软雅黑", Font.BOLD, 14));
            measureBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
            clearBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
            painBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
            measureBtn.addActionListener(btnListener);
            painBtn.addActionListener(btnListener);
            clearBtn.addActionListener(btnListener);

            setLocation(300, 200);
            setTitle("AirPortInputView");
            this.setSize(WIDTH, HEIGHT);

            JPanel p = new JPanel(new BorderLayout(5, 5));
            JPanel p1 = new JPanel(new GridLayout(1, 2, 10, 10));
            JPanel p2 = new JPanel(new GridLayout(1, 2, 10, 10));
            JPanel p3 = new JPanel(new GridLayout(1, 3, 50, 50));

            p.setBackground(new Color(255, 255, 255));
            p1.setBackground(new Color(255, 255, 255));
            p2.setBackground(new Color(255, 255, 255));
            p3.setBackground(new Color(255, 255, 255));
            p1.setBorder(BorderFactory.createEmptyBorder(10, 30, 5, 20));
            p2.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 20));
            p3.setBorder(BorderFactory.createEmptyBorder(5, 50, 10, 50));

            p1.add(nLab);
            p1.add(nTxf);
            p2.add(pointLab);
            p2.add(pointArea);
            p3.add(painBtn);
            p3.add(measureBtn);
            p3.add(clearBtn);

            p.add(p1, "North");
            p.add(p2, "Center");
            p.add(p3, "South");

            add(p);

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disposeString(String pointString) {
        String[] ss = pointString.split(" ");
        String[] sss = null;
        List<String> pointList = new ArrayList<String>();
        for (int i = 0; i < ss.length; i++) {
            if (ss[i].indexOf("\n") == -1) {
                System.out.println("不包含");
                pointList.add(ss[i]);
            } else {
                sss = ss[i].split("\n");
                pointList.add(sss[0]);
                pointList.add(sss[1]);
            }
        }
        for (int i = 1; i < pointList.size(); i++) {
            if (i % 2 != 0) {
                util.Point point = new Point();
                point.setX(Double.parseDouble(pointList.get(i - 1)));
                point.setY(Double.parseDouble(pointList.get(i)));
                controller.airPortMapModel.getPoints().add(point);
            }
        }
        controller.airPortMapModel.readPoints();
    }

    private class BtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == painBtn) {
                if (nTxf.getText().trim().length() == 0 || pointArea.getText().trim().length() == 0) {
                    JOptionPane.showMessageDialog(null, "请输入完整坐标信息", "【出错啦】", JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("绘制");
                    controller.isPaintMap = true;
                    String nString = nTxf.getText();
                    String pointString = pointArea.getText();
                    disposeString(pointString);
                    controller.mainView.updateMapData();
                    controller.calculateModel.updateMapData();
                }
            }
            if (e.getSource() == measureBtn) {
                System.out.println("测量");
                controller.isPaintRunway = true;
                controller.calculateModel.calculateAll();
                controller.mainView.updateRunwayData(controller.airPortMapModel.getTrackBeginPoints(),
                        controller.airPortMapModel.getTrackEndPoints());
            }
            if (e.getSource() == clearBtn) {
                System.out.println("清空");
                controller.isPaintRunway = false;
                controller.isPaintMap = false;
                controller.airPortMapModel.getPoints().clear();
            }
        }
    }
}
