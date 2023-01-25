package engine.physics.collider;

import engine.graphics.Image;
import engine.physics.maths.Vector2f;

public class BitmapCollider
{
	private byte[] alphas;
	private int width, height;
	private Vector2f dist;
	public BitmapCollider(Image img)
	{
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.alphas = new byte[width*height];
		this.dist = null;
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				alphas[x + y * width] = (byte) (((img.getPixel(x, y) >> 24) != 0)? 1 : 0);
			}
		}
	}
	public boolean pointCollision(Vector2f pos, Vector2f point)
	{
		dist = point.sub(pos);
		int x = (int) dist.x;
		int y = (int) dist.y;
		if (dist.inQuadrant() == 1 && x >= 0 && x < width && y >=0 && y < height)
			return alphas[x + y * width] == 1;
		return false;
	}
}
