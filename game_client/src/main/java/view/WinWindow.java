package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WinWindow extends JDialog {
    private ActionListener newGameListener;
    private ActionListener exitListener;
    private ActionListener recordsListener;
    private ActionListener switchCarListener;
    private String carName;
    private JLabel switchCarLabel;

    public WinWindow(JFrame owner, String carName) {
        super(owner, "Win", true);

        this.carName = carName;

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        contentPane.add(createWinLabel(layout));
        contentPane.add(createNewGameButton(layout));
        contentPane.add(createExitButton(layout));
        contentPane.add(switchCarLabel = createCarTypeLabel(layout));
        contentPane.add(createSwitchCarButton(layout));
        contentPane.add(createRecordsButton(layout));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 130));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public void setNewGameListener(ActionListener newGameListener) {
        this.newGameListener = newGameListener;
    }

    public void setExitListener(ActionListener exitListener) {
        this.exitListener = exitListener;
    }

    public void setRecordsListener(ActionListener recordsListener){
        this.recordsListener = recordsListener;
    }

    public void setSwitchCarListener(ActionListener switchCarListener){
        this.switchCarListener = switchCarListener;
    }

    private JLabel createWinLabel(GridBagLayout layout) {
        JLabel label = new JLabel("You win!");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        layout.setConstraints(label, gbc);
        return label;
    }

    private JButton createNewGameButton(GridBagLayout layout) {
        JButton newGameButton = new JButton("New game");
        newGameButton.setPreferredSize(new Dimension(100, 25));

        newGameButton.addActionListener(e -> {
            dispose();

            if (newGameListener != null) {
                newGameListener.actionPerformed(e);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 0, 0, 0);
        layout.setConstraints(newGameButton, gbc);

        return newGameButton;
    }

    private JButton createExitButton(GridBagLayout layout) {
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(100, 25));

        exitButton.addActionListener(e -> {
            dispose();

            if (exitListener != null) {
                exitListener.actionPerformed(e);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 5, 0, 0);
        layout.setConstraints(exitButton, gbc);

        return exitButton;
    }

    private JLabel createCarTypeLabel(GridBagLayout layout) {
        JLabel label = new JLabel(carName);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        layout.setConstraints(label, gbc);
        return label;
    }

    private JButton createSwitchCarButton(GridBagLayout layout){
        JButton switchCarButton = new JButton("Switch car");

        switchCarButton.addActionListener(e -> {
            dispose();

            if (switchCarListener != null) {
                switchCarListener.actionPerformed(e);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 5, 0, 0);
        layout.setConstraints(switchCarButton, gbc);
        return switchCarButton;
    }

    private JButton createRecordsButton(GridBagLayout layout){
        JButton recordsButton = new JButton("Records");

        recordsButton.addActionListener(e -> {
            dispose();

            if (recordsListener != null) {
                recordsListener.actionPerformed(e);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(15, 5, 0, 0);
        layout.setConstraints(recordsButton, gbc);
        return recordsButton;
    }
}
