package com.ericarao.dnd.core.View;

import com.ericarao.dnd.core.NetworkClient;
import com.ericarao.dnd.core.model.ClientUpdate;
import com.ericarao.dnd.core.model.RegisterPlayer;
import javafx.application.Application;
import static javafx.geometry.HPos.RIGHT;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientView extends Application {

    private NetworkClient networkClient = new NetworkClient("127.0.0.1", 8000, this::handleClientUpdate);
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void start(Stage clientStage) {
        executorService.submit(() -> networkClient.run());

        clientStage.setTitle("DND Tool: Client View");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome Player!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label charNameLabel = new Label("Character Name:");
        grid.add(charNameLabel, 0, 1);
        TextField charNameTextField = new TextField();
        grid.add(charNameTextField, 1, 1);

        Label charClassLabel = new Label("Character Class:");
        grid.add(charClassLabel, 0, 2);
        TextField charClassTextField = new TextField();
        grid.add(charClassTextField, 1, 2);

        Label charLevelLabel = new Label("Player Level:");
        grid.add(charLevelLabel, 0, 3);
        TextField charLevelTextField = new TextField();
        grid.add(charLevelTextField, 1, 3);

        Label hpLabel = new Label("Character HP:");
        grid.add(hpLabel, 0, 4);
        TextField hpTextField = new TextField();
        grid.add(hpTextField, 1, 4);

        Label strLabel = new Label("STR:");
        grid.add(strLabel, 0, 5);
        TextField strTextField = new TextField();
        grid.add(strTextField, 1, 5);

        Label dexLabel = new Label("DEX:");
        grid.add(dexLabel, 0, 6);
        TextField dexTextField = new TextField();
        grid.add(dexTextField, 1, 6);

        Label conLabel = new Label("CON:");
        grid.add(conLabel, 0, 7);
        TextField conTextField = new TextField();
        grid.add(conTextField, 1, 7);

        Label intLabel = new Label("INT:");
        grid.add(intLabel, 0, 8);
        TextField intTextField = new TextField();
        grid.add(intTextField, 1, 8);

        Label wisLabel = new Label("WIS:");
        grid.add(wisLabel, 0, 9);
        TextField wisTextField = new TextField();
        grid.add(wisTextField, 1, 9);

        Label chaLabel = new Label("CHA:");
        grid.add(chaLabel, 0, 10);
        TextField chaTextField = new TextField();
        grid.add(chaTextField, 1, 10);

        Label initiativeLabel = new Label("Intiative:");
        grid.add(initiativeLabel, 0, 11);
        TextField initiativeTextField = new TextField();
        grid.add(initiativeTextField, 1, 11);


        Button btn = new Button("Submit Change");
        HBox hbBtn = new HBox(12);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 13);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 0, 13);
        grid.setColumnSpan(actiontarget, 10);
        grid.setHalignment(actiontarget, RIGHT);
        actiontarget.setId("actiontarget");

        btn.setOnAction(e -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Change submitted (button clicked).");
            RegisterPlayer.builder()
                    .setPlayerName(charNameTextField.getText())
                    .setPlayerClass(charClassTextField.getText())
                    .setPlayerLevel(Integer.parseInt(charLevelTextField.getText()))
                    .setPlayerHP(Integer.parseInt(hpTextField.getText()))
                    .setPlayerStr(Integer.parseInt(strTextField.getText()))
                    .setPlayerDex(Integer.parseInt(dexTextField.getText()))
                    .setPlayerCon(Integer.parseInt(conTextField.getText()))
                    .setPlayerInt(Integer.parseInt(intTextField.getText()))
                    .setPlayerWis(Integer.parseInt(wisTextField.getText()))
                    .setPlayerCha(Integer.parseInt(chaTextField.getText()))
                    .setPlayerInitiative(Integer.parseInt(initiativeTextField.getText()))
                    .build();
        });

        Scene scene = new Scene(grid, 500, 500);
        clientStage.setScene(scene);
        clientStage.show();
    }

    private void handleClientUpdate(ClientUpdate clientUpdate) {
        Platform.runLater(() -> handleClientUpdateInternal(clientUpdate));
    }

    private void handleClientUpdateInternal(ClientUpdate clientUpdate) {
        // here we can safely update the UI
    }

    public static void main(String[] args) {
        launch(args);
    }

}