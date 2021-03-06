package ch.herzog.fractalfx.dialog;

import ch.herzog.fractalfx.component.DoubleField;
import ch.herzog.fractalfx.component.IntegerField;
import ch.herzog.fractalfx.concurrent.VideoGenerationTask;
import ch.herzog.fractalfx.fractal.Mandelbrot;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

public class VideoCreationDialog extends Dialog<VideoGenerationTask> {

    private IntegerField widthSpinner;
    private IntegerField heightSpinner;
    private DoubleField centerXSpinner;
    private DoubleField centerYSpinner;
    private DoubleField scaleSpinner;
    private IntegerField iterationsSpinner;
    private IntegerField framesPerSecondSpinner;
    private IntegerField secondsSpinner;
    private DoubleField zoomPerSecondSpinner;

    public VideoCreationDialog() {
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Generate Video");
        setHeaderText("Generate Mandelbrot Video");

        getDialogPane().setContent(initGrid());

        ButtonType generateButton = new ButtonType("Generate", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(generateButton, ButtonType.CANCEL);

        setResultConverter(dialogButton -> {
            if (dialogButton == generateButton) {
                return new VideoGenerationTask(
                        new Mandelbrot(
                                widthSpinner.getValue(),
                                heightSpinner.getValue(),
                                centerXSpinner.getValue(),
                                centerYSpinner.getValue(),
                                scaleSpinner.getValue(),
                                iterationsSpinner.getValue()
                        ),
                        secondsSpinner.getValue(),
                        framesPerSecondSpinner.getValue(),
                        zoomPerSecondSpinner.getValue()
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
        widthSpinner.setEditable(true);
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

        framesPerSecondSpinner = new IntegerField();
        framesPerSecondSpinner.setEditable(true);
        grid.add(new Label("Frames per second:"), 0, 6);
        grid.add(framesPerSecondSpinner, 1, 6);

        secondsSpinner = new IntegerField();
        secondsSpinner.setEditable(true);
        grid.add(new Label("Seconds:"), 0, 7);
        grid.add(secondsSpinner, 1, 7);

        zoomPerSecondSpinner = new DoubleField();
        zoomPerSecondSpinner.setEditable(true);
        grid.add(new Label("Zoom per second:"), 0, 8);
        grid.add(zoomPerSecondSpinner, 1, 8);

        return grid;
    }
}
