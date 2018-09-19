package ch.herzog.fractalfx.concurrent;

import javafx.concurrent.Task;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MandelbrotTask extends Task<BufferedImage> {

    private int width;
    private int height;
    private int iterations;

    private long start;
    private long end;

    public MandelbrotTask(int width, int height, int iterations) {
        this.iterations = iterations;
        this.width = width;
        this.height = height;
    }

    @Override
    public BufferedImage call() {
        start = System.nanoTime();

        double xmin = -2;
        double xmax = 1;
        double ymin = -1;
        double ymax = 1;

        double xfactor = (xmax - xmin) / width;
        double yfactor = (ymin - ymax) / height;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int xpix = 0; xpix < width; xpix++) {
            for (int ypix = 0; ypix < height; ypix++) {

                double x = (double) xpix * xfactor + -2;
                double y = (double) ypix * yfactor + 1;
                double ax = 0;
                double ay = 0;
                int i = 0;

                for (; i < iterations; i++) {
                    double axn = ax;
                    double ayn = ay;
                    ax = axn * axn - ayn * ayn + x;
                    ay = 2 * axn * ayn + y;

                    if (ax * ax + ay * ay > 4) {
                        break;
                    }
                }

                image.setRGB(xpix, ypix, getMandelbrotColor(i, ax, ay));
            }
        }

        end = System.nanoTime();
        return image;
    }


    private int getMandelbrotColor(int i, double x, double y) {
        if (i == iterations) {
            return Color.BLACK.getRGB();
        } else {
            return Color.getHSBColor((float) i / iterations + 0.4F, 1, 1).getRGB();

        }
    }

    public long getExecutionTime() {
        return end - start;
    }

}
