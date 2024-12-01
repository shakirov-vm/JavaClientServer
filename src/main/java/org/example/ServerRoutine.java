package org.example;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRoutine extends Thread {

    private ServerSocket serverSocket;

    public ServerRoutine(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server Socket Getted");
    }

    @Override
    public void run() {
        //while (true) {
            try {
                final Socket socketToClient = serverSocket.accept();
                System.out.println("Server Socket Accepted");
                try {
                    System.out.println("Server Socket Start To Read");

                    ObjectInputStream objectInputStream = new ObjectInputStream(socketToClient.getInputStream());
                    System.out.println("Server Socket Input Stream created");
                    CallInfo o = (CallInfo) objectInputStream.readObject();

                    System.out.println("Server Socket Object Readed");
                    System.out.println("Method name: " + o.getMethod());
                    System.out.println("Read object: " + o);


                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketToClient.getOutputStream());
                    System.out.println("Server Stream Created");

                    // java.io.NotSerializableException: java.lang.reflect.Method
                    objectOutputStream.writeObject((double) 1.0f);
                    System.out.println("Server Socket Object Writed");
                } catch (IOException e) {
                    e.printStackTrace();

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        //}
    }
}
