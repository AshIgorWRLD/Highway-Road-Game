package road;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import recordtable.RecordTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

@Getter
@Setter
@Slf4j
public class Road extends JPanel implements ActionListener {
    private static final int TIMER_DELAY = 40;
    private static final int PLAYER_DRIVER = 1;
    private static final int MAX_REAL_SPEED = 200;
    private static final int PLAYER_SPEED_INFO_X_COORDINATE = 50;
    private static final int PLAYER_SCORE_INFO_X_COORDINATE = 300;
    private static final int PLAYER_CHOOSING_CAR_INFO_X_COORDINATE = 500;
    private static final int PLAYER_INFO_Y_COORDINATE = 30;
    private static final int LAYOUT_Y = 0;

    private final Image img;
    private final RoadLogic roadLogic;
    private final Timer mainTimer;
    private final Font font;
    private boolean isDriver;
    private String playerName;

    public Road(boolean isDriver, RoadLogic roadLogic, String playerName) {
        this.img = new ImageIcon("src\\main\\resources\\images\\game_road.png").getImage();
        this.roadLogic = roadLogic;
        this.mainTimer = new Timer(TIMER_DELAY, this);
        this.font = new Font("Times New Roman", Font.ITALIC, 20);
        this.isDriver = isDriver;
        this.mainTimer.start();
        this.playerName = playerName;
        if (this.isDriver) {
            addKeyListener(new MyKeyAdapter());
        } else {
            addKeyListener(new MyKeyAdapter2());
            this.roadLogic.addPlayer2();
        }
        setFocusable(true);
    }

    public void changeFocusing(boolean value) {
        setFocusable(value);
        requestFocusInWindow();
    }

    public void paint(Graphics g) {
        g = (Graphics2D) g;
        g.drawImage(img, roadLogic.getPlayerLayer1Position(), LAYOUT_Y, null);
        g.drawImage(img, roadLogic.getPlayerLayer2Position(), LAYOUT_Y, null);
        if (isDriver) {
            double playerSpeed = (MAX_REAL_SPEED / (roadLogic.getPlayerMaxSpeed() * 2)) *
                    roadLogic.getPlayerSpeed() * 2;
            g.setFont(font);
            g.drawString("Speed: " + playerSpeed + " km/h", PLAYER_SPEED_INFO_X_COORDINATE,
                    PLAYER_INFO_Y_COORDINATE);
            g.drawString("Your Score: " + roadLogic.getPlayerKmRes() / 2 + " km",
                    PLAYER_SCORE_INFO_X_COORDINATE, PLAYER_INFO_Y_COORDINATE);
        } else {
            g.setFont(font);
            g.drawString("Car Type: " + roadLogic.getPlayer2CarName() + "  Line: " +
                            roadLogic.getPlayer2LineNumber(), PLAYER_CHOOSING_CAR_INFO_X_COORDINATE,
                    PLAYER_INFO_Y_COORDINATE);
        }
        roadLogic.takeRoadChanges();
        DrawPriorityLine(g);
    }

    private void TestCollisionsWithEnemies() throws IOException {
        if (roadLogic.testCollisionsWithEnemies()) {
            if (isDriver) {
                JOptionPane.showMessageDialog(null, "YOU LOST");
                RecordTable recordTable = new RecordTable();
                recordTable.refreshRecordTable(playerName, roadLogic.getPlayerKmRes() / 2);
            } else {
                JOptionPane.showMessageDialog(null, "YOU WON!");
            }
            System.exit(0);
        }
    }

    public void DrawPriorityLine(Graphics g) {
        g = (Graphics2D) g;
        roadLogic.sortEnemies();
        boolean isPlayerDrawn = false;
        try {
            for (Enemy enemy : roadLogic.getEnemyList()) {
                if (enemy.getPriority() > roadLogic.getPlayerPriority() && !isPlayerDrawn) {
                    g.drawImage(roadLogic.getPlayerImg(), roadLogic.getPlayerX(),
                            roadLogic.getPlayerY(), null);
                    isPlayerDrawn = true;
                }
                g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), null);
            }
            if (!isPlayerDrawn) {
                g.drawImage(roadLogic.getPlayerImg(), roadLogic.getPlayerX(),
                        roadLogic.getPlayerY(), null);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        roadLogic.roadIteration();
        repaint();
        try {
            TestCollisionsWithEnemies();
        } catch (IOException ioException) {
            log.error("GAME CRASHED", e);
        }
    }

    private class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent event) {
            roadLogic.pressPlayerKey(event);
        }

        public void keyReleased(KeyEvent event) {
            roadLogic.releasePlayerKey(event);
        }

    }

    private class MyKeyAdapter2 extends KeyAdapter {
        public void keyPressed(KeyEvent event) {
            roadLogic.pressPlayer2Key(event);
        }
    }
}