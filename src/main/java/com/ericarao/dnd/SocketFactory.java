package com.ericarao.dnd;

import java.io.IOException;
import java.net.Socket;

public class SocketFactory {
    public Socket create(String hostName, int port) {
        try {
            return new Socket(hostName, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
