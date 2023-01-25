package engine.system;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import engine.graphics.Camera;
import engine.graphics.Image;
import engine.graphics.maps.tiles.Tilemap;
import engine.physics.maths.Vector2f;

public class Renderer
{
	private final Camera camera;
	private final int[] pixels;
	private final int width;
	private final int height;
	private boolean alpha;
	
	public Renderer(BufferedImage image, Camera camera)
	{
		this.pixels = ((DataBufferInt)(image.getRaster().getDataBuffer())).getData();
		this.camera = camera;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.alpha = false;
	}
	
	public int getPixel(Vector2f pos)
	{
		return getPixel((int)pos.x, (int)pos.y);
	}
	
	public int getPixel(int x, int y)
	{
		if (x < 0 || x >= width || y < 0 || y >= height) return 0;
		return pixels[x + y * width] & 0xffffff;
	}

	public void setPixel(Vector2f pos, int color, boolean clipped)
	{
		rawSetPixel((int) (pos.x - camera.getX()), (int) (pos.y - camera.getY()), color, clipped);
	}

	public void setPixel(int x, int y, int color, boolean clipped)
	{
		rawSetPixel((int) (x - camera.getX()), (int) (y - camera.getY()), color, clipped);
	}
	
	public void rawSetPixel(Vector2f pos, int color, boolean clipped)
	{
		rawSetPixel((int) pos.x, (int) pos.y, color, clipped);
	}
	
	public void rawSetPixel(int x, int y, int color, boolean clipped)
	{
		int alphaNorm = (color >> 24) & 255;
		if (alpha && alphaNorm == 0) return;
		if (clipped)
		{
			if (x < 0 || x >= width || y < 0 || y >= height) return;
		}
		else
		{
			if (x < 0) x += width;
			else if (x >= width) x -= width;
			if (y < 0) y += height;
			else if (y >= height) y -= height;
		}
		if (!alpha || alphaNorm == 255)
		{
			pixels[x + y * width] = color;
		}
		else
		{
			int pixelColor = pixels[x + y * width];
			float sr = ((color >> 16) & 255)/255.0f;
			float sg = ((color >> 8) & 255)/255.0f;
			float sb = ((color) & 255)/255.0f;
			float dr = ((pixelColor >> 16) & 255)/255.0f;
			float dg = ((pixelColor >> 8) & 255)/255.0f;
			float db = ((pixelColor) & 255)/255.0f;
			float srcAlpha = alphaNorm/255.0f;
			float destAlpha = ((pixelColor >> 24) & 255)/255.0f;
			float compAlpha = srcAlpha + (1-srcAlpha) * destAlpha;
			float cr = ((sr*srcAlpha)+(1-srcAlpha)*dr*destAlpha)/compAlpha;
			float cg = ((sg*srcAlpha)+(1-srcAlpha)*dg*destAlpha)/compAlpha;
			float cb = ((sb*srcAlpha)+(1-srcAlpha)*db*destAlpha)/compAlpha;
			pixels[x + y * width] = ((int)(255*compAlpha) << 24) | ((int)(255*cr) << 16) | ((int)(255*cg) << 8) | ((int)(255*cb));
		}
	}
	
	public void drawLine(Vector2f s, Vector2f e, int color, boolean clipped)
	{
		drawLine((int)s.x, (int)s.y, (int)e.x, (int)e.y, color, clipped);
	}
	
