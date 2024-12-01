package org.example;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRoutine extends Thread {

    private ServerSocket serverSocket;
    Object service;

    public ServerRoutine(int port, Object service_) throws IOException {
        serverSocket = new ServerSocket(port);
        service = service_;
    }

    @Override
    public void run() {
        //while (true) {
            try {
                final Socket socketToClient = serverSocket.accept();
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(socketToClient.getInputStream());
                    CallInfo o = (CallInfo) objectInputStream.readObject();

                    try {
                        Method method = service.getClass().getMethod(o.method, Person.class);
                        Object retVal = method.invoke(service, o.args);

                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketToClient.getOutputStream());
                        objectOutputStream.writeObject(retVal);

                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }

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
