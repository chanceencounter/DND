package com.ericarao.dnd.core.View;

import com.ericarao.dnd.core.model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerView {

    //View Specific
    private final Map<Integer, PlayerGridPane> playerMapping = new ConcurrentHashMap<>();
    private final StackPane stack = new StackPane();
    private final Label label = new Label();
    private final ObservableList<PlayerComboBoxItem> comboBoxItems = FXCollections.observableArrayList();

    private Scene scene;

    public Scene getScene() {
        if (scene == null) {
            scene = initServerScene();
        }

        return scene;
    }

    public void addPlayer(int id, RegisterPlayer registerPlayer) {

        PlayerGridPane newPlayerGridPane = new PlayerGridPane(registerPlayer);
        //If Value is not null, exit.
        if (playerMapping.putIfAbsent(id, newPlayerGridPane) != null) {
            return;
        }
        Platform.runLater(() -> {
            stack.getChildren().add(newPlayerGridPane);
            //Get Pane Player Name
            comboBoxItems.add(new PlayerComboBoxItem(id, registerPlayer.getPlayerName()));
            label.setText(String.valueOf(registerPlayer.getPlayerName()));
            //setVisibility(stack, comboBox, label);
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

        ComboBox<PlayerComboBoxItem> comboBox = new ComboBox<>();
        comboBox.setItems(comboBoxItems);
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

    //TODO: Write Method for "Recieve Query for Credentials or ClientUpdate"
    //Method for Comparing Credentials
    public boolean compareCredentials(String inputPassword, String dmPassword,
                                      String inputIPAddress, String dmIPAddress) {
        return (inputPassword.equals(dmPassword) && inputIPAddress.equals(dmIPAddress));
    }

    //Method for Updating Player
    public void updatePlayer(int id, UpdatePlayerStats updatePlayerStats) {

    }
}