	public void drawLine(int x1, int y1, int x2, int y2, int color, boolean clipped)
	{
		int x, y, dx, dy, dx1, dy1, px, py, xe, ye;
		dx = x2 - x1; dy = y2 - y1;

		if (dx == 0)
		{
			if (y2 < y1)
			{
				for (y = y2; y <= y1; y++)
					setPixel(x1, y, color, clipped);
			}
			else
			{
				for (y = y1; y <= y2; y++)
					setPixel(x1, y, color, clipped);
			}
			return;
		}

		if (dy == 0)
		{
			if (x2 < x1)
			{
				for (x = x2; x <= x1; x++)
					setPixel(x, y1, color, clipped);
			}
			else
			{
				for (x = x1; x <= x2; x++)
					setPixel(x, y1, color, clipped);
			}
			return;
		}

		dx1 = Math.abs(dx); dy1 = Math.abs(dy);
		px = 2 * dy1 - dx1;	py = 2 * dx1 - dy1;
		if (dy1 <= dx1)
		{
			if (dx >= 0)
			{
				x = x1; y = y1; xe = x2;
			}
			else
			{
				x = x2; y = y2; xe = x1;
			}

			setPixel(x, y, color, clipped);

			while(x<xe)
			{
				x = x + 1;
				if (px<0)
					px = px + 2 * dy1;
				else
				{
					if ((dx<0 && dy<0) || (dx>0 && dy>0)) y = y + 1; else y = y - 1;
					px = px + 2 * (dy1 - dx1);
				}
				setPixel(x, y, color, clipped);
			}
		}
		else
		{
			if (dy >= 0)
			{
				x = x1; y = y1; ye = y2;
			}
			else
			{
				x = x2; y = y2; ye = y1;
			}

			setPixel(x, y, color, clipped);

			while(y<ye)
			{
				y = y + 1;
				if (py <= 0)
					py = py + 2 * dx1;
				else
				{
					if ((dx<0 && dy<0) || (dx>0 && dy>0)) x = x + 1; else x = x - 1;
					py = py + 2 * (dx1 - dy1);
				}
				setPixel(x, y, color, clipped);
			}
		}
	}
	
	public void drawTriangle(Vector2f v1, Vector2f v2, Vector2f v3, int color, boolean clipped)
	{
		drawLine(v1, v2, color, clipped);
		drawLine(v2, v3, color, clipped);
		drawLine(v3, v1, color, clipped);
	}
	
