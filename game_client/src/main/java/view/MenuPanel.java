package view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel{

    @Getter
    @Setter
    private JPanel panel;

    private JButton playButton;
    private JButton playOnlineButton;
    private JButton chooseCarButton;
    private JButton localRecordsButton;
    private JButton infoButton;
    private JButton exitButton;

    public MenuPanel(int windowWidth, int windowHeight){
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(windowWidth, windowHeight));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        playButton = new JButton("Play");
        gbc.ipadx = 66;
        panel.add(playButton, gbc);
        gbc.gridx = 1;
        playOnlineButton = new JButton("Play online");
        gbc.ipadx = 56;
        panel.add(playOnlineButton, gbc);
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 1;
        chooseCarButton = new JButton("Choose car");
        gbc.ipadx = 200;
        panel.add(chooseCarButton, gbc);
        gbc.gridy = 2;
        localRecordsButton = new JButton("Local records");
        gbc.ipadx = 188;
        panel.add(localRecordsButton, gbc);
        gbc.gridy = 3;
        infoButton = new JButton("Information");
        gbc.ipadx = 200;
        panel.add(infoButton, gbc);
        gbc.gridy = 4;
        exitButton = new JButton("Exit");
        gbc.ipadx = 244;
        panel.add(exitButton, gbc);
    }

    public void changeVisibility(boolean value){
        panel.setVisible(value);
    }

    public void switchToOnlineMode(){

    }

    public void addPlayButtonActionListener(ActionListener actionListener){
        playButton.addActionListener(actionListener);
    }



}
