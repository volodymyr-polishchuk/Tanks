package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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

            while (!clientDialog.isClosed()) {
                System.out.println("Server reading from channel");
                String entry = null;
                try {
                    entry = in.readUTF();
                } catch (SocketException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Connection drop...");
                    break;
                }

//                System.out.println("READ from clientDialog message - " + entry);

                String[] lines = entry.split("/");
                if (lines.length == 2)
                    hashMap.put(lines[0], lines[1]);
                String tLine = "";
                int count = hashMap.size();
                for(HashMap.Entry<String, String> item: hashMap.entrySet()) {
                    count--;
                    tLine += item.getKey() + "/" + item.getValue();
                    tLine += count > 0 ? ":" : "";
                }
                out.writeUTF(tLine);
                System.out.println("Send to client (" + clientDialog.getLocalPort() + ") -> " + tLine);
                out.flush();
                Thread.sleep(10);
//                if (entry.equalsIgnoreCase("quit")) {
//                    System.out.println("Client initialize connections suicide ...");
//                    out.writeUTF("Server reply - " + entry + " - OK");
//                    Thread.sleep(3000);
//                    break;
//                }
//
//                System.out.println("Server try writing to channel");
//                out.writeUTF("Server reply - " + entry + " - OK");
//                System.out.println("Server Wrote message to clientDialog.");
//                out.flush();
//                Thread.sleep(100);
            }

            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            in.close();
            out.close();
            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}