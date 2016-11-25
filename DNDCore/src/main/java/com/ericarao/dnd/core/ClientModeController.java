package com.ericarao.dnd.core;

import com.ericarao.dnd.core.View.ClientView;
import com.ericarao.dnd.core.model.*;

import java.util.Optional;

public class ClientModeController {
    private final NetworkClient networkClient;
    private final ClientView clientView;

    public ClientModeController(ClientView clientView, String hostName, int port) {
        this.clientView = clientView;
        this.networkClient = new NetworkClient(hostName, port, this::handleClientData);
        clientView.setRegisterPlayerCallback(this::handleRegisterPlayer);
    }

    public void login(PlayerLoginCredentials playerLoginCredentials) {
        PlayerLogin playerLogin = PlayerLogin.builder()
                .setRoomName(playerLoginCredentials.roomName())
                .setRoomPassword(playerLoginCredentials.roomPassword())
                .build();

        networkClient.run();
        networkClient.enqueueNetworkPacket(playerLogin);
    }

    private Optional<NetworkPacket> handleClientData(NetworkPacket networkPacket) {
        switch (networkPacket.getType()) {
            case PlayerLoginResponse:
                PlayerLoginResponse response = (PlayerLoginResponse)networkPacket;
                if (response.getSuccess()) {
                    System.out.print("Login successful!");
                } else {
                    System.out.println("Login not successful :(");
                }
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
