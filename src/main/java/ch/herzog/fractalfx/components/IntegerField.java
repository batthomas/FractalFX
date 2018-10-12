package ch.herzog.fractalfx.components;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class IntegerField extends TextField {

    public IntegerField() {
        setTextFormatter(new TextFormatter<>(change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        }));
    }

    public Integer getValue() {
        return Integer.parseInt(getText());
    }
}
