package view;

import lombok.Getter;
import lombok.Setter;
import road.Road;
import road.RoadLogic;

import javax.swing.*;

public class MainFrame extends JFrame{
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 655;

    @Getter
    @Setter
    private String carName;

    public MainFrame(boolean isDriver, RoadLogic roadLogic, String name){
        super("Race");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        getContentPane().removeAll();
        add(new Road(isDriver, roadLogic, name));
        repaint();
        setVisible(true);
    }
}
