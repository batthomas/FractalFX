package ch.herzog.fractalfx.controller;

import ch.herzog.fractalfx.concurrent.ImageGenerationTask;
import ch.herzog.fractalfx.concurrent.VideoGenerationTask;
import ch.herzog.fractalfx.dialog.ImageCreationDialog;
import ch.herzog.fractalfx.dialog.VideoCreationDialog;
import ch.herzog.fractalfx.fractal.Fractal;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FractalFXUIController implements Initializable {

    @FXML
    private Canvas canvas;

    @FXML
    private ProgressBar mandelbrotProgress;

    @FXML
    private AnchorPane canvasContainer;

    @FXML
    private TreeView<Object> treeView;

    private ImageCreationDialog imageCreationDialog;
    private VideoCreationDialog videoCreationDialog;

    private ExecutorService executor;

    public void initialize(URL location, ResourceBundle resources) {
        executor = Executors.newFixedThreadPool(4);

        imageCreationDialog = new ImageCreationDialog();
        videoCreationDialog = new VideoCreationDialog();

        treeView.setRoot(new TreeItem<>("Mandelbrot"));
        treeView.setShowRoot(false);
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getValue() instanceof Fractal) {
                Fractal fractal = (Fractal) newValue.getValue();
                canvas.setWidth(fractal.getWidth());
                canvas.setHeight(fractal.getHeight());
                canvasContainer.setPrefSize(fractal.getWidth(), fractal.getHeight());
                canvas.getGraphicsContext2D().drawImage(SwingFXUtils.toFXImage(fractal.getImage(), null), 0, 0);
            } else {
                canvas.setWidth(0);
                canvas.setHeight(0);
            }
        });
    }

    @FXML
    public void openCreateImageDialog() {
        Optional<ImageGenerationTask> optional = imageCreationDialog.showAndWait();
        if (optional.isPresent()) {
            ImageGenerationTask task = optional.get();
            Fractal fractal = task.getFractal();
            canvas.setWidth(fractal.getWidth());
            canvas.setHeight(fractal.getHeight());
            task.setOnSucceeded(event -> {
                TreeItem<Object> item = new TreeItem<>(fractal);
                treeView.getRoot().getChildren().add(item);
                canvas.getGraphicsContext2D().drawImage(SwingFXUtils.toFXImage(fractal.getImage(), null), 0, 0);
                treeView.getSelectionModel().select(item);
            });
            executor.submit(task);
        }
    }

    @FXML
    public void openCreateVideoDialog() {
        Optional<VideoGenerationTask> optional = videoCreationDialog.showAndWait();
        if (optional.isPresent()) {
            VideoGenerationTask task = optional.get();
            canvas.setWidth(task.getFractal().getWidth());
            canvas.setHeight(task.getFractal().getHeight());
            canvasContainer.setPrefSize(task.getFractal().getWidth(), task.getFractal().getHeight());
            mandelbrotProgress.progressProperty().bind(task.progressProperty());
            task.setOnSucceeded(event -> {
                Fractal[] fractals = task.getValue();
                TreeItem<Object> frames = new TreeItem<>("Video");
                for (Fractal fractal : fractals) {
                    frames.getChildren().add(new TreeItem<>(fractal));
                }
                treeView.getRoot().getChildren().add(frames);
                treeView.getSelectionModel().select(frames);
            });
            executor.submit(task);
        }
    }

}
