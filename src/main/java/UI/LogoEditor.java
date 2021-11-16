package UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class LogoEditor {
    public void imageResize(String imagePath, String newImagePath, int height, int width) throws IOException {

        //Taking in image to edit
        File newFile = new File(imagePath);
        BufferedImage imageInput = ImageIO.read(newFile);
        //Image output
        BufferedImage imageOutput = new BufferedImage(height, width, imageInput.getType());
        //drawing
        Graphics2D g2d = imageOutput.createGraphics();
        g2d.drawImage(imageInput, 0, 0, width, height, null);
        g2d.dispose();

        String formatName = newImagePath.substring(newImagePath.lastIndexOf(".") + 1);

        ImageIO.write(imageOutput, formatName, new File(newImagePath));


    }
}
