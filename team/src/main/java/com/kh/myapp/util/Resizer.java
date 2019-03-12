package com.kh.myapp.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resizer {
	
	public static void resizeImage(String inputFile, String outputFile) throws IOException {
		File input = new File(inputFile);
        BufferedImage image = ImageIO.read(input);

        BufferedImage resized = resize(image, 500, 500);

        File output = new File(outputFile);
        ImageIO.write(resized, "png", output);
	}
	
	private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
