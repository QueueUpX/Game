package Main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class BufferedImageLoader {
	private BufferedImage image;

	public BufferedImage loadImage(String path) {
		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load image: " + path);
		}
		return image;
	}

	public ImageIcon loadImgIcon(String path) {
		ImageIcon img=new ImageIcon(path);
		return img;
	}
}
