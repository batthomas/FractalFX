package ch.herzog.fractalfx.concurrent;

import ch.herzog.fractalfx.fractal.Fractal;

public class ImageGenerationTask extends ProfiledTask<Fractal> {

    private Fractal fractal;

    public ImageGenerationTask(Fractal fractal) {
        this.fractal = fractal;
    }

    @Override
    public Fractal call() {
        setStart(System.nanoTime());

        fractal.calculateFractal();

        setEnd(System.nanoTime());
        return fractal;
    }

    public Fractal getFractal() {
        return fractal;
    }
}
