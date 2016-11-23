package com.ericarao.dnd.core;

import com.ericarao.dnd.core.model.NetworkPacket;
import com.ericarao.dnd.core.utils.JsonUtils;
import com.ericarao.dnd.core.utils.NetworkUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.*;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Function;

public class NetworkClient {

    //Variables
    private final String hostName;
    private final int port;
    private final Function<NetworkPacket, Optional<NetworkPacket>> networkPacketConsumer;
    private final ConcurrentLinkedQueue<NetworkPacket> threadSafeOutboundMsgQueue = new ConcurrentLinkedQueue<>();
    private volatile boolean shouldStop = false;

    public NetworkClient(String hostName, int port, Function<NetworkPacket, Optional<NetworkPacket>> networkPacketConsumer) {
        this.hostName = hostName;
        this.port = port;
        this.networkPacketConsumer = networkPacketConsumer;
    }

    public void run() {
        try(Socket clientSocket = new Socket(hostName, port);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            while (!shouldStop) {
                if (in.ready()) {
                    processServerData(in.readLine()).ifPresent(packet -> NetworkUtils.write(out, packet));
                }
                NetworkPacket outboundPacket;
                if ((outboundPacket = threadSafeOutboundMsgQueue.poll()) != null) {
                    NetworkUtils.write(out, outboundPacket);
                }
            }
        } catch (IOException e) {
            System.out.println("Encountered exception: " + e.getMessage());
        }
    }

    //Remember to shut down your client when exiting!
    public void shutDown() {
        shouldStop = true;
    }

    //
    public void enqueueNetworkPacket(NetworkPacket networkPacket) {
        threadSafeOutboundMsgQueue.add(networkPacket);
    }

    private Optional<NetworkPacket> processServerData(String data) {
        try {
            return networkPacketConsumer.apply(JsonUtils.MAPPER.readValue(data, NetworkPacket.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}