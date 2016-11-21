package com.ericarao.dnd.server;

import com.ericarao.dnd.core.NetworkClient;
import com.ericarao.dnd.core.model.ClientUpdate;
import com.ericarao.dnd.core.model.RegisterPlayer;
import javafx.application.Application;
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javafx.geometry.HPos.RIGHT;

public class ServerView extends Application {

    private int playerHealth;
    private NetworkClient networkClient = new NetworkClient("127.0.0.1", 8000, this::handleClientUpdate);
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final StackPane stack = new StackPane();
    final ComboBox comboBox = new ComboBox();
    final Label label = new Label();
    private static int numPlayers;
    private static RegisterPlayer[] registerPlayerList;


    @Override
    public void start(Stage serverStage) {
        executorService.submit(() -> networkClient.run());

        serverStage.setTitle("DND Tool: Server View");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        //Add Panes per User
        for (RegisterPlayer registerPlayer : registerPlayerList) {
            for (int i = 0; i < numPlayers; i++) {
                registerPlayerList[i] = registerPlayer;
            }
        }


        comboBox.getItems().addAll("");
        grid.add(comboBox, 0, 1);
        Button btnChange = new Button();
        btnChange.setText("Read comboBox");
        grid.add(btnChange, 1, 1);
        btnChange.setOnAction(event -> setVisibility(stack, comboBox, label));

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setSpacing(5);
        vBox.getChildren().addAll(label, comboBox, btnChange, stack);
        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 300, 400);

        serverStage.setTitle("Server: DM View");
        serverStage.setScene(scene);
        serverStage.show();
    }

    //Set visibility
    public void setVisibility(Pane pane, ComboBox comboBox, Label label) {

        //Set Label
        label.setText("selectLogin: " + comboBox.getValue());

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

        Label damageLabel = new Label("Damage:");
        playerGrids.add(damageLabel, 0, 1);
        TextField damageTextField = new TextField();
        playerGrids.add(damageTextField, 1, 1);

        Label initiativeLabel = new Label ("Initiative: ");
        playerGrids.add(initiativeLabel, 0, 2);
        Label currentPlayerInitValueLabel = new Label(String.valueOf(registerPlayer.getPlayerInitiative()));
        playerGrids.add(currentPlayerInitValueLabel, 1, 2);

        Button btn = new Button("Submit Change");
        HBox hbBtn = new HBox(2);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        playerGrids.add(hbBtn, 1, 3);

        final Text actiontarget = new Text();
        playerGrids.add(actiontarget, 0, 3);
        playerGrids.setColumnSpan(actiontarget, 4);
        playerGrids.setHalignment(actiontarget, RIGHT);
        actiontarget.setId("actiontarget");

        btn.setOnAction(e -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Change submitted (button clicked).");
        });
        return playerGrids;
    }

    private void handleClientUpdate(ClientUpdate clientUpdate) {
        Platform.runLater(() -> { playerHealth = clientUpdate.getNewHealth(); });
    }
    public static void main(String[] args) {
        launch(args);
    }

}