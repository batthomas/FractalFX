package ch.herzog.fractalfx.fractal;

import java.awt.image.BufferedImage;

public abstract class Fractal {

    protected int width;
    protected int height;
    protected double centerX;
    protected double centerY;
    protected double scale;
    protected int iterations;

    protected BufferedImage image;

    public Fractal(int width, int height, double centerX, double centerY, double scale, int iterations) {
        this.width = width;
        this.height = height;
        this.centerX = centerX;
        this.centerY = centerY;
        this.scale = scale;
        this.iterations = iterations;
    }

    public abstract void calculateFractal();

    public abstract Fractal clone();

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public BufferedImage getImage() {
        return image;
    }
}
