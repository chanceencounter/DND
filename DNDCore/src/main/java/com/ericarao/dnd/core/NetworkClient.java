package com.ericarao.dnd.core;

import com.ericarao.dnd.core.model.NetworkPacket;
import com.ericarao.dnd.core.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class NetworkClient {

    //Variables
    private final String hostName;
    private final int port;
    private final Consumer<NetworkPacket> networkPacketConsumer;
    private final ConcurrentLinkedQueue<NetworkPacket> threadSafeOutboundMsgQueue = new ConcurrentLinkedQueue<>();
    private volatile boolean shouldStop = false;


    public NetworkClient(String hostName, int port, Consumer<NetworkPacket> networkPacketConsumer) {
        this.hostName = hostName;
        this.port = port;
        this.networkPacketConsumer = networkPacketConsumer;
    }


    public void run() {
        try(Socket clientSocket = new Socket(hostName, port);
            PrintWriter writeServer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader readServer = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()))) {
            while (!shouldStop) {
                if (clientSocket.getInputStream().available() > 0) {
                    processServerData(readServer.readLine());
                }
                NetworkPacket outboundPacket;
                if ((outboundPacket = threadSafeOutboundMsgQueue.poll()) != null) {
                    martialPacket(outboundPacket).ifPresent(writeServer::write);
                }
            }
        } catch (IOException e) {
            System.out.println("Encountered exception: " + e.getMessage());
        }
    }

    public void shutDown() {
        shouldStop = true;
    }

    public void enqueueNetworkPacket(NetworkPacket networkPacket) {
        threadSafeOutboundMsgQueue.add(networkPacket);
    }

    private Optional<String> martialPacket(NetworkPacket networkPacket) {
        try {
            return Optional.of(JsonUtils.MAPPER.writeValueAsString(networkPacket));
        } catch (JsonProcessingException e) {
            System.out.println("Encountered exception: " + e.getMessage());
            return Optional.empty();
        }
    }

    private void processServerData(String data) {
        try {
            networkPacketConsumer.accept(JsonUtils.MAPPER.readValue(data, NetworkPacket.class));
        } catch (Exception e) {
            System.out.println("Encounter Exception, reading data from server: " + e);
        }
    }
}