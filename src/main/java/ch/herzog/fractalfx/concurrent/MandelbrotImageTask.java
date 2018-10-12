package ch.herzog.fractalfx.concurrent;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MandelbrotImageTask extends ProfiledTask<BufferedImage> {

    private int width;
    private int height;
    private double xcenter;
    private double ycenter;
    private double scale;
    private int iterations;

    public MandelbrotImageTask(int width, int height, double xcenter, double ycenter, double scale, int iterations) {
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.xcenter = xcenter;
        this.ycenter = ycenter;
        this.iterations = iterations;
    }

    public MandelbrotImageTask(int width, int height, int iterations) {
        this(width, height, 0.0, 0.0, 1.0, iterations);
    }

    public MandelbrotImageTask(int width, int height) {
        this(width, height, 0.0, 0.0, 1.0, 100);
    }

    @Override
    public BufferedImage call() {
        setStart(System.nanoTime());

        double xmin = -2;
        double xmax = 1;
        double ymin = -1;
        double ymax = 1;

        double xfactor = (xmax - xmin) / width;
        double yfactor = (ymin - ymax) / height;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int xpix = 0; xpix < width; xpix++) {
            for (int ypix = 0; ypix < height; ypix++) {

                double x = ((xpix * xfactor + xmin) / scale) + xcenter;
                double y = ((ypix * yfactor + ymax) / scale) + ycenter;

                double zre = 0;
                double zim = 0;
                int i = 0;

                for (; i < iterations; i++) {
                    double tzre = zre;
                    double tzim = zim;

                    zre = tzre * tzre - tzim * tzim + x;
                    zim = 2 * tzre * tzim + y;

                    if (zre * zre + zim * zim > 4) {
                        break;
                    }
                }
                image.setRGB(xpix, ypix, getMandelbrotColor(i));
            }
            updateProgress(xpix + 1, width);
        }

        setEnd(System.nanoTime());
        return image;
    }


    private int getMandelbrotColor(int i) {
        if (i == iterations) {
            return Color.BLACK.getRGB();
        } else {
            return Color.getHSBColor(i / 255F, 1, 1).getRGB();

        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getXCenter() {
        return xcenter;
    }

    public double getYcenter() {
        return ycenter;
    }

    public double getScale() {
        return scale;
    }

    public int getIterations() {
        return iterations;
    }
}