	public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int color, boolean clipped)
	{
		drawLine(x1, y1, x2, y2, color, clipped);
		drawLine(x2, y2, x3, y3, color, clipped);
		drawLine(x3, y3, x1, y1, color, clipped);
	}
	
	public void fillTriangle(Vector2f v1, Vector2f v2, Vector2f v3, int color, boolean clipped)
	{
		fillTriangle((int)v1.x, (int)v1.y, (int)v2.x, (int)v2.y, (int)v3.x, (int)v3.y, color, clipped);
	}

	public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int color, boolean clipped)
	{

		int t1x, t2x, y, minx, maxx, t1xp, t2xp;
		boolean changed1 = false;
		boolean changed2 = false;
		int signx1, signx2, dx1, dy1, dx2, dy2;
		int e1, e2;
		
		if (y1>y2) 
		{ 
			int temp = y1;
			y1 = y2;
			y2 = temp;
			temp = x1;
			x1 = x2;
			x2 = temp;
		}
		if (y1>y3) 
		{ 
			int temp = y1;
			y1 = y3;
			y3 = temp;
			temp = x1;
			x1 = x3;
			x3 = temp;
		}
		if (y2>y3) 
		{ 
			int temp = y2;
			y2 = y3;
			y3 = temp;
			temp = x2;
			x2 = x3;
			x3 = temp;
		}

		t1x = t2x = x1; y = y1;
		dx1 = x2 - x1; if (dx1<0) { dx1 = -dx1; signx1 = -1; }
		else signx1 = 1;
		dy1 = y2 - y1;

		dx2 = x3 - x1; if (dx2<0) { dx2 = -dx2; signx2 = -1; }
		else signx2 = 1;
		dy2 = y3 - y1;

		if (dy1 > dx1) {
			int temp = dx1;
			dx1 = dy1;
			dy1 = temp;
			changed1 = true;
		}
		if (dy2 > dx2) {
			int temp = dx2;
			dx2 = dy2;
			dy2 = temp;
			changed2 = true;
		}

		e2 = dx2 >> 1;
		if (!(y1 == y2))
		{
			e1 = dx1 >> 1;
			
			for (int i = 0; i < dx1;) 
			{
				t1xp = 0; t2xp = 0;
				if (t1x<t2x) { minx = t1x; maxx = t2x; }
				else { minx = t2x; maxx = t1x; }

				next1:
				while (i<dx1) 
				{
					i++;
					e1 += dy1;
					while (e1 >= dx1)
					{
						e1 -= dx1;
						if (changed1) t1xp = signx1;
						else break next1;
					}
					if (changed1) break;
					else t1x += signx1;
				}
				next2:
				while (true)
				{
					e2 += dy2;
					while (e2 >= dx2)
					{
						e2 -= dx2;
						if (changed2) t2xp = signx2;
						else break next2;
					}
					if (changed2)     break;
					else              t2x += signx2;
				}
				if (minx>t1x) minx = t1x; if (minx>t2x) minx = t2x;
				if (maxx<t1x) maxx = t1x; if (maxx<t2x) maxx = t2x;
				for (int x = minx; x <= maxx; x++)
					setPixel(x, y, color, clipped);
				if (!changed1) t1x += signx1;
				t1x += t1xp;
				if (!changed2) t2x += signx2;
				t2x += t2xp;
				y += 1;
				if (y == y2) break;
			}
		}
		dx1 = x3 - x2; if (dx1<0) { dx1 = -dx1; signx1 = -1; }
		else signx1 = 1;
		dy1 = y3 - y2;
		t1x = x2;

		if (dy1 > dx1) {
			int temp = dx1;
			dx1 = dy1;
			dy1 = temp;
			changed1 = true;
		}
		else changed1 = false;

		e1 = dx1 >> 1;

		for (int i = 0; i <= dx1; i++) 
		{
			t1xp = 0; t2xp = 0;
			if (t1x<t2x) { minx = t1x; maxx = t2x; }
			else { minx = t2x; maxx = t1x; }
			next3:
			while (i<dx1) 
			{
				e1 += dy1;
				while (e1 >= dx1) 
				{
					e1 -= dx1;
					if (changed1) { t1xp = signx1; break; }
					else break next3;
				}
				if (changed1) break;
				else   	   	  t1x += signx1;
				if (i<dx1) i++;
			}
			next4:
			while (t2x != x3) 
			{
				e2 += dy2;
				while (e2 >= dx2) 
				{
					e2 -= dx2;
					if (changed2) t2xp = signx2;
					else break next4;
				}
				if (changed2)     break;
				else              t2x += signx2;
			}

			if (minx>t1x) minx = t1x; if (minx>t2x) minx = t2x;
			if (maxx<t1x) maxx = t1x; if (maxx<t2x) maxx = t2x;
			for (int x = minx; x <= maxx; x++)
				setPixel(x, y, color, clipped);
			if (!changed1) t1x += signx1;
			t1x += t1xp;
			if (!changed2) t2x += signx2;
			t2x += t2xp;
			y += 1;
			if (y>y3) return;
		}
	}
	
	public void drawPolygon(int[] x, int[] y, int color, boolean clipped)
	{
		int n = x.length;
		for (int i = 0; i < n; i++)
		{
			drawLine(x[i % n], y[i % n], x[(i+1) % n], y[(i+1) % n], color, clipped);
		}
	}
	
	public void drawPolygon(float[] x, float[] y, int color, boolean clipped)
	{
		int n = x.length;
		for (int i = 0; i < n; i++)
		{
			drawLine((int)x[i % n], (int)y[i % n], (int)x[(i+1) % n], (int)y[(i+1) % n], color, clipped);
		}
	}
	
	public void drawRect(Vector2f s, Vector2f e, int color, boolean clipped)
	{
		drawRect((int)s.x, (int)s.y, (int)e.x, (int)e.y, color, clipped);
	}
	
	public void drawRect(int x1, int y1, int x2, int y2, int color, boolean clipped)
	{
		for (int y = y1; y < y1+y2; y++)
		{
			setPixel(x1, y, color, clipped);
			setPixel(x1+x2, y, color, clipped);
		}
		for (int x = x1; x < x1+x2; x++)
		{
			setPixel(x, y1, color, clipped);
			setPixel(x, y1+y2, color, clipped);
		}
	}
	
	public void fillRect(Vector2f s, Vector2f e, int color, boolean clipped)
	{
		fillRect((int)s.x, (int)s.y, (int)e.x, (int)e.y, color, clipped);
	}
	
	public void fillRect(int x1, int y1, int x2, int y2, int color, boolean clipped)
	{
		for (int y = y1; y < y1+y2; y++)
		{
			for (int x = x1; x < x1+x2; x++)
			{
				setPixel(x, y, color, clipped);
			}
		}
	}
	
	public void drawCircle(Vector2f pos, int radius, int color, boolean clipped)
	{
		drawCircle((int)pos.x, (int)pos.y, radius, color, clipped);
	}
	
	public void drawCircle(int x, int y, int radius, int color, boolean clipped)
	{
		if (radius <= 0) return;
		int x0 = 0;
		int y0 = radius;
		int d = 3 - 2 * radius;

		while (y0 >= x0)
		{
			setPixel(x + x0, y - y0, color, clipped);
			setPixel(x + y0, y - x0, color, clipped);
			setPixel(x + y0, y + x0, color, clipped);
			setPixel(x + x0, y + y0, color, clipped);
			setPixel(x - x0, y + y0, color, clipped);
			setPixel(x - y0, y + x0, color, clipped);
			setPixel(x - y0, y - x0, color, clipped);
			setPixel(x - x0, y - y0, color, clipped);														
			if (d < 0) d += 4 * x0++ + 6;
			else d += 4 * (x0++ - y0--) + 10;
		}
	}

	public void fillCircle(Vector2f pos, int radius, int color, boolean clipped)
	{
		fillCircle((int)pos.x, (int)pos.y, radius, color, clipped);
	}
	
	public void fillCircle(int x, int y, int radius, int color, boolean clipped)
	{
		if (radius <= 0) return;
		int x0 = 0;
		int y0 = radius;
		int d = 3 - 2 * radius;

		while (y0 >= x0)
		{
			for (int i = x - x0; i <= x + x0; i++)
				setPixel(i, y - y0, color, clipped);
			for (int i = x - y0; i <= x + y0; i++)
				setPixel(i, y - x0, color, clipped);
			for (int i = x - x0; i <= x + x0; i++)
				setPixel(i, y + y0, color, clipped);
			for (int i = x - y0; i <= x + y0; i++)
				setPixel(i, y + x0, color, clipped);
			if (d < 0) d += 4 * x0++ + 6;
			else d += 4 * (x0++ - y0--) + 10;
		}
	}
	
	public void drawImage(Vector2f pos, Image image, boolean clipped)
	{
		drawImage((int)pos.x, (int)pos.y, image, clipped);
	}
	
	public void drawImage(int xOff, int yOff, Image image, boolean clipped)
	{
		if (xOff < 0 || xOff >= width || yOff < 0 || yOff >= height ) return;
		for (int y = 0; y < image.getHeight(); y++)
		{
			for (int x = 0; x < image.getWidth(); x++)
			{
				setPixel(x + xOff, y + yOff, image.getPixel(x, y), clipped);
			}
		}
	}
	
	public void drawTilemap(Vector2f pos, Tilemap map)
	{
		drawTilemap((int)pos.x, (int)pos.y, map);
	}
	
	public void drawTilemap(int xOff, int yOff, Tilemap map)
	{
		for (int y = 0; y < map.numTileY; y++)
		{
			for (int x = 0; x < map.numTileX; x++)
			{
				drawImage(xOff + (map.tileWidth * x), yOff + (map.tileHeight * y), map.getTile(x, y), true);
			}
		}
	}
	
	public void clear(int color)
	{
		Arrays.fill(pixels, color);
	}
	
	public void setAlpha(boolean alpha)
	{
		this.alpha = alpha;
	}

}
