package client;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

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
        System.out.println(Arrays.toString(sendToServer(new Tank("name", 1, 1, 1))));
    }

    public Tank[] sendToServer(Tank tank) throws IOException, InterruptedException {
        if (!socket.isClosed() || !socket.isInputShutdown() || !socket.isOutputShutdown()) {
            outputStream.writeUTF(tank.getData());
            outputStream.flush();
            String line = inputStream.readUTF();
            if (line.isEmpty()) return new Tank[0];
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

    public static void main(String[] args) throws IOException, InterruptedException {
        InetAddress address = InetAddress.getByName("localhost");
        GameDevClient client = new GameDevClient(address, 3345);
    }

    public void disconnect() {
        try {
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
