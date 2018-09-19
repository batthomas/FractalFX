package ch.herzog.fractalfx.controller;

import ch.herzog.fractalfx.concurrent.MandelbrotTask;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.UnaryOperator;

public class FractalFXUIController implements Initializable {

    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane canvasContainer;

    @FXML
    private ProgressBar mandelbrotProgress;

    @FXML
    private TextField widthInput, heightInput, iterationsInput;

    private ExecutorService executor;

    public void initialize(URL location, ResourceBundle resources) {
        executor = Executors.newFixedThreadPool(4);

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        widthInput.setTextFormatter(new TextFormatter<>(integerFilter));
        heightInput.setTextFormatter(new TextFormatter<>(integerFilter));
        iterationsInput.setTextFormatter(new TextFormatter<>(integerFilter));
    }

    @FXML
    public void calculateMandelbrot() {
        int width = Integer.parseInt(widthInput.getText());
        int height = Integer.parseInt(heightInput.getText());
        int iterations = Integer.parseInt(iterationsInput.getText());

        canvasContainer.setPrefSize(width, height);
        canvas.setWidth(width);
        canvas.setHeight(height);

        MandelbrotTask task = new MandelbrotTask(width, height, iterations);
        task.setOnRunning(event -> mandelbrotProgress.setProgress(event.getSource().getProgress()));
        task.setOnSucceeded(event -> {
            mandelbrotProgress.setProgress(0);
            BufferedImage image = task.getValue();
            canvas.getGraphicsContext2D().drawImage(SwingFXUtils.toFXImage(image, null), 0, 0);
            System.out.println((task.getExecutionTime()) + "ns");
            System.out.println((task.getExecutionTime() / 1000000) + "ms");
            System.out.println((task.getExecutionTime() / 1000000 / 1000) + "s\n");
        });
        executor.submit(task);
    }

}
