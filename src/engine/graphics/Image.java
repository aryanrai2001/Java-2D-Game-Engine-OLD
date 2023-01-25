package engine.graphics;

public class Image
{
	protected int width;
	protected int height;
	protected int[] pixels;
	public final int[] getData()
	{
		return pixels;
	}
	public final int getPixel(int x, int y)
	{
		return pixels[x + y * width];
	}
	public final int getPixelNormalized(float x, float y)
	{
		if (x > 1.0f || y > 1.0f || x < 0.0f || y < 0.0f) return 0xffff00ff;
		return pixels[((int)(x*(width-1)) + (int)(y*(height-1)) * width)];
	}
	public final int getWidth()
	{
		return width;
	}
	public final int getHeight()
	{
		return height;
	}
}
