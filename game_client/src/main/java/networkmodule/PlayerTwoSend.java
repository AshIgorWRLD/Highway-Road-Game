package networkmodule;

import lombok.extern.slf4j.Slf4j;
import view.MainFrame;
import road.RoadLogic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Slf4j
public class PlayerTwoSend extends Thread {
    private static final int SEND_BUFFER_LEN = 3;

    private final RoadLogic roadLogic;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    private int[] sendBuffer;

    public PlayerTwoSend(RoadLogic roadLogic, DataInputStream dataInputStream,
                         DataOutputStream dataOutputStream) {
        this.roadLogic = roadLogic;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    private void send() throws IOException {
        if (roadLogic.getPlayer2CarSpamInfo()) {
            sendBuffer = roadLogic.getPlayer2Changes();
            for (int i = 0; i < SEND_BUFFER_LEN; i++) {
                dataOutputStream.writeInt(sendBuffer[i]);
            }
            dataOutputStream.writeBoolean(roadLogic.getPlayer2CarSpamInfo());
            roadLogic.setPlayer2CarSpamInfo(false);
        }
    }

    public void run() {
        MainFrame mainFrame = new MainFrame(false, roadLogic, "");
        boolean isActive = true;
        while (isActive) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                log.error("UNABLE TO SLEEP ACTIVE THREAD");
            }
            try {
                send();
            } catch (IOException e) {
                isActive = false;
            }
        }
    }
}