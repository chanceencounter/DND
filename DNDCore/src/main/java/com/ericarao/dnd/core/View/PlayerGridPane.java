package com.ericarao.dnd.core.View;

import com.ericarao.dnd.core.model.ClientUpdate;
import com.ericarao.dnd.core.model.PlayerUpdateStatsDM;
import com.ericarao.dnd.core.model.RegisterPlayer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static javafx.geometry.HPos.RIGHT;

public class PlayerGridPane extends GridPane {

    private Label charNameValueLabel;
    private  Label charClassValueLabel;
    private  Label charLevelValueLabel;
    private Label hpValueLabel;
    private Label strValueLabel;
    private Label dexValueLabel;
    private Label conValueLabel;
    private Label intValueLabel;
    private Label wisValueLabel;
    private Label chaValueLabel;
    private Label initiativeValueLabel;
    private TextField damageTextField;
    private  TextField statusTextField;
    private TextField saveThrowTextField;

    public PlayerGridPane(RegisterPlayer registerPlayer) {

        //Player Info
        //"this" is used for whatever gridpane you are using in this instance
        Label charNameLabel = new Label("Character Name: ");
        this.add(charNameLabel, 0, 1);
        charNameValueLabel = new Label(registerPlayer.getPlayerName());
        this.add(charNameValueLabel, 1, 1);

        Label charClassLabel = new Label("Character Class: ");
        this.add(charClassLabel, 0, 2);
        charClassValueLabel = new Label(registerPlayer.getPlayerClass());
        this.add(charClassValueLabel, 1, 2);

        Label charLevelLabel = new Label("Player Level: ");
        this.add(charLevelLabel, 0, 3);
        charLevelValueLabel = new Label(String.valueOf(registerPlayer.getPlayerLevel()));
        this.add(charLevelValueLabel, 1, 3);

        Label hpLabel = new Label("Character HP: ");
        this.add(hpLabel, 0, 4);
        hpValueLabel = new Label(String.valueOf(registerPlayer.getPlayerHP()));
        this.add(hpValueLabel, 1, 4);

        Label strLabel = new Label("STR: ");
        this.add(strLabel, 0, 5);
        strValueLabel = new Label(String.valueOf(registerPlayer.getPlayerStr()));
        this.add(strValueLabel, 1, 5);

        Label dexLabel = new Label("DEX: ");
        this.add(dexLabel, 0, 6);
        dexValueLabel = new Label(String.valueOf(registerPlayer.getPlayerDex()));
        this.add(dexValueLabel, 1, 6);

        Label conLabel = new Label("CON: ");
        this.add(conLabel, 0, 7);
        conValueLabel = new Label(String.valueOf(registerPlayer.getPlayerCon()));
        this.add(conValueLabel, 1, 7);

        Label intLabel = new Label("INT: ");
        this.add(intLabel, 0, 8);
        intValueLabel = new Label(String.valueOf(registerPlayer.getPlayerInt()));
        this.add(intValueLabel, 1, 8);

        Label wisLabel = new Label("WIS: ");
        this.add(wisLabel, 0, 9);
        wisValueLabel = new Label(String.valueOf(registerPlayer.getPlayerWis()));
        this.add(wisValueLabel, 1, 9);

        Label chaLabel = new Label("CHA: ");
        this.add(chaLabel, 0, 10);
        chaValueLabel = new Label(String.valueOf(registerPlayer.getPlayerCha()));
        this.add(chaValueLabel, 1, 10);

        Label initiativeLabel = new Label("Intiative: ");
        this.add(initiativeLabel, 0, 11);
        initiativeValueLabel = new Label(String.valueOf(registerPlayer.getPlayerInitiative()));
        this.add(initiativeValueLabel, 1, 11);

        //Info Affected/Sent
        Label damageLabel = new Label("Damage: ");
        this.add(damageLabel, 0, 12);
        damageTextField = new TextField();
        this.add(damageTextField, 1, 12);

        Label statusLabel = new Label("Status Effect: ");
        this.add(statusLabel, 0, 13);
        statusTextField = new TextField();
        this.add(statusTextField, 1, 13);

        Label saveThrowLabel = new Label("Saving Throw: ");
        this.add(saveThrowLabel, 0, 14);
        saveThrowTextField = new TextField();
        this.add(saveThrowTextField, 1, 14);

        Button btn = new Button("Submit Change");
        HBox hbBtn = new HBox(15);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        this.add(hbBtn, 1, 15);

        final Text actiontarget = new Text();
        this.add(actiontarget, 0, 16);
        this.setColumnSpan(actiontarget, 4);
        this.setHalignment(actiontarget, RIGHT);
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
        this.setVisible(false);
    }

    public void update(PlayerUpdateStatsDM updatePlayerStats) {

        charLevelValueLabel.setText(String.valueOf(updatePlayerStats.getPlayerLevel()));
        hpValueLabel.setText(String.valueOf(updatePlayerStats.getPlayerHP()));
        strValueLabel.setText(String.valueOf(updatePlayerStats.getPlayerStr()));
        dexValueLabel.setText(String.valueOf(updatePlayerStats.getPlayerDex()));
        conValueLabel.setText(String.valueOf(updatePlayerStats.getPlayerCon()));
        intValueLabel.setText(String.valueOf(updatePlayerStats.getPlayerInt()));
        wisValueLabel.setText(String.valueOf(updatePlayerStats.getPlayerWis()));
        chaValueLabel.setText(String.valueOf(updatePlayerStats.getPlayerCha()));
        initiativeValueLabel.setText(String.valueOf(updatePlayerStats.getPlayerInitiative()));

        //Remember to add these to the client view
        damageTextField.setText(String.valueOf(updatePlayerStats.getDamageHP()));
        statusTextField.setText(String.valueOf(updatePlayerStats.getStatusEffect()));
        saveThrowTextField.setText(String.valueOf(updatePlayerStats.getSavingThrow()));
    }
    
}
