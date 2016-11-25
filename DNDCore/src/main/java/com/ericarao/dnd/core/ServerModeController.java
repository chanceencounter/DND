package com.ericarao.dnd.core;

import com.ericarao.dnd.core.View.ServerView;
import com.ericarao.dnd.core.model.NetworkPacket;
import com.ericarao.dnd.core.model.ServerResponse;

import java.util.Optional;

public class ServerModeController {
    private final ServerView serverView;
    private final NetworkServer networkServer;

    public ServerModeController(ServerView serverView, int port) {
        this.serverView = serverView;
        this.networkServer = new NetworkServer(port, 4, this::handleServerData);
    }

    private Optional<ServerResponse> handleServerData(int clientId, NetworkPacket networkPacket) {
        return Optional.empty();
    }
}
