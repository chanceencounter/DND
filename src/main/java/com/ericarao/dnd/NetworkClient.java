package com.ericarao.dnd;

import com.ericarao.dnd.model.NetworkPacket;
import com.ericarao.dnd.utils.JsonUtils;
import com.ericarao.dnd.utils.NetworkUtils;

import java.io.*;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class NetworkClient {

    //Variables
    private final String hostName;
    private final int port;
    private final Function<NetworkPacket, Optional<NetworkPacket>> networkPacketConsumer;
    private final ConcurrentLinkedQueue<NetworkPacket> threadSafeOutboundMsgQueue = new ConcurrentLinkedQueue<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private volatile boolean running = false;


    public NetworkClient(String hostName, int port, Function<NetworkPacket, Optional<NetworkPacket>> networkPacketConsumer) {
        this.hostName = hostName;
        this.port = port;
        this.networkPacketConsumer = networkPacketConsumer;
    }

    public void run() {
        if (!running) {
            running = true;
            executorService.submit(() -> runInternal());
        }
    }

    //Remember to shut down your client when exiting!
    public void shutDown() {
        running = false;
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

    private void runInternal() {
        try(Socket clientSocket = new Socket(hostName, port);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            while (running) {
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
}