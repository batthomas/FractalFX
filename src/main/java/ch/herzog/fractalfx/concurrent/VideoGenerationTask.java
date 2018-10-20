package ch.herzog.fractalfx.concurrent;

import ch.herzog.fractalfx.fractal.Fractal;

public class VideoGenerationTask extends ProfiledTask<Fractal[]> {

    private Fractal fractal;
    private double zoomPerSecond;
    private int seconds;
    private int framesPerSecond;

    public VideoGenerationTask(Fractal fractal, int seconds, int framesPerSecond, double zoomPerSecond) {
        this.fractal = fractal;
        this.seconds = seconds;
        this.framesPerSecond = framesPerSecond;
        this.zoomPerSecond = zoomPerSecond;
    }

    @Override
    protected Fractal[] call() {
        Fractal[] fractals = new Fractal[seconds * framesPerSecond];
        for (int second = 0; second < seconds; second++) {
            for (int frame = 0; frame < framesPerSecond; frame++) {
                Fractal copy = fractal.clone();
                copy.setScale(copy.getScale() + (second * framesPerSecond + frame) * zoomPerSecond / framesPerSecond);
                copy.calculateFractal();
                fractals[second * framesPerSecond + frame] = copy;
                updateProgress(second * framesPerSecond + frame, seconds * framesPerSecond - 1);
            }
        }
        return fractals;
    }

    public double getZoomPerSecond() {
        return zoomPerSecond;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public Fractal getFractal() {
        return fractal;
    }
}
