package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Objects;

public class MonoThreadClientHandler implements Runnable {

    private static Socket clientDialog;

    static HashMap<String, String> hashMap = new HashMap<>();

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

                String[] lines = entry.split("/");
                if (lines.length == 2) {
                    hashMap.put(lines[0], lines[1]);
                    name = lines[0];
                }
                String tLine = "";
                int count = 0;
                for(HashMap.Entry<String, String> item: hashMap.entrySet()) {
                    if (!item.getKey().equals(lines[0])) {
                        tLine += count == 0 ? "" : ":";
                        tLine += item.getKey() + "/" + item.getValue();
                        count++;
                    }
                }
                out.writeUTF(tLine);
                out.flush();
                Thread.sleep(3);
            }

            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");
            hashMap.remove(name);
            in.close();
            out.close();
            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}