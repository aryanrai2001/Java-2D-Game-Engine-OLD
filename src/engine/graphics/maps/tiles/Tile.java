package engine.graphics.maps.tiles;

import engine.graphics.Image;
import engine.graphics.Texture;

public class Tile extends Image
{
	public Tile(String path)
	{
		Texture image = new Texture(path);
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.pixels = image.getData();
	}
	public Tile(int width, int height, int color)
	{
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		for (int i = 0; i < pixels.length; i++)
		{
			pixels[i] = color;
		}
	}
	public void setPixel(int x, int y, int color)
	{
		if (x < 0 || x >= width || y < 0 || y >= height) return;
		pixels[x + y * width] = color;
	}
}
