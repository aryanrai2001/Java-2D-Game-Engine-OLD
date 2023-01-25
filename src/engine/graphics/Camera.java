package engine.graphics;

import engine.physics.maths.Vector2f;

public class Camera
{
	private Vector2f position;
	private float halfWidth, halfHeight;
	private float rotation; //TODO: Implement Rotation Functionality
	private float zoom; //TODO: Implement Zoom Functionality
	
	public Camera(float width, float height)
	{
		halfWidth = width/2;
		halfHeight = height/2;
		position = new Vector2f(-halfWidth, -halfHeight);
		rotation = 0;
		zoom = 0;
	}
	
	public void move(float deltaX, float deltaY)
	{
		position.x += deltaX;
		position.y += deltaY;
	}
	
	public void moveX(float deltaX)
	{
		position.x += deltaX;
	}
	
	public void moveY(float deltaY)
	{
		position.y += deltaY;
	}
	
	public Vector2f getPosition()
	{
		return position;
	}
	
	public float getX()
	{
		return position.x;
	}
	
	public float getY()
	{
		return position.y;
	}
	
	public void setPosition(Vector2f position) 
	{
		this.position.x = position.x - halfWidth;
		this.position.y = position.y - halfHeight;
	}
	

	public void setPosition(float x, float y) 
	{
		this.position.x = x - halfWidth;
		this.position.y = y - halfHeight;
	}
	
	public float getRotation() 
	{
		return rotation;
	}
	
	public void setRotation(float rotation) 
	{
		this.rotation = rotation;
	}
	
	public float getZoom()
	{
		return zoom;
	}
	
	public void setZoom(float zoom) 
	{
		this.zoom = zoom;
	}
	
	
}
