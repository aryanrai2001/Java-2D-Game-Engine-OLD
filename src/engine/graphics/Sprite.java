package engine.graphics;

public class Sprite extends Image
{
	public Sprite(Image img)
	{
		this.width = img.width;
		this.height = img.height;
		this.pixels = img.pixels;
	}
	public Sprite(int w, int h)
	{
		this.width = w;
		this.height = h;
		this.pixels = new int[width * height];
	}
	public final void setPixel(int x, int y, int color)
	{
		if (x < 0 || x >= width || y < 0 || y >= height) return;
		pixels[x + y * width] = color;
	}
}
