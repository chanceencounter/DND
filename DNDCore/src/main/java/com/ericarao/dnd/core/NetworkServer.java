package com.ericarao.dnd.core;

import com.ericarao.dnd.core.model.NetworkPacket;
import com.ericarao.dnd.core.model.ServerResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;

public class NetworkServer {

    //Variables
    private final int port;
    private final int threads;
    private final ConcurrentLinkedQueue<ServerResponse> threadSafeOutboundMsgQueue = new ConcurrentLinkedQueue<>();
    private final AtomicInteger clientCounter = new AtomicInteger();
    private final ConcurrentMap<Integer, ClientHandler> clientMap = new ConcurrentHashMap<>();
    private final BiFunction<Integer, NetworkPacket, Optional<ServerResponse>> packetProcessor;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private volatile boolean running = false;

    /**
     * Constructor
     */
    public NetworkServer(int port,
                         int threads,
                         BiFunction<Integer, NetworkPacket, Optional<ServerResponse>> packetProcessor) {
        this.port = port;
        this.threads = threads;
        this.packetProcessor = packetProcessor;
    }

    public void run() {
        if (!running) {
            running = true;
            executorService.submit(() -> runInternal());
        }
    }

    public void shutdown() {
        running = false;
    }

    public void enqueueMsg(ServerResponse serverResponse) {
        threadSafeOutboundMsgQueue.add(serverResponse);
    }

    private ClientHandler createHandler(Socket socket) {
        int id = clientCounter.incrementAndGet();
        ClientHandler clientHandler = new ClientHandler(socket);
        clientHandler.setPacketProcessor(getClientPacketProcessor(id));
        clientMap.put(id, clientHandler);
        return clientHandler;
    }

    //Function takes an ID and NetworkPacket and returns an [optional] server response.
    //Higher Order Function TODO: Read about Lambdas and Higher Order Functions
    private Function<NetworkPacket, Optional<NetworkPacket>> getClientPacketProcessor (int id) {
        return packet -> packetProcessor.apply(id, packet)
                .flatMap(resp -> resp.getClientIds().contains(id)
                    ? Optional.of(resp.getResponse())
                    : Optional.empty());
    }

    /**
     * The executor allows the work of communication with clients
     * to be handed off to other threads leaving the server available
     * to accept new connections
     */
    private void runInternal() {
        ExecutorService executorService = null;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            executorService = Executors.newFixedThreadPool(threads);
            while(running) {
                Socket client = serverSocket.accept();
                executorService.execute(() -> createHandler(client).run());
            }
        } catch (IOException e) {
            System.out.println("Could not initServerScene server. " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdown();
            }
            // stop our current clients
            clientMap.values().forEach(ClientHandler::shutdown);
        }
    }

    private void processEnqueuedPacket() {
        threadSafeOutboundMsgQueue.forEach(serverResponse -> {
            serverResponse.getClientIds().forEach(id -> clientMap.get(id).enqueueNetworkPacket(serverResponse.getResponse()));
        });
    }
}
