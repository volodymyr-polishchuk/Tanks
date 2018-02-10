package client;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Vladimir on 10/02/18.
 **/
public class GameDevClient {
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public GameDevClient(InetAddress address, int port) throws IOException, InterruptedException {
        socket = new Socket(address, port);
        System.out.println("Connection open");

        outputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new DataInputStream(socket.getInputStream());
    }

    public Tank[] sendToServer(Tank tank) throws IOException, InterruptedException {
        if (!socket.isClosed() || !socket.isInputShutdown() || !socket.isOutputShutdown()) {
            outputStream.writeUTF(tank.getData());
            outputStream.flush();
            String line = inputStream.readUTF();
            String[] lines = line.split(":");
            Tank[] tanks = new Tank[lines.length];
            for (int i = 0; i < lines.length; i++) {
                tanks[i] = Tank.getInstantsByData(lines[i]);
            }
            return tanks;
        }
        close();
        throw new ConnectException("Connection is close");
    }

    public void close() throws IOException {
        outputStream.close();
        inputStream.close();
        socket.close();
    }
}
