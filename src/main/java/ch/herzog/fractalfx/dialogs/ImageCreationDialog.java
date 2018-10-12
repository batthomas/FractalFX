package ch.herzog.fractalfx.dialogs;

import ch.herzog.fractalfx.components.DoubleField;
import ch.herzog.fractalfx.components.IntegerField;
import ch.herzog.fractalfx.concurrent.MandelbrotImageTask;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

public class ImageCreationDialog extends Dialog<MandelbrotImageTask> {

    private IntegerField widthSpinner;
    private IntegerField heightSpinner;
    private DoubleField centerXSpinner;
    private DoubleField centerYSpinner;
    private DoubleField scaleSpinner;
    private IntegerField iterationsSpinner;

    public ImageCreationDialog() {
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Generate Image");
        setHeaderText("Generate Mandelbrot Image");

        getDialogPane().setContent(initGrid());

        ButtonType generateButton = new ButtonType("Generate", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(generateButton, ButtonType.CANCEL);

        setResultConverter(dialogButton -> {
            if (dialogButton == generateButton) {
                return new MandelbrotImageTask(
                        widthSpinner.getValue(),
                        heightSpinner.getValue(),
                        centerXSpinner.getValue(),
                        centerYSpinner.getValue(),
                        scaleSpinner.getValue(),
                        iterationsSpinner.getValue()
                );
            }
            return null;
        });
    }

    private GridPane initGrid() {
        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 50, 20, 20));

        widthSpinner = new IntegerField();
        grid.add(new Label("Width:"), 0, 0);
        grid.add(widthSpinner, 1, 0);

        heightSpinner = new IntegerField();
        heightSpinner.setEditable(true);
        grid.add(new Label("Height:"), 0, 1);
        grid.add(heightSpinner, 1, 1);

        centerXSpinner = new DoubleField();
        centerXSpinner.setEditable(true);
        grid.add(new Label("Center X:"), 0, 2);
        grid.add(centerXSpinner, 1, 2);

        centerYSpinner = new DoubleField();
        centerYSpinner.setEditable(true);
        grid.add(new Label("Center Y:"), 0, 3);
        grid.add(centerYSpinner, 1, 3);

        scaleSpinner = new DoubleField();
        scaleSpinner.setEditable(true);
        grid.add(new Label("Scale:"), 0, 4);
        grid.add(scaleSpinner, 1, 4);

        iterationsSpinner = new IntegerField();
        iterationsSpinner.setEditable(true);
        grid.add(new Label("Iterations:"), 0, 5);
        grid.add(iterationsSpinner, 1, 5);

        return grid;
    }
}
