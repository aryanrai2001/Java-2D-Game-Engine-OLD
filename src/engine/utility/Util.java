package engine.utility;

import engine.physics.maths.Vector2f;

public class Util
{
	
	public static int width, height;

	public static int colorIntensity(int color, int signedPercentageChange)
	{
		if (signedPercentageChange == 0) return color;
		int finalColor = color & 0xff000000;
		int r = (color & 0x00ff0000) >> 16;
		int g = (color & 0x0000ff00) >> 8;
		int b = (color & 0x000000ff) >> 0;
		int dR = (int)(r * signedPercentageChange/100.0f);
		int dG = (int)(g * signedPercentageChange/100.0f);
		int dB = (int)(b * signedPercentageChange/100.0f);
		r += dR;
		g += dG;
		b += dB;
		if (r < 0) r = 0;
		if (r > 255) r = 255;
		if (g < 0) g = 0;
		if (g > 255) g = 255;
		if (b < 0) b = 0;
		if (b > 255) b = 255;
		finalColor = finalColor | r << 16 | g << 8 | b;
		return finalColor;
	}
	
	public static float normalize(float value, float min, float max)
	{
		return (value-min)/(max-min);
	}
	
	public static float lerp(float norm, float min, float max)
	{
		return (max-min)*norm + min;
	}
	
	public static float map(float value, float srcMin, float srcMax, float destMin, float destMax)
	{
		return lerp(normalize(value, srcMin, srcMax), destMin, destMax);
	}
	
	public static float clamp(float value, float min, float max)
	{
		return Math.min(Math.max(value, Math.min(min, max)), Math.max(min, max));
	}
	
	public static float randomRange(float min, float max)
	{
		return (float) (min + Math.random() * (max - min));
	}
	
	public static int randomInt(float min, float max)
	{
		return (int) Math.floor(min + Math.random() * (max - min + 1));
	}
	
	public static float randomDist(float min, float max, int iterations)
	{
		float total = 0;
		for (int i = 0; i < iterations; i++)
		{
			total += randomRange(min, max);
		}
		return total/iterations;
	}
	
	public static float roundToPlaces(float value, int places)
	{
		float mult = (float) Math.pow(10, places);
		return Math.round(value*mult)/mult;
	}
	
	public static float roundToNearest(float value, float nearest)
	{
		return Math.round(value/nearest) * nearest;
	}
	
	public static boolean inRange(float value, float min, float max)
	{
		return (value >= Math.min(min, max)) && (value <= Math.max(max, min));
	}
	
	public static boolean rangeIntersect(float min1, float max1, float min2, float max2)
	{
		return (Math.max(min1, max1) >= Math.min(min2, max2)) && (Math.max(min2, max2) >= Math.min(min1,  max1));
	}
	
	public static float wrapX(float x)
	{
		if (x < 0) x = width - 1;
		else if (x >= width) x = 0;
		return x;
	}
	
	public static float wrapY(float y)
	{
		if (y < 0) y = height - 1;
		else if (y >= height) y = 0;
		return y;
	}
	
	public static Vector2f quadraticBezier(Vector2f v0, Vector2f v1, Vector2f v2, float t)
	{
		return new Vector2f((float)(Math.pow(1 - t, 2) * v0.x + 
							(1 - t) * 2 * t * v1.x + 
							t * t * v2.x), 
		   					(float)(Math.pow(1 - t, 2) * v0.y + 
							(1 - t) * 2 * t * v1.y + 
							t * t * v2.y)); 
	}
	
	public static Vector2f cubicBezier(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, float t) {
		return new Vector2f((float)(Math.pow(1 - t, 3) * v0.x + 
							Math.pow(1 - t, 2) * 3 * t * v1.x + 
							(1 - t) * 3 * t * t * v2.x + 
							t * t * t * v3.x),
							(float)(Math.pow(1 - t, 3) * v0.y + 
							Math.pow(1 - t, 2) * 3 * t * v1.y + 
							(1 - t) * 3 * t * t * v2.y + 
							t * t * t * v3.y));
	}
}
