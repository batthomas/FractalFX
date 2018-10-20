package ch.herzog.fractalfx.component;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class DoubleField extends TextField {

    public DoubleField() {
        setTextFormatter(new TextFormatter<>(change -> {
            if (change.isReplaced()) {
                if (change.getText().matches("[^0-9]")) {
                    change.setText(change.getControlText().substring(change.getRangeStart(), change.getRangeEnd()));
                }
            }
            if (change.isAdded()) {
                if (change.getControlText().contains(".")) {
                    if (change.getText().matches("[^0-9]")) {
                        change.setText("");
                    }
                } else if (change.getText().matches("[^0-9.]")) {
                    change.setText("");
                }
            }
            return change;

        }));
    }

    public Double getValue() {
        return Double.parseDouble(getText());
    }
}
