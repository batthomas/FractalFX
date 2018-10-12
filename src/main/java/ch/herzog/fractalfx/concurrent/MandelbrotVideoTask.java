package ch.herzog.fractalfx.concurrent;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MandelbrotVideoTask extends ProfiledTask<BufferedImage[]> {

    private int width;
    private int height;
    private double centerX;
    private double centerY;
    private double zoomPerSecond;
    private double scale;
    private int iterations;
    private int seconds;
    private int framesPerSecond;

    private ExecutorService executor;

    public MandelbrotVideoTask(int width, int height, double centerX, double centerY, int iterations, int seconds, int framesPerSecond, double zoomPerSecond) {
        this.width = width;
        this.height = height;
        this.centerX = centerX;
        this.centerY = centerY;
        this.iterations = iterations;
        this.seconds = seconds;
        this.framesPerSecond = framesPerSecond;
        this.zoomPerSecond = zoomPerSecond;
        scale = 1.0;
        executor = Executors.newFixedThreadPool(4);
    }

    @Override
    protected BufferedImage[] call() throws Exception {
        BufferedImage[] images = new BufferedImage[seconds * framesPerSecond];
        List<Future> futures = new ArrayList<>();

        for (int second = 0; second < seconds; second++) {
            for (int frame = 0; frame < framesPerSecond; frame++) {
                MandelbrotImageTask mandelbrotImageTask = new MandelbrotImageTask(width, height, centerX, centerY, scale, iterations);
                final int fsecond = second;
                final int fframe = frame;
                mandelbrotImageTask.setOnSucceeded(event -> {
                    BufferedImage image = mandelbrotImageTask.getValue();
                    images[fsecond * framesPerSecond + fframe] = image;
                });
                futures.add(executor.submit(mandelbrotImageTask));
                scale += zoomPerSecond / framesPerSecond;
            }
        }
        while (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
            futures.removeIf(Future::isDone);
            updateProgress(seconds * framesPerSecond - futures.size(), seconds * framesPerSecond);
            if (futures.size() == 0) {
                break;
            }
        }
        return images;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getZoomPerSecond() {
        return zoomPerSecond;
    }

    public double getScale() {
        return scale;
    }

    public int getIterations() {
        return iterations;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }
}
