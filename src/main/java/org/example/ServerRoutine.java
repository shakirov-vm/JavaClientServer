package org.example;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerRoutine extends Thread {

    private ServerSocket serverSocket;
    Object service;

    public ServerRoutine(int port, Object service_) throws IOException {
        serverSocket = new ServerSocket(port);
        service = service_;
    }

    @Override
    public void run() {
        Socket socketToClient = null;
        try {
            socketToClient = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socketToClient.getInputStream());
                CallInfo o = (CallInfo) objectInputStream.readObject();

                Method method = service.getClass().getMethod(o.method, getParametersType(o));
                Object retVal = method.invoke(service, o.args);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketToClient.getOutputStream());
                objectOutputStream.writeObject(retVal);

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private Class<?>[] getParametersType(CallInfo info) {
        try {
            List<String> stringParameters = info.parameterNames;
            Class<?>[] classParameters = new Class<?>[stringParameters.size()];
            for (int i = 0; i < stringParameters.size(); i++) {
                classParameters[i] = Class.forName(stringParameters.get(i));
            }
            return classParameters;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
