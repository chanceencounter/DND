package com.ericarao.dnd;

import com.ericarao.dnd.model.*;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NetworkClientTest {
    private static final String HOST_NAME = "testroom";
    private static final int PORT_NUMBER = 8080;
    private NetworkClient networkClient;
    private Socket socket;

    @Before
    public void setup() {
        SocketFactory socketFactory = mock(SocketFactory.class);
        socket = mock(Socket.class);
        when(socketFactory.create(HOST_NAME, PORT_NUMBER)).thenReturn(socket);
        networkClient = new NetworkClient(HOST_NAME, PORT_NUMBER, null, socketFactory);
        networkClient.run();
    }

    @Test
    public void itSendsNetworkPackets() {
        NetworkPacket playerLogin = PlayerLogin.builder()
                .setPlayerName("Player1")
                .setRoomName("Test")
                .setRoomPassword("asdf")
                .build();

        NetworkPacket playerLoginResponse = PlayerLoginResponse.builder()
                .setSuccess(true)
                .build();

        networkClient.enqueueNetworkPacket(playerLogin);
        networkClient.enqueueNetworkPacket(playerLoginResponse);
    }
}
