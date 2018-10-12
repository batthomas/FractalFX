package ch.herzog.fractalfx.concurrent;

import javafx.concurrent.Task;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSaveTask extends Task<Boolean> {

    private BufferedImage[] images;
    private File path;

    public ImageSaveTask(BufferedImage image, File path) {
        images = new BufferedImage[]{image};
        this.path = path;
    }

    public ImageSaveTask(BufferedImage[] images, File path) {
        this.images = images;
        this.path = path;
    }

    @Override
    protected Boolean call() {
        try {
            path.mkdirs();
            for (int i = 0; i < images.length; i++) {
                String filename = "img" + String.format("%0" + ("" + images.length).length() + "d", i) + ".png";
                ImageIO.write(images[i], "png", new File(path, filename));
                updateProgress(i, images.length - 1);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
