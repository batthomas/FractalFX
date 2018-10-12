package ch.herzog.fractalfx.controller;

import ch.herzog.fractalfx.concurrent.ImageSaveTask;
import ch.herzog.fractalfx.concurrent.MandelbrotImageTask;
import ch.herzog.fractalfx.concurrent.MandelbrotVideoTask;
import ch.herzog.fractalfx.dialogs.ImageCreationDialog;
import ch.herzog.fractalfx.dialogs.VideoCreationDialog;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FractalFXUIController implements Initializable {

    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane canvasContainer;

    @FXML
    private ProgressBar mandelbrotProgress;

    private ImageCreationDialog imageCreationDialog;
    private VideoCreationDialog videoCreationDialog;

    private ExecutorService executor;

    public void initialize(URL location, ResourceBundle resources) {
        executor = Executors.newFixedThreadPool(4);
        imageCreationDialog = new ImageCreationDialog();
        videoCreationDialog = new VideoCreationDialog();
    }

    @FXML
    public void openCreateImageDialog() {
        Optional<MandelbrotImageTask> optional = imageCreationDialog.showAndWait();
        if (optional.isPresent()) {
            MandelbrotImageTask task = optional.get();
            canvasContainer.setPrefSize(task.getWidth(), task.getHeight());
            canvas.setWidth(task.getWidth());
            canvas.setHeight(task.getHeight());
            mandelbrotProgress.progressProperty().bind(task.progressProperty());
            task.setOnSucceeded(event -> {
                BufferedImage image = task.getValue();
                canvas.getGraphicsContext2D().drawImage(SwingFXUtils.toFXImage(image, null), 0, 0);
            });
            executor.submit(task);
        }
    }

    @FXML
    public void openCreateVideoDialog() {
        Optional<MandelbrotVideoTask> optional = videoCreationDialog.showAndWait();
        if (optional.isPresent()) {
            MandelbrotVideoTask task = optional.get();
            canvasContainer.setPrefSize(task.getWidth(), task.getHeight());
            canvas.setWidth(task.getWidth());
            canvas.setHeight(task.getHeight());
            mandelbrotProgress.progressProperty().bind(task.progressProperty());
            task.setOnSucceeded(event -> {
                BufferedImage[] images = task.getValue();
                ImageSaveTask imageSaveTask = new ImageSaveTask(images, new File("./Mandelbrot")); //TODO: Dynamic Save Location
                executor.submit(imageSaveTask);
                mandelbrotProgress.progressProperty().bind(imageSaveTask.progressProperty());
            });
            executor.submit(task);
        }
    }
}
