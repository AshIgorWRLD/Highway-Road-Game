package networkmodule;

import lombok.extern.slf4j.Slf4j;
import road.RoadLogic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Slf4j
public class PlayerOneReceive extends Thread {
    private static final int RECEIVE_BUFFER_LEN = 3;
    private static final int INDEX_POSITION = 0;
    private static final int SPEED_POSITION = 1;
    private static final int ENEMY_SPAWN_Y_POSITION = 2;

    private final RoadLogic roadLogic;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    private int[] receiveBuffer;

    public PlayerOneReceive(RoadLogic roadLogic, DataInputStream dataInputStream,
                            DataOutputStream dataOutputStream) {
        this.roadLogic = roadLogic;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    private void receive() throws IOException {
        boolean isOver = dataInputStream.readBoolean();
        if (isOver) {
            roadLogic.setDeactivateFabric(false);
        }
        receiveBuffer = new int[RECEIVE_BUFFER_LEN];
        for (int i = 0; i < RECEIVE_BUFFER_LEN; i++) {
            receiveBuffer[i] = dataInputStream.readInt();
        }
        boolean isSpammed = dataInputStream.readBoolean();
        roadLogic.setIndex(receiveBuffer[INDEX_POSITION]);
        roadLogic.setSpeed(receiveBuffer[SPEED_POSITION]);
        roadLogic.setEnemySpawnY(receiveBuffer[ENEMY_SPAWN_Y_POSITION]);
        roadLogic.setSpammerActive(isSpammed);
    }

    public void run() {
        boolean isActive = true;
        while (isActive) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                log.error("UNABLE TO SLEEP ACTIVE THREAD");
            }
            try {
                receive();
            } catch (IOException e) {
                roadLogic.setDeactivateFabric(false);
                roadLogic.setPlayer2Active(false);
                isActive = false;
            }
        }
    }
}
