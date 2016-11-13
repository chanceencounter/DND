package com.ericarao.dnd.core;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    //Variables
    private final Socket socket;

    //
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(BufferedReader input = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream()))) {
            System.out.println("Client handler started on thread: " +
                    Thread.currentThread().getName());
            String clientInput;
            while ((clientInput = input.readLine()) != null) {
                System.out.println(String.format("%s:%d - %s",
                        socket.getInetAddress().getHostName(), socket.getPort(), clientInput));
                output.write("Server Received Message: " + clientInput);
                output.newLine();
                output.flush();
            }
        } catch (IOException e) {
            System.out.println("Encountered exception while handling client. " + e.getMessage());
        }

    }
}
