package com.ericarao.dnd.View;

import com.ericarao.dnd.model.DMLoginCredentials;
import com.ericarao.dnd.model.PlayerLoginCredentials;
import static javafx.geometry.HPos.RIGHT;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.function.Consumer;

public class LoginView {

    private final InputValidation inputValidationNum = new InputValidation();

    private Consumer<DMLoginCredentials> dmLoginCredentialsConsumer;
    private Consumer<PlayerLoginCredentials> playerLoginCredentialsConsumer;
    private Scene scene;

    public void setDmLoginCallback(Consumer<DMLoginCredentials> dmLoginCallback) {
        this.dmLoginCredentialsConsumer = dmLoginCallback;
    }

    public void setPlayerLoginCallback(Consumer<PlayerLoginCredentials> playerLoginCallback) {
        this.playerLoginCredentialsConsumer = playerLoginCallback;
    }

    public Scene getScene() {
        if (scene == null) {
            scene = initScene();
        }

        return scene;
    }

    private Scene initScene() {
        final ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll("Dungeon Master", "Player Character");
        final Label label = new Label();
        final StackPane stack = new StackPane();

        //Listen to ComboBox
        comboBox.getSelectionModel().selectedIndexProperty()
                .addListener((ObservableValue<? extends Number> observable,
                              Number oldValue, Number newValue) ->
                        setVisibility(stack, comboBox, label)
                );

        stack.getChildren().add(dmLoginPane());
        stack.getChildren().add(playerLoginPane());

        // Placing it after adding rectangle to stack
        // will trigger the changelistener to show default rectangle
        comboBox.setValue("Player Character");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setSpacing(5);
        vBox.getChildren().addAll(label, comboBox, stack);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        return new Scene(root, 300, 400);

        /*
        loginStage.setTitle("Login");
        loginStage.setScene(scene);
        loginStage.show();
        */
    }

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

    private GridPane playerLoginPane() {
        GridPane playerLoginGridPane = new GridPane();
        playerLoginGridPane.setAlignment(Pos.CENTER);
        playerLoginGridPane.setHgap(10);
        playerLoginGridPane.setVgap(10);
        playerLoginGridPane.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome Player");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        playerLoginGridPane.add(scenetitle, 0, 0, 2, 1);

        Label idLabel = new Label("Room ID:");
        playerLoginGridPane.add(idLabel, 0, 1);
        TextField idTextField = new TextField();
        playerLoginGridPane.add(idTextField, 1, 1);

        Label ipAddressLabel = new Label("Room IP:");
        playerLoginGridPane.add(ipAddressLabel, 0, 2);
        TextField ipTextField = new TextField();
        playerLoginGridPane.add(ipTextField, 1, 2);

        Label userName = new Label("Player Name:");
        playerLoginGridPane.add(userName, 0, 3);
        TextField userTextField = new TextField();
        playerLoginGridPane.add(userTextField, 1, 3);

        Label pw = new Label("Room Password:");
        playerLoginGridPane.add(pw, 0, 4);
        PasswordField pwBox = new PasswordField();
        playerLoginGridPane.add(pwBox, 1, 4);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        playerLoginGridPane.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        playerLoginGridPane.add(actiontarget, 0, 6);
        playerLoginGridPane.setColumnSpan(actiontarget, 2);
        playerLoginGridPane.setHalignment(actiontarget, RIGHT);
        actiontarget.setId("actiontarget");

        Scene scene = new Scene(playerLoginGridPane, 300, 275);
        //loginStage.setScene(scene);
        //loginStage.show();

        btn.setOnAction(e -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Sign in button pressed");
            PlayerLoginCredentials newPlayer = PlayerLoginCredentials.builder()
                    .setDmIP(ipTextField.getText())
                    .setPlayerName(userTextField.getText())
                    .setRoomName(idTextField.getText())
                    .setRoomPassword(pwBox.getText())
                    .build();

            onSignInButtonPressed(newPlayer);
            //loginStage.setTitle("DND Tool: Client View");
            //loginStage.setScene(newClientView.getScene());
            //loginStage.show();
        });

        return playerLoginGridPane;
    }

    private GridPane dmLoginPane() {
        GridPane dmLoginGridPane = new GridPane();
        dmLoginGridPane.setAlignment(Pos.CENTER);
        dmLoginGridPane.setHgap(10);
        dmLoginGridPane.setVgap(10);
        dmLoginGridPane.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome Dungeon Master");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        dmLoginGridPane.add(scenetitle, 0, 0, 2, 1);

        Label idLabel = new Label("Room ID:");
        dmLoginGridPane.add(idLabel, 0, 1);
        TextField idTextField = new TextField();
        dmLoginGridPane.add(idTextField, 1, 1);

        Label numLabel = new Label("Number of Players:");
        dmLoginGridPane.add(numLabel, 0, 2);
        TextField numTextField = new TextField();
        //Number Input Validation
        inputValidationNum.numberValidation(numTextField);
        dmLoginGridPane.add(numTextField, 1, 2);
//        dmLoginGridPane.add(new Label(inputValidationNum.valid(numTextField)), 2, 2);

        Label pw = new Label("Room Password:");
        dmLoginGridPane.add(pw, 0, 3);
        TextField pwBox = new TextField();
        dmLoginGridPane.add(pwBox, 1, 3);


        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(4);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        dmLoginGridPane.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        dmLoginGridPane.add(actiontarget, 0, 6);
        dmLoginGridPane.setColumnSpan(actiontarget, 2);
        dmLoginGridPane.setHalignment(actiontarget, RIGHT);
        actiontarget.setId("actiontarget");

        btn.setOnAction(e -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Sign in button pressed");
            DMLoginCredentials currentDM = DMLoginCredentials.builder()
                    .setRoomName(idTextField.getText())
                    .setNumPlayers(Integer.parseInt(numTextField.getText()))
                    .setRoomPassword(pwBox.getText())
                    .build();

            onSignInButtonPressed(currentDM);
            //loginStage.setTitle("DND Tool: Server View");
            //loginStage.setScene(newServerView.getScene());

        });

        /*
        if (loginStage.getScene() == null) {
            Scene scene = new Scene(dmLoginGridPane, 300, 275);
            loginStage.setScene(scene);
        }
        */

        return dmLoginGridPane;
    }

    private void onSignInButtonPressed(DMLoginCredentials dmLoginCredentials) {
        if (dmLoginCredentialsConsumer != null) {
            dmLoginCredentialsConsumer.accept(dmLoginCredentials);
        }
    }

    private void onSignInButtonPressed(PlayerLoginCredentials playerLoginCredentials) {
        if (playerLoginCredentialsConsumer != null) {
            playerLoginCredentialsConsumer.accept(playerLoginCredentials);
        }
    }
}