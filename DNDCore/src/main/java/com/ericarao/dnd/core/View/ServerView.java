package com.ericarao.dnd.core.View;

import com.ericarao.dnd.core.model.*;
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

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static javafx.geometry.HPos.RIGHT;

public class ServerView {
    private static final String VBOX_ID = "vbox.id";

    //Variables for Tracking Players
    private int numPlayers;
    private int currentPlayerNum;
    private RegisterPlayer currentPlayer;

    //Credential Objects
    private DMLoginCredentials dmLoginCredentialsObject;

    //Login Credentials
    private String dmIPAddress;
    private String dmPassword;

    //View Specific
    private Map<Integer, RegisterPlayer> playerMapping = new ConcurrentHashMap<>();
    private StackPane stack = new StackPane();
    private ComboBox comboBox = new ComboBox();
    private Label label = new Label();

    private Scene scene;

    public Scene getScene() {
        if (scene == null) {
            scene = initServerScene();
        }

        return scene;
    }

    public void addPlayer(int id, RegisterPlayer registerPlayer) {

        //If Value is not null, exit.
        if (playerMapping.putIfAbsent(id, registerPlayer) != null) {
            return;
        }
        Platform.runLater(() -> {
            stack.getChildren().add(id, addPlayerChangeGrid(playerMapping.get(id)));
            //Get Pane Player Name
            comboBox.getItems().add(id, playerMapping.get(id).getPlayerName());
            comboBox.setValue(String.valueOf(playerMapping.get(id).getPlayerName()));
            label.setText(String.valueOf(playerMapping.get(id).getPlayerName()));
            setVisibility(stack, comboBox, label);
        });
    }

    //Start ServerView
    private Scene initServerScene() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome our dear Overlord!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        try {
            Label ipLabel = new Label("DM IP: " +
                    Inet4Address.getLocalHost().getHostAddress());
            grid.add(ipLabel, 0, 1, 2, 1);
        } catch (UnknownHostException e) {
            e.printStackTrace();
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

        return new Scene(root, 500, 500);
    }

    //Gridpanes specifically for Modifying a Player.
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

        Label statusLabel = new Label("Status Effect: ");
        playerGrids.add(statusLabel, 0, 13);
        TextField statusTextField = new TextField();
        playerGrids.add(statusTextField, 1, 13);

        Label saveThrowLabel = new Label("Saving Throw: ");
        playerGrids.add(saveThrowLabel, 0, 14);
        TextField saveThrowTextField = new TextField();
        playerGrids.add(saveThrowTextField, 1, 14);

        Button btn = new Button("Submit Change");
        HBox hbBtn = new HBox(15);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        playerGrids.add(hbBtn, 1, 15);

        final Text actiontarget = new Text();
        playerGrids.add(actiontarget, 0, 16);
        playerGrids.setColumnSpan(actiontarget, 4);
        playerGrids.setHalignment(actiontarget, RIGHT);
        actiontarget.setId("actiontarget");

        btn.setOnAction(e -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Change submitted (button clicked).");
            ClientUpdate updatePlayer = ClientUpdate.builder()
                    .setDamage(Integer.parseInt(damageTextField.getText()))
                    .setStatusEffect(statusTextField.getText())
                    .setSavingThrow(Integer.parseInt(saveThrowTextField.getText()))
                    .build();
        });

        return playerGrids;
    }

    //Setters
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

    //Set Credentials for Logging In
    //Also method for Setting DM Credentials
    public void setDMLoginCredentialsObject(DMLoginCredentials dmLoginCredentialsObject) {
        //Prevent user from reconstructing this. Not ok.
        if (this.dmLoginCredentialsObject == null) {
            this.dmLoginCredentialsObject = dmLoginCredentialsObject;
            numPlayers = this.dmLoginCredentialsObject.numPlayers();
            this.dmIPAddress = dmLoginCredentialsObject.getIP();
            this.dmPassword = dmLoginCredentialsObject.roomPassword();
        }
    }

    //CurrentPlayer Number on List
    public void getCurrentPlayerNum(int currentPlayerNum) {
        this.currentPlayerNum = currentPlayerNum;
    }

    //TODO: Write Method for "Recieve Query for Credentials or ClientUpdate"
    //Method for Comparing Credentials
    public boolean compareCredentials(String inputPassword, String dmPassword,
                                      String inputIPAddress, String dmIPAddress) {
        return (inputPassword.equals(dmPassword) && inputIPAddress.equals(dmIPAddress));
    }

    //Method for Determining if you are Dropping the Player
    public void dropConnection(PlayerLoginCredentials playerCredentialsObject) {
        if (!compareCredentials(playerCredentialsObject.roomPassword(), dmPassword,
                playerCredentialsObject.dmIP(), dmIPAddress)) {
            //Drop connection if player sends wrong credentials.
        }
    }
}