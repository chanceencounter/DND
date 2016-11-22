package com.ericarao.dnd.core.View;

import com.ericarao.dnd.core.NetworkClient;
import com.ericarao.dnd.core.NetworkServer;
import com.ericarao.dnd.core.model.ClientUpdate;
import com.ericarao.dnd.core.model.DMLoginCredentials;
import com.ericarao.dnd.core.model.RegisterPlayer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javafx.geometry.HPos.RIGHT;

public class ServerView {

    private int playerHealth;
    private final StackPane stack = new StackPane();
    final ComboBox comboBox = new ComboBox();
    final Label label = new Label();
    private static int numPlayers;
    private DMLoginCredentials dmLoginCredentialsObject;
    private Scene scene;
    private NetworkServer networkServer;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private ArrayList<RegisterPlayer> registerPlayerList = new ArrayList<>(numPlayers);

    public void start(Stage serverStage) {
        executorService.submit(() -> networkServer.run());

        //If dmLoginCredentialsObject is not given, please fail.
        if (dmLoginCredentialsObject == null) {
            executorService.shutdown();
            return;
        }

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        try {
            Label ipLabel = new Label("DM IP: " +
                    Inet4Address.getLocalHost().getHostAddress());
            grid.add(ipLabel, 0, 1, 2, 1);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //Add Panes per User
        for (int i = 0; i < numPlayers; i++) {
                registerPlayerList.add(i, RegisterPlayer.builder()
                        .setPlayerName("Player " + (i + 1))
                        .setPlayerClass("")
                        .setPlayerLevel(0)
                        .setPlayerHP(0)
                        .setPlayerStr(0)
                        .setPlayerDex(0)
                        .setPlayerCon(0)
                        .setPlayerInt(0)
                        .setPlayerWis(0)
                        .setPlayerCha(0)
                        .setPlayerInitiative(0)
                        .build());
            stack.getChildren().add(i, addPlayerChangeGrid(registerPlayerList.get(i)));
            //Get Pane Player Name
            comboBox.getItems().add(i, registerPlayerList.get(i).getPlayerName());
            comboBox.setValue(String.valueOf(registerPlayerList.get(i).getPlayerName()));
            label.setText(String.valueOf(registerPlayerList.get(i).getPlayerName()));
            setVisibility(stack, comboBox, label);
        }
        grid.add(comboBox, 0, 2);

        Button btnChange = new Button();
        btnChange.setText("Read comboBox");
        grid.add(btnChange, 1, 2);
        grid.add(label, 2, 2);
        btnChange.setOnAction(event -> setVisibility(stack, comboBox, label));

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setSpacing(5);
        vBox.getChildren().addAll(grid, comboBox, btnChange, stack);
        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        scene = new Scene(root, 300, 275);
        serverStage.setScene(scene);
    }

    //Set visibility
    public void setVisibility(Pane pane, ComboBox comboBox, Label label) {

        //Set Label
        label.setText("viewCharacter: " + comboBox.getValue());

        // Make all children invisible
        for (Node node : pane.getChildren()) {
            node.setVisible(false);
        }
        // make the selected rectangle visible
        int selectedIndex = comboBox.getSelectionModel()
                .selectedIndexProperty().getValue();
        pane.getChildren().get(selectedIndex).setVisible(true);

    }

    //Gridpanes specifically for damaging a player.
    private GridPane addPlayerChangeGrid(RegisterPlayer registerPlayer){

        GridPane playerGrids = new GridPane();

        //Player Info
        Label charNameLabel = new Label("Character Name: ");
        playerGrids.add(charNameLabel, 0, 1);
        Label charNameValueLabel = new Label(registerPlayer.getPlayerName());
        playerGrids.add(charNameValueLabel, 1, 1);

        Label charClassLabel = new Label("Character Class: ");
        playerGrids.add(charClassLabel, 0, 2);
        Label charClassValueLabel = new Label(registerPlayer.getPlayerClass());
        playerGrids.add(charClassValueLabel, 1, 2);

        Label charLevelLabel = new Label("Player Level: ");
        playerGrids.add(charLevelLabel, 0, 3);
        Label charLevelValueLabel = new Label(String.valueOf(registerPlayer.getPlayerLevel()));
        playerGrids.add(charLevelValueLabel, 1, 3);

        Label hpLabel = new Label("Character HP: ");
        playerGrids.add(hpLabel, 0, 4);
        Label hpValueLabel = new Label(String.valueOf(registerPlayer.getPlayerHP()));
        playerGrids.add(hpValueLabel, 1, 4);

        Label strLabel = new Label("STR: ");
        playerGrids.add(strLabel, 0, 5);
        Label strValueLabel = new Label(String.valueOf(registerPlayer.getPlayerStr()));
        playerGrids.add(strValueLabel, 1, 5);

        Label dexLabel = new Label("DEX: ");
        playerGrids.add(dexLabel, 0, 6);
        Label dexValueLabel = new Label(String.valueOf(registerPlayer.getPlayerDex()));
        playerGrids.add(dexValueLabel, 1, 6);

        Label conLabel = new Label("CON: ");
        playerGrids.add(conLabel, 0, 7);
        Label conValueLabel = new Label(String.valueOf(registerPlayer.getPlayerCon()));
        playerGrids.add(conValueLabel, 1, 7);

        Label intLabel = new Label("INT: ");
        playerGrids.add(intLabel, 0, 8);
        Label intValueLabel = new Label(String.valueOf(registerPlayer.getPlayerInt()));
        playerGrids.add(intValueLabel, 1, 8);

        Label wisLabel = new Label("WIS: ");
        playerGrids.add(wisLabel, 0, 9);
        Label wisValueLabel = new Label(String.valueOf(registerPlayer.getPlayerWis()));
        playerGrids.add(wisValueLabel, 1, 9);

        Label chaLabel = new Label("CHA: ");
        playerGrids.add(chaLabel, 0, 10);
        Label chaValueLabel = new Label(String.valueOf(registerPlayer.getPlayerCha()));
        playerGrids.add(chaValueLabel, 1, 10);

        Label initiativeLabel = new Label("Intiative: ");
        playerGrids.add(initiativeLabel, 0, 11);
        Label initiativeValueLabel = new Label(String.valueOf(registerPlayer.getPlayerInitiative()));
        playerGrids.add(initiativeValueLabel, 1, 11);
        
        //Info Affected/Sent
        Label damageLabel = new Label("Damage: ");
        playerGrids.add(damageLabel, 0, 12);
        TextField damageTextField = new TextField();
        playerGrids.add(damageTextField, 1, 12);

        Button btn = new Button("Submit Change");
        HBox hbBtn = new HBox(13);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        playerGrids.add(hbBtn, 1, 13);

        final Text actiontarget = new Text();
        playerGrids.add(actiontarget, 0, 13);
        playerGrids.setColumnSpan(actiontarget, 4);
        playerGrids.setHalignment(actiontarget, RIGHT);
        actiontarget.setId("actiontarget");

        btn.setOnAction(e -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Change submitted (button clicked).");
        });
        return playerGrids;
    }

    public void setDMLoginCredentialsObject(DMLoginCredentials dmLoginCredentialsObject) {
        this.dmLoginCredentialsObject = dmLoginCredentialsObject;
        numPlayers = this.dmLoginCredentialsObject.numPlayers();
        networkServer = new NetworkServer(2000, numPlayers);
    }

    //Get Current HP of Current Player
    public void getCurrentRegisterPlayerHP(RegisterPlayer registerPlayerObject) {
        this.playerHealth = registerPlayerObject.getPlayerHP();
    }

    private void handleClientUpdate(ClientUpdate clientUpdate) {
        Platform.runLater(() -> { playerHealth = clientUpdate.getNewHealth(); });
    }

}