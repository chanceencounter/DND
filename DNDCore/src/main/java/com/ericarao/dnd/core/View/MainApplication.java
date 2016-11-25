package com.ericarao.dnd.core.View;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginView loginView = new LoginView();
        primaryStage.setScene(loginView.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
