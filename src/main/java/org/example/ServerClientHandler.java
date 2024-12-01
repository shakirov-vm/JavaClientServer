package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerClientHandler extends Thread {
    private Socket socket;
    ObjectInputStream inputStream;

    ServerClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        //while (true) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                CallInfo o = (CallInfo) objectInputStream.readObject();

                System.out.println("Server Socket Object Readed");
                System.out.println("Method name: " + o.getMethod());
                System.out.println("Read object: " + o);
            } catch (IOException e) {
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        //}
    }
}
