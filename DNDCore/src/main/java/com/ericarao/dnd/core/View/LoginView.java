package com.ericarao.dnd.core.View;

import com.ericarao.dnd.core.model.DMLoginCredentials;
import com.ericarao.dnd.core.model.PlayerLoginCredentials;
import com.sun.deploy.util.SessionState;
import javafx.application.Application;
import static javafx.geometry.HPos.RIGHT;
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
import javafx.stage.Stage;

public class LoginView extends Application {

    private InputValidation inputValidationNum = new InputValidation();

    @Override
    public void start(Stage loginStage) {
        final ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll("Dungeon Master", "Player Character");
        final Label label = new Label();
        final StackPane stack = new StackPane();
        Button btnChange = new Button();
        btnChange.setText("Read comboBox");

        loginStage.setTitle("Login");

        //Listen on Button Action
        btnChange.setOnAction(event -> setVisibility(stack, comboBox, label));

        //Listen on combobox
        /*comboBox.getSelectionModel().selectedIndexProperty()
                .addListener((ObservableValue<? extends Number> observable,
                              Number oldValue, Number newValue) ->
                        setVisibility(stack, comboBox, label)
                );*/

        stack.getChildren().add(dmLoginPane(loginStage));
        stack.getChildren().add(playerLoginPane(loginStage));

        // Placing it after adding rectangle to stack
        // will trigger the changelistener to show default rectangle
        comboBox.setValue("Player Character");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setSpacing(5);
        vBox.getChildren().addAll(label, comboBox, btnChange, stack);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 300, 400);

        loginStage.setTitle("Login");
        loginStage.setScene(scene);
        loginStage.show();

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

    private GridPane playerLoginPane(Stage loginStage) {
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
        loginStage.setScene(scene);
        loginStage.show();

        btn.setOnAction(e -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Sign in button pressed");
            PlayerLoginCredentials newPlayer = PlayerLoginCredentials.builder()
                    .setDmIP(ipTextField.getText())
                    .setPlayerName(userTextField.getText())
                    .setRoomName(idTextField.getText())
                    .setRoomPassword(pwBox.getText())
                    .build();
            ClientView newClientView = new ClientView();
            //newClientView.


        });



        return playerLoginGridPane;
    }

    private GridPane dmLoginPane(Stage loginStage) {
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
                    .setRoomName(idLabel.getText())
                    .setNumPlayers(Integer.parseInt(numTextField.getText()))
                    .setRoomPassword(pwBox.getText())
                    .build();

            ServerView newServerView = new ServerView();
            newServerView.setDMLoginCredentialsObject(currentDM);
            newServerView.start(loginStage);
            loginStage.setTitle("ServerView: DM");

           // Scene serverViewScene = newServerView.returnView();
            //loginStage.setScene(serverViewScene);
        });

        if (loginStage.getScene() == null) {
            Scene scene = new Scene(dmLoginGridPane, 300, 275);
            loginStage.setScene(scene);
        }

        return dmLoginGridPane;
    }

    public static void main(String[] args) {
        launch(args);
    }

}