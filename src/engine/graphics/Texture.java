package engine.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture extends Image
{
	public Texture(String path)
	{
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(this.getClass().getResource(path));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.pixels = image.getRGB(0, 0, width, height, null, 0, width);
	}
}
