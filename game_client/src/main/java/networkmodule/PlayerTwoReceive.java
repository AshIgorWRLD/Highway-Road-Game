package networkmodule;

import lombok.extern.slf4j.Slf4j;
import road.RoadLogic;
import view.MainFrame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Slf4j
public class PlayerTwoReceive extends Thread {
    private static final int RECEIVE_BUFFER_LEN = 6;
    private static final int X_POSITION = 0;
    private static final int Y_POSITION = 1;
    private static final int D_X_POSITION = 2;
    private static final int D_Y_POSITION = 3;
    private static final int CONDITION_IDX_POSITION = 4;
    private static final int ACCELERATION_POSITION = 5;

    private final RoadLogic roadLogic;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    private int[] receiveBuffer;

    public PlayerTwoReceive(RoadLogic roadLogic, DataInputStream dataInputStream,
                            DataOutputStream dataOutputStream) {
        this.roadLogic = roadLogic;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    private void receive() throws IOException {
        boolean isOver = dataInputStream.readBoolean();
        if (isOver) {
            log.info("YOUR OPPONENT LEFT");
            System.exit(0);
        }
        receiveBuffer = new int[RECEIVE_BUFFER_LEN];
        for (int i = 0; i < RECEIVE_BUFFER_LEN; i++) {
            receiveBuffer[i] = dataInputStream.readInt();
        }
        roadLogic.setPlayerX(receiveBuffer[X_POSITION]);
        roadLogic.setPlayerY(receiveBuffer[Y_POSITION]);
        roadLogic.setPlayerDX(receiveBuffer[D_X_POSITION]);
        roadLogic.setPlayerDY(receiveBuffer[D_Y_POSITION]);
        roadLogic.setPlayerConditionIdx(receiveBuffer[CONDITION_IDX_POSITION]);
        roadLogic.setPlayerAcceleration(receiveBuffer[ACCELERATION_POSITION]);
        roadLogic.changePlayerImg(roadLogic.getPlayerConditionIdx());
    }

    public void run() {
        boolean isActive = true;
        String carName = null;
        try {
            carName = dataInputStream.readUTF();
        } catch (IOException e) {
            log.error("UNABLE TO READ CAR TYPE");
        }
        roadLogic.setCarName(carName);
        roadLogic.addPlayer();
        PlayerTwoSend playerTwoSend = new PlayerTwoSend(roadLogic, dataInputStream,
                dataOutputStream);
        playerTwoSend.start();
        while (isActive) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                log.info("UNABLE TO SLEEP ACTIVE THREAD");
            }
            try {
                receive();
            } catch (IOException e) {
                isActive = false;
            }
        }
    }
}
