package com.ericarao.dnd.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkServer {

    //Variables
    private final int port;
    private final int playerCount;

    /**
     * Constructor
     */
    public NetworkServer(int port, int playerCount) {
        this.port = port;
        this.playerCount = playerCount + 1;
    }

    public void run() {

        /**
         * The executor allows the work of communication with clients
         * to be handed off to other threads leaving the server available
         * to accept new connections
         */
        ExecutorService executorService = null;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            executorService = Executors.newFixedThreadPool(playerCount);
            while(true) {
                Socket client = serverSocket.accept();
                executorService.execute(new ClientHandler(client));
            }
        } catch (IOException e) {
            System.out.println("Could not start server. " + e.getMessage());
            throw new RuntimeException(e);
        } finally {

        }
    }
}
