package com.ericarao.dnd.core.View;

import com.ericarao.dnd.core.model.*;

import static javafx.geometry.HPos.RIGHT;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.function.Consumer;

public class ClientView {
    private static final String SUBMIT_CHANGE_BUTTON = "submit-change";

    private PlayerLoginCredentials playerLoginCredentialsObject;
    private DMLoginCredentials dmLoginCredentials;
    private Consumer<RegisterPlayer> registerPlayerConsumer;
    private Scene scene;

    public Scene getScene() {
        if (scene == null) {
            scene = initClientScene();
        }

        return scene;
    }

    //Start ClientView
    private Scene initClientScene() {

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
        btn.setId(SUBMIT_CHANGE_BUTTON);
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
            RegisterPlayer registerPlayer = RegisterPlayer.builder()
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

            submitRegisterPlayer(registerPlayer);
        });

        return new Scene(grid, 500, 500);
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

    public void setRegisterPlayerCallback(Consumer<RegisterPlayer> registerPlayerConsumer) {
        this.registerPlayerConsumer = registerPlayerConsumer;
    }

    private void submitRegisterPlayer(RegisterPlayer registerPlayer) {
        if (registerPlayerConsumer != null) {
            registerPlayerConsumer.accept(registerPlayer);
        }
    }
}