package org.example;

import java.io.IOException;

public class ServerFactoryImpl implements ServerFactory {

    public ServerFactoryImpl() {

    }

    public void listen(int port, Object service) {
        try {
            ServerRoutine server = new ServerRoutine(port, service);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
