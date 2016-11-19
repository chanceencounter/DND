package com.ericarao.dnd.core;

import com.ericarao.dnd.core.model.ClientUpdate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class NetworkClient {

    //Variables
    private final String hostName;
    private final int port;
    private final Consumer<ClientUpdate> clientUpdateHandler;

    public NetworkClient(String hostName, int port, Consumer<ClientUpdate> clientUpdateHandler) {
        this.hostName = hostName;
        this.port = port;
        this.clientUpdateHandler = clientUpdateHandler;
    }


    public void run() {
        try(Socket clientSocket = new Socket(hostName, port);
            PrintWriter writeServer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader readServer = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            BufferedReader readUser = new BufferedReader(new InputStreamReader(System.in))) {
            //
            String userInput;
            while ((userInput = readUser.readLine()) != null) {
                writeServer.println(userInput);
                System.out.println("Recieved: " + readServer.readLine());
            }
        } catch (IOException e) {
            System.out.println("Encountered exception: " + e.getMessage());
        }
    }
}