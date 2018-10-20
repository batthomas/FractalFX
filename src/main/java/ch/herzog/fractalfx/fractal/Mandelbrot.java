package ch.herzog.fractalfx.fractal;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Mandelbrot extends Fractal {

    public Mandelbrot(int width, int height, double centerX, double centerY, double scale, int iterations) {
        super(width, height, centerX, centerY, scale, iterations);
    }

    @Override
    public void calculateFractal() {
        double minX = -2;
        double maxX = 1;
        double minY = -1;
        double maxY = 1;

        double factorX = (maxX - minX) / width;
        double factorY = (minY - maxY) / height;

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int pixelX = 0; pixelX < width; pixelX++) {
            for (int pixelY = 0; pixelY < height; pixelY++) {

                double x = ((pixelX * factorX + minX) / scale) + centerX;
                double y = ((pixelY * factorY + maxX) / scale) + centerY;

                double zRe = 0;
                double zIm = 0;
                int i = 0;

                for (; i < iterations; i++) {
                    double tempZRe = zRe;
                    double tempZIm = zIm;

                    zRe = tempZRe * tempZRe - tempZIm * tempZIm + x;
                    zIm = 2 * tempZRe * tempZIm + y;

                    if (zRe * zRe + zIm * zIm > 4) {
                        break;
                    }
                }
                image.setRGB(pixelX, pixelY, getColor(i));
            }
        }
    }

    @Override
    public Fractal clone() {
        return new Mandelbrot(width, height, centerX, centerY, scale, iterations);
    }

    private int getColor(int i) {
        if (i == iterations) {
            return Color.BLACK.getRGB();
        } else {
            return Color.getHSBColor(i / 255F, 1, 1).getRGB();
        }
    }

    @Override
    public String toString() {
        return "Mandelbrot";
    }
}
