package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.*;
import java.net.Socket;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
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
        public Object invoke(Object proxy, Method method, Object[] args) throws IllegalArgumentException {

            Class<?>[] parameters = method.getParameterTypes();
            List<String> stringParameters = new ArrayList<String>();
            for (Class<?> cls : parameters) {
                stringParameters.add(cls.getName());
            }
            CallInfo toServer = new CallInfo(method.getName(), stringParameters, args);

            method.getParameterTypes();

            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectOutputStream.writeObject(toServer);

                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                Object o = objectInputStream.readObject();
//                objectOutputStream.close();

                return o;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public ClientFactoryImpl(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public <T> T newClient(Class<T> client) {
        InvocationHandler handler = new <T>CustomHandler(client, clientSocket);
        T proxyedClientClass = (T) Proxy.newProxyInstance(client.getClassLoader(), new Class<?>[] { client }, handler);

        return proxyedClientClass;
    }
}
