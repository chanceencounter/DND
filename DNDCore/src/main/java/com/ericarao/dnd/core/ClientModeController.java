package com.ericarao.dnd.core;

import com.ericarao.dnd.core.View.ClientView;
import com.ericarao.dnd.core.model.NetworkPacket;
import com.ericarao.dnd.core.model.PlayerLoginCredentials;
import com.ericarao.dnd.core.model.RegisterPlayer;

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
        networkClient.enqueueNetworkPacket();
    }

    private Optional<NetworkPacket> handleClientData(NetworkPacket networkPacket) {
        return Optional.empty();
    }

    private void handleRegisterPlayer(RegisterPlayer registerPlayer) {
        networkClient.enqueueNetworkPacket(registerPlayer);
    }
}
