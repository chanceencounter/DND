package com.ericarao.dnd.core.View;

import javafx.scene.control.TextField;

public class InputValidation {

    //Need to include threads for listening to keystrokes.
    private final String regex = "\\d+";

    //Number or Digit Input Validation
    public void numberValidation(TextField textField) {

        boolean isPressed = false;
        //Numbers
        while (textField.getOnKeyPressed().equals(isPressed)){
            if (textField.getOnKeyPressed().equals(regex)) {
                textField.setEditable(true);
            }
            else {
                textField.setEditable(false);
            }
        }

    }

    public String valid (TextField textField) {
        if (!textField.getOnKeyPressed().equals(regex)){
            return "";
        }
        else return "Invalid input.";
    }


}
