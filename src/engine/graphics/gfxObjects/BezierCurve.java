package engine.graphics.gfxObjects;

import engine.physics.maths.Vector2f;
import engine.system.Renderer;
import engine.utility.Util;

public class BezierCurve
{
	private Vector2f[] points;
	public BezierCurve(Vector2f[] points)
	{
		this.points = new Vector2f[points.length*2-3];
		this.points[0] = points[0];
		this.points[this.points.length-1] = points[points.length-1];
		for (int i = 1; i < this.points.length-1; i+=2)
		{
			this.points[i] = points[i/2+1];
		}
		for (int i = 2; i < this.points.length-1; i+=2)
		{
			this.points[i] = points[i/2].midPointTo(points[i/2+1]);
		}
	}
	public void render(Renderer renderer)
	{
		for (int i = 0; i < points.length-1; i+=2)
			for (float t = 0; t < 1; t += 0.0001f)
				renderer.setPixel(Util.quadraticBezier(points[i], points[i+1], points[i+2], t),0xffffffff, true);
	}
	public Vector2f[] getPoints()
	{
		return points;
	}
}
