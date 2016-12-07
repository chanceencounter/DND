package com.ericarao.dnd;

import com.ericarao.dnd.View.ServerView;
import com.ericarao.dnd.model.*;

import java.util.Optional;

public class ServerModeController {
    private final ServerView serverView;
    private final NetworkServer networkServer;

    private String roomName;
    private String roomPassword;

    public ServerModeController(ServerView serverView, int port) {
        this.serverView = serverView;
        this.networkServer = new NetworkServer(port, 4, this::handleServerData);
    }

    public void login(DMLoginCredentials dmLoginCredentials) {
        roomName = dmLoginCredentials.roomName();
        roomPassword = dmLoginCredentials.roomPassword();
        networkServer.run();
    }

    private Optional<ServerResponse> handleServerData(int clientId, NetworkPacket networkPacket) {
        switch (networkPacket.getType()) {
            case RegisterPlayer:
                serverView.addPlayer(clientId, (RegisterPlayer)networkPacket,
                        clientUpdate -> processClientUpdate(clientId, clientUpdate));
                return Optional.of(ServerResponse.builder()
                        .addClientIds(clientId)
                        .setResponse(RegisterPlayerResponse.builder().setSuccess(true).build())
                        .build());
            case PlayerLogin:
                return Optional.of(ServerResponse.builder()
                        .addClientIds(clientId)
                        .setResponse(handlePlayerLogin((PlayerLogin)networkPacket))
                        .build());
            case PlayerUpdateStatsDM:
                serverView.updatePlayer(clientId, (PlayerUpdateStatsDM)networkPacket);
                break;
            // these should never be sent to the server
            case PlayerLoginResponse:
            case RegisterPlayerResponse:
            default:
                throw new IllegalArgumentException(String.format("Unknown network packet: %s from client: %d", networkPacket.getType(), clientId));
        }

        return Optional.empty();
    }

    private void processClientUpdate(int id, ClientUpdate clientUpdate) {
        networkServer.enqueueMsg(ServerResponse.builder()
                .addClientIds(id)
                .setResponse(clientUpdate)
                .build());
    }

    private PlayerLoginResponse handlePlayerLogin(PlayerLogin playerLogin) {
        if (playerLogin.getRoomName().equals(roomName) && playerLogin.getRoomPassword().equals(roomPassword)) {
            return PlayerLoginResponse.builder()
                    .setSuccess(true)
                    .build();
        }

        return PlayerLoginResponse.builder()
                .setSuccess(false)
                .build();
    }
}
