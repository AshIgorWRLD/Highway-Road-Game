package networkmodule;

import lombok.extern.slf4j.Slf4j;
import view.MainFrame;
import road.RoadLogic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Slf4j
public class PlayerOneSend extends Thread {
    private static final int SEND_BUFFER_LEN = 6;

    private final RoadLogic roadLogic;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final String playerName;

    private int[] sendBuffer;

    public PlayerOneSend(RoadLogic roadLogic, DataInputStream dataInputStream,
                         DataOutputStream dataOutputStream, String playerName) {
        this.roadLogic = roadLogic;
        this.roadLogic.addPlayer();
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.playerName = playerName;
    }

    private void send() throws IOException {
        sendBuffer = roadLogic.getPlayerChanges();
        for (int i = 0; i < SEND_BUFFER_LEN; i++) {
            dataOutputStream.writeInt(sendBuffer[i]);
        }
    }

    public void run() {
        boolean isActive = true;
        try {
            dataOutputStream.writeUTF(roadLogic.getCarName());
        } catch (IOException e) {
            log.error("UNABLE TO SEND CAR TYPE");
        }
        MainFrame raceLayout = new MainFrame(true, roadLogic, playerName);
        while (isActive) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                log.error("UNABLE TO SLEEP ACTIVE THREAD");
            }
            try {
                send();
            } catch (IOException e) {
                roadLogic.setDeactivateFabric(false);
                roadLogic.setPlayer2Active(false);
                isActive = false;
            }
        }
    }
}