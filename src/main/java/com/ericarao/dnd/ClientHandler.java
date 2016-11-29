package com.ericarao.dnd;

import com.ericarao.dnd.model.NetworkPacket;
import com.ericarao.dnd.utils.JsonUtils;
import com.ericarao.dnd.utils.NetworkUtils;

import java.io.*;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;

public class ClientHandler implements Runnable {
    //Variables
    private final Socket socket;
    private final ConcurrentLinkedQueue<NetworkPacket> threadSafeOutboundMsgQueue = new ConcurrentLinkedQueue<>();
    private Function<NetworkPacket, Optional<NetworkPacket>> packetProcessor;
    private volatile boolean shouldStop = false;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
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
            System.out.println("Encountered exception while handling client. " + e.getMessage());
        }
    }

    public void shutdown() {
        shouldStop = true;
    }

    public void setPacketProcessor(Function<NetworkPacket, Optional<NetworkPacket>> packetProcessor) {
        this.packetProcessor = packetProcessor;
    }

    public void enqueueNetworkPacket(NetworkPacket networkPacket) {
        threadSafeOutboundMsgQueue.add(networkPacket);
    }

    private Optional<NetworkPacket> processServerData(String data) {
        try {
            return packetProcessor.apply(JsonUtils.MAPPER.readValue(data, NetworkPacket.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
