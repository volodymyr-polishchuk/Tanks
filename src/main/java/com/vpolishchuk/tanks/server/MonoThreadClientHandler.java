package com.vpolishchuk.tanks.server;

import com.vpolishchuk.tanks.client.Bullet;
import com.vpolishchuk.tanks.client.Tank;
import com.sun.javafx.geom.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

public class MonoThreadClientHandler implements Runnable {

    private static Socket clientDialog;

    public static final HashSet<Tank> tanks = new HashSet<>();
    public static final HashSet<Bullet> bullets = new HashSet<>();

    public MonoThreadClientHandler(Socket client) {
        MonoThreadClientHandler.clientDialog = client;
    }

    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            System.out.println("DataInputStream created");
            System.out.println("DataOutputStream  created");
            String name = "";
            while (!clientDialog.isClosed()) {
//                System.out.println("Server reading from channel");
                String entry;
                try {
                    entry = in.readUTF();
                } catch (SocketException | EOFException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Connection drop...");
                    break;
                }

//                System.out.println("READ from clientDialog message - " + entry);
                String[] args = entry.split(" ");
                String result = "";
                switch (args[0]) {
                    case "tank": {
                        synchronized (tanks) {
                            Tank tank1 = Tank.getInstantsByData(args[1]);
                            tanks.stream().filter(tank -> tank.equals(tank1)).forEach(tank -> {
                                tank1.setHealth(tank.getHealth());
                            });
                            if (tanks.contains(tank1)) {
                                tanks.remove(tank1);
                            }
                            tanks.add(tank1);
                            name = tank1.getName();
                            for (Tank tank : tanks) {
                                result += tank.getData() + ":";
                            }
                            result = result.substring(0, result.length() - 1);
                        }
                    } break;
                    case "bullet": {
                        synchronized (bullets) {
                            bullets.add(Bullet.getInstantsByData(args[1]));
                        }
                    } break;
                    case "bullets": {
                        synchronized (bullets) {
                            synchronized (tanks) {
                                if (bullets.isEmpty()) break;
                                HashSet<Bullet> tBullets = new HashSet<>();
                                for (Bullet bullet : bullets) {
                                    bullet.update();
                                    tanks.stream()
                                            .filter(tank -> tank.intersect(new Point(bullet.getX(), bullet.getY())))
                                            .forEach(tank -> {
                                                tank.doHit();
                                                bullet.die();
                                            });
                                    if (bullet.isDie()) tBullets.add(bullet);
                                }
                                bullets.removeAll(tBullets);
                                for (Bullet bullet : bullets) {
                                    result += bullet.getData() + ":";
                                }
                                if (!bullets.isEmpty())
                                    result = result.substring(0, result.length() - 1);
                            }
                        }
                    } break;
                }

                out.writeUTF(result);
                out.flush();
            }

            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");
            tanks.remove(new Tank(name, 0, 0, 0));
            in.close();
            out.close();
            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
