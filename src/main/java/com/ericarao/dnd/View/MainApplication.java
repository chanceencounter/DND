package com.ericarao.dnd.View;

import com.ericarao.dnd.ServerModeController;
import com.ericarao.dnd.ClientModeController;
import com.ericarao.dnd.model.DMLoginCredentials;
import com.ericarao.dnd.model.PlayerLoginCredentials;
import com.ericarao.dnd.model.PlayerLoginResponse;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MainApplication extends Application {
    private static final int PORT = 8000;

    private Stage primaryStage;
    private ClientView clientView;
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
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.show();
    }

    private void handleDmLogin(DMLoginCredentials dmLoginCredentials) {
        ServerView serverView = new ServerView();
        serverModeController = new ServerModeController(serverView, PORT);
        serverModeController.login(dmLoginCredentials);
        primaryStage.setScene(serverView.getScene());
    }

    private void handlePlayerLogin(PlayerLoginCredentials playerLoginCredentials) {
        clientView = new ClientView();
        clientModeController = new ClientModeController(clientView, playerLoginCredentials.dmIP(), PORT);
        clientModeController.login(playerLoginCredentials, this::handleLoginComplete);
        primaryStage.setScene(new LoginStatusView().getScene());
    }

    private void handleLoginComplete(PlayerLoginResponse playerLoginResponse) {
        if (playerLoginResponse.getSuccess()) {
            Platform.runLater(() -> {
                primaryStage.setScene(clientView.getScene());
                primaryStage.show();
            });

        } else {
            //handle failure by returning to login
        }
    }
}
