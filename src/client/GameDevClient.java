package client;

import javax.swing.*;
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
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;

    public GameDevClient(InetAddress address, int port) throws IOException, InterruptedException {
        socket = new Socket(address, port);
        System.out.println("Connection open");

        outputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new DataInputStream(socket.getInputStream());
    }

    public Tank[] sendToServerTank(Tank tank) throws IOException, InterruptedException {
        synchronized (outputStream) {
            synchronized (inputStream) {
                if (!socket.isClosed()) {
                    outputStream.writeUTF("tank " + tank.getData());
                    outputStream.flush();
                    String line = null;
                    try {
                        line = inputStream.readUTF();
                    } catch (EOFException e) {
                        e.printStackTrace();
                        close();
                    }
                    if (line == null || line.isEmpty()) return new Tank[0];
                    String[] lines = line.split(":");
                    Tank[] tanks = new Tank[lines.length];
                    for (int i = 0; i < lines.length; i++) {
                        tanks[i] = Tank.getInstantsByData(lines[i]);
                    }
                    return tanks;
                }
            }
        }
        close();
        JOptionPane.showMessageDialog(null, "Connection close", "Server error", JOptionPane.WARNING_MESSAGE);
        throw new ConnectException("Connection is close");
    }

    public void sendToServerBullet(Bullet bullet) throws IOException {
        synchronized (outputStream) {
            synchronized (inputStream) {
                if (!socket.isClosed()) {
                    outputStream.writeUTF("bullet " + bullet.getData());
                    outputStream.flush();
                    try {
                        inputStream.readUTF();
                    } catch (EOFException e) {
                        e.printStackTrace();
                        close();
                    }
                    return;
                }
            }
        }
        close();
        JOptionPane.showMessageDialog(null, "Connection close", "Server error", JOptionPane.WARNING_MESSAGE);
        throw new ConnectException("Connection is close");
    }

    public Bullet[] getFromServerBullets() throws IOException {
        synchronized (outputStream) {
            synchronized (inputStream) {
                if (!socket.isClosed()) {
                    outputStream.writeUTF("bullets");
                    outputStream.flush();
                    String line = null;
                    try {
                        line = inputStream.readUTF();
                    } catch (EOFException e) {
                        e.printStackTrace();
                        close();
                    }
                    if (line == null || line.isEmpty()) return new Bullet[0];
                    String[] lines = line.split(":");
                    Bullet[] bullets = new Bullet[lines.length];
                    for (int i = 0; i < lines.length; i++) {
                        bullets[i] = Bullet.getInstantsByData(lines[i]);
                    }
                    return bullets;
                }
            }
        }
        close();
        JOptionPane.showMessageDialog(null, "Connection close", "Server error", JOptionPane.WARNING_MESSAGE);
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
