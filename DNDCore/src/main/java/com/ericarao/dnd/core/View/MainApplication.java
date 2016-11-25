package com.ericarao.dnd.core.View;

import com.ericarao.dnd.core.ClientModeController;
import com.ericarao.dnd.core.NetworkClient;
import com.ericarao.dnd.core.NetworkServer;
import com.ericarao.dnd.core.ServerModeController;
import com.ericarao.dnd.core.model.DMLoginCredentials;
import com.ericarao.dnd.core.model.PlayerLoginCredentials;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    private static final int PORT = 8000;
    private static final int NUM_SERVER_THREADS = 4;

    private Stage primaryStage;
    private ClientModeController clientModeController;
    private ServerModeController serverModeController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        LoginView loginView = new LoginView();
        loginView.setDmLoginCallback(this::handleDmLogin);
        loginView.setPlayerLoginCallback(this::handlePlayerLogin);

        primaryStage.setScene(loginView.getScene());
        primaryStage.show();
    }

    private void handleDmLogin(DMLoginCredentials dmLoginCredentials) {
        ServerView serverView = new ServerView();
        serverModeController = new ServerModeController(serverView, PORT);
        primaryStage.setScene(serverView.getScene());
    }

    private void handlePlayerLogin(PlayerLoginCredentials playerLoginCredentials) {
        ClientView clientView = new ClientView();
        clientModeController = new ClientModeController(clientView, playerLoginCredentials.dmIP(), PORT);
        primaryStage.setScene(clientView.getScene());
    }
}
