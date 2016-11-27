package com.ericarao.dnd.core;

import com.ericarao.dnd.core.View.ClientView;
import com.ericarao.dnd.core.model.*;

import java.util.Optional;
import java.util.function.Consumer;

public class ClientModeController {
    private final NetworkClient networkClient;
    private final ClientView clientView;

    private Consumer<PlayerLoginResponse> loginResponseConsumer;

    public ClientModeController(ClientView clientView, String hostName, int port) {
        this.clientView = clientView;
        this.networkClient = new NetworkClient(hostName, port, this::handleClientData);
        clientView.setRegisterPlayerCallback(this::handleRegisterPlayer);
    }

    public void login(PlayerLoginCredentials playerLoginCredentials, Consumer<PlayerLoginResponse> loginResponseConsumer) {
        this.loginResponseConsumer = loginResponseConsumer;
        PlayerLogin playerLogin = PlayerLogin.builder()
                .setRoomName(playerLoginCredentials.roomName())
                .setRoomPassword(playerLoginCredentials.roomPassword())
                .setPlayerName(playerLoginCredentials.playerName())
                .build();

        networkClient.run();
        networkClient.enqueueNetworkPacket(playerLogin);
    }

    private Optional<NetworkPacket> handleClientData(NetworkPacket networkPacket) {
        switch (networkPacket.getType()) {
            case PlayerLoginResponse:
                loginResponseConsumer.accept((PlayerLoginResponse) networkPacket);
                break;

            // should never be sent to the client
            case RegisterPlayer:
            case PlayerLogin:
            default:
                throw new IllegalArgumentException(String.format("Unknown packet type %s", networkPacket.getType()));
        }

        return Optional.empty();
    }

    private void handleRegisterPlayer(RegisterPlayer registerPlayer) {
        networkClient.enqueueNetworkPacket(registerPlayer);
    }
}
