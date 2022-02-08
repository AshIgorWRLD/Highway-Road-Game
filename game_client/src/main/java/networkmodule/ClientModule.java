package networkmodule;

import lombok.extern.slf4j.Slf4j;
import road.RoadLogic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Slf4j
public class ClientModule extends Thread {
    private final RoadLogic roadLogic;

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ClientModule(String carName) {
        roadLogic = new RoadLogic(true, carName);
    }

    public void connectSpammer(String ip, int port, String name) {
        try {
            boolean isReady = false;
            socket = new Socket(ip, port);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream.writeInt(2);
            while (!isReady) {
                boolean readinessAnswer = dataInputStream.readBoolean();
                if (readinessAnswer) {
                    isReady = true;
                }
            }
            PlayerTwoReceive playerTwoReceive = new PlayerTwoReceive(roadLogic, dataInputStream,
                    dataOutputStream);
            playerTwoReceive.start();
            log.info("SUCCESSFULLY CONNECTED TO THE SERVER");
        } catch (IOException e) {
            log.info("UNABLE TO CONNECT TO THE SERVER");
        }
    }

    public void connectDriver(String ip, int port, String name) {
        try {
            boolean isReady = false;
            socket = new Socket(ip, port);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream.writeInt(1);
            while (!isReady) {
                boolean readinessAnswer = dataInputStream.readBoolean();
                if (readinessAnswer) {
                    isReady = true;
                }
            }
            PlayerOneSend playerOneSend = new PlayerOneSend(roadLogic, dataInputStream,
                    dataOutputStream, name);
            playerOneSend.start();
            PlayerOneReceive playerOneReceive = new PlayerOneReceive(roadLogic, dataInputStream,
                    dataOutputStream);
            playerOneReceive.start();
            log.info("SUCCESSFULLY CONNECTED TO THE SERVER");
        } catch (IOException e) {
            log.info("UNABLE TO CONNECT TO THE SERVER");
        }
    }
}
