package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.*;
import java.net.Socket;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Handler;

public class ClientFactoryImpl implements ClientFactory {

    private Socket clientSocket;
    static class CustomHandler<T> implements InvocationHandler {

        private final T original;
        private Socket clientSocket;

        public CustomHandler(T original_, Socket clientSocket_) {
            original = original_;
            clientSocket = clientSocket_;
        }
        public Object invoke(Object proxy, Method method, Object[] args)
                throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            CallInfo toServer = new CallInfo(method.getName(), args);
            System.out.println("Client Object Created, name: " + method.getName());

            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                System.out.println("Client Stream Created");

                // java.io.NotSerializableException: java.lang.reflect.Method
                objectOutputStream.writeObject(toServer);
                System.out.println("Client Socket Object Writed");

//                objectOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {

                System.out.println("Client Socket Start To Read");

                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                System.out.println("Client Socket Input Stream created");
                Object o = objectInputStream.readObject();

                System.out.println("Client Socket Object Readed");
                System.out.println("Client Method type: " + method.getReturnType());
                System.out.println("Client Getted float: " + (double) o);
                return o;
            } catch (IOException e) {
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // method.invoke(original, args); NO HANDLER ON CLIENT, handler on server!!
            return null;
        }
    }

    public ClientFactoryImpl(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            System.out.println("Client Socket Getted");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public <T> T newClient(Class<T> client) {
        InvocationHandler handler = new <T>CustomHandler(client, clientSocket);
        T proxyedClientClass = (T) Proxy.newProxyInstance(client.getClassLoader(), new Class<?>[] { client }, handler);

//        System.out.println("Proxyed: " + proxyedClientClass);

        return proxyedClientClass;
    }
}
