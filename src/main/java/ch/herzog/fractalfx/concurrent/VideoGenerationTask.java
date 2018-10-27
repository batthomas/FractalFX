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
                fractal = fractal.clone();
                fractal.setScale(fractal.getScale() + zoomPerSecond / framesPerSecond);
                fractal.calculateFractal();
                fractals[second * framesPerSecond + frame] = fractal;
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
