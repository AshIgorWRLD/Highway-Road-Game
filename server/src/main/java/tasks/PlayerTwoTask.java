package tasks;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Slf4j
public class PlayerTwoTask implements Runnable {
    private final static int WAITING_INFO_NUMBER = 3;
    private final static int NOTHING_CAME = 0;

    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final int[] comingInfo;

    public PlayerTwoTask(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.comingInfo = new int[WAITING_INFO_NUMBER];
    }

    private void sendReceive() throws IOException {
        boolean done = false;
        for (int i = 0; i < WAITING_INFO_NUMBER; i++) {
            comingInfo[i] = dataInputStream.readInt();
            if (comingInfo[i] != NOTHING_CAME) {
                done = true;
            }
        }
        boolean spammed = dataInputStream.readBoolean();
        if (done) {
            dataOutputStream.writeBoolean(false);
            for (int i = 0; i < WAITING_INFO_NUMBER; i++) {
                dataOutputStream.writeInt(comingInfo[i]);
            }
            dataOutputStream.writeBoolean(spammed);
        }
    }

    public void run() {
        boolean isActive = true;
        while (isActive) {
            try {
                sendReceive();
            } catch (IOException e) {
                isActive = false;
            }
        }
        log.info("PLAYER 2(SPAMMER) DISCONNECTED");
        try {
            dataOutputStream.writeBoolean(true);
        } catch (IOException ignored) {
        }
    }
}
