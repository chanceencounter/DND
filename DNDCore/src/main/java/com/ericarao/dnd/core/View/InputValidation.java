package com.ericarao.dnd.core.View;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class InputValidation {

    //Need to include threads for listening to keystrokes.
    private final String regex = "[1-9]";
    //private boolean isPressed = false;

    //Number or Digit Input Validation
    public void numberValidation(TextField textField) {

        textField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //isPressed = true;
                //Error: For some reason the key pressed is capturing "null"
                if (textField.getText().matches(regex) && Integer.parseInt(textField.getText()) < 9) {
                    //isPressed = false;
                } else {
                    textField.setEditable(false);
                    textField.setText("");
                    textField.setEditable(true);

                }
            }
        });

        /*textField.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                isPressed = false;
                textField.setEditable(true);
            }
        });*/

    }


    /*
        public String valid(TextField textField) {
        if (!textField.getOnKeyPressed().equals(regex)) {
            return "";
        } else return "Invalid input.";
    }
    */
}
