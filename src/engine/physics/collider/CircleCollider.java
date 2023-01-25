package engine.physics.collider;

import engine.physics.maths.Vector2f;
import engine.system.Renderer;

public class CircleCollider
{
	private float radius;
	public CircleCollider(float radius)
	{
		this.radius = radius;
	}
	public boolean circleCollision(float distance, CircleCollider c)
	{
		return distance <= (c.radius+ radius);
	}
	public boolean pointCollision(Vector2f circle, Vector2f point)
	{
		return (circle.sub(point).length()) <= radius;
	}
	public void render(Vector2f position, Renderer renderer)
	{
		renderer.drawCircle(position, (int)radius, 0xffffffff, true);
	}
}
