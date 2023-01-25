package engine.physics.collider;

import engine.physics.maths.Vector2f;
import engine.system.Renderer;
import engine.utility.Util;

public class BoxCollider
{
	private int width, height;
	private Vector2f size;
	public BoxCollider(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.size = new Vector2f(width, height);
	}
	public boolean boxCollision(Vector2f distance, BoxCollider c)
	{
		float distanceX = distance.x;
		float distanceY = distance.y;
		return Util.rangeIntersect(-width/2, width/2, distanceX - c.width/2, distanceX + c.width/2)
			&& Util.rangeIntersect(-height/2, height/2, distanceY - c.height/2, distanceY + c.height/2);
	}
	public boolean pointCollision(Vector2f box, Vector2f point)
	{
		float x = box.x, y = box.y;
		return Util.inRange(point.x, x-width/2, x+width/2)
			&& Util.inRange(point.y, y-height/2, y+height/2);
	}
	public void render(Vector2f position, Renderer renderer)
	{
		renderer.drawRect(position.sub(size.div(2)), size, 0xffffffff, true);
	}
}
