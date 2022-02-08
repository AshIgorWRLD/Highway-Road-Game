package tasks;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class PlayerOneTask implements Runnable {
    private final static int WAITING_INFO_NUMBER = 6;
    private final static int NOTHING_CAME = 0;

    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final ArrayList<Boolean> readyList;
    private final int ownNumber;
    private final int[] comingInfo;

    public PlayerOneTask(DataInputStream dataInputStream, DataOutputStream dataOutputStream,
                         ArrayList<Boolean> readyList, int ownNumber) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.readyList = readyList;
        this.ownNumber = ownNumber;
        this.comingInfo = new int[WAITING_INFO_NUMBER];
    }

    private void initialSendReceive(){
        try {
            String carType = dataInputStream.readUTF();
            dataOutputStream.writeUTF(carType);
        } catch (IOException e) {
            log.error("UNABLE TO TRANSPORT CAR TYPE");
        }
    }

    private void sendReceive() throws IOException {
        boolean done = false;
        for (int i = 0; i < WAITING_INFO_NUMBER; i++) {
            comingInfo[i] = dataInputStream.readInt();
            if (comingInfo[i] != NOTHING_CAME) {
                done = true;
            }
        }
        if (done) {
            dataOutputStream.writeBoolean(false);
            for (int i = 0; i < WAITING_INFO_NUMBER; i++) {
                dataOutputStream.writeInt(comingInfo[i]);
            }
        }
    }

    public void run() {
        boolean isActive = true;
        initialSendReceive();
        while (isActive) {
            try {
                sendReceive();
            } catch (IOException e) {
                isActive = false;
            }
        }
        readyList.set(ownNumber, true);
        log.info("PLAYER 1(DRIVER) DISCONNECTED");
        try {
            dataOutputStream.writeBoolean(true);
        } catch (IOException ignored) {
        }
    }
}
