package server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@AllArgsConstructor
@Getter
@Setter
public class MySocket {
    private int type;
    private Socket socket;

    @SneakyThrows
    public InputStream getInputStream() {
        return socket.getInputStream();
    }

    @SneakyThrows
    public OutputStream getOutputStream() {
        return socket.getOutputStream();
    }
}
