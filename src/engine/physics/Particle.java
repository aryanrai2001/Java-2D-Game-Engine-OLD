package engine.physics;

import engine.physics.maths.Vector2f;

public class Particle
{
	public float x, y;
	public float vx, vy;
	public float ax, ay;
	public float friction;
	
	public Particle(float x, float y)
	{
		this.x = x;
		this.y = y;
		vx = vy = ax = ay = 0;
		friction = 0;
	}
	
	public void update()
	{
		vx = (vx + ax) * (1f-friction);
		vy = (vy + ay) * (1f-friction);
		x += vx;
		y += vy;
//		velocity.setVector(velocity.add(acceleration).mul(1f-friction));
//		position.setVector(position.add(velocity));
	}
	
	public void accelerate(Vector2f acceleration)
	{
//		velocity.setVector(velocity.add(acceleration).mul(1f-friction));
		vx = (vx + acceleration.x) * (1f-friction);
		vy = (vy + acceleration.y) * (1f-friction);
	}
	
	public float angleTo(Particle p)
	{
//		return p.position.sub(position).getAngle();
		return (float)(Math.atan2(p.y - y, p.x - x) * (180.0/3.14159265359));
	}
	
	public float distanceTo(Particle p)
	{
//		return p.position.sub(position).length();
		return (float) Math.sqrt((p.x - x) * (p.x - x) + (p.y - y) * (p.y - y));
	}
	
	public void setSpeed(float speed)
	{
//		velocity.setLength(speed);
		if (!(vx == 0.0f && vy == 0.0f))
		{
			float len = (float) Math.sqrt(vx * vx + vy * vy);
			vx = vx/len*speed;
			vy = vy/len*speed;
		}
    	else vx = speed;
	}
	
	public void setHeading(float angle)
	{
//		velocity.setAngle(dir);
		if(!(vx == 0.0f && vy == 0.0f))
    	{
    		float length = (float) Math.sqrt(vx * vx + vy * vy);
        	vx = (float)Math.cos(angle / (180.0/3.14159265359)) * length;
        	vy = (float)Math.sin(angle / (180.0/3.14159265359)) * length;
    	}
	}
	
	public void setFriction(float friction)
	{
		if (friction <= 0) this.friction = 0;
		else if (friction >= 100) this.friction = 0.5f;
		else this.friction = friction/2000;
	}

	public void setPosition(Vector2f pos)
	{
//		position.setVector(pos);
		x = pos.x;
		y = pos.y;
	}
	
	public void setVelocity(Vector2f vel)
	{
//		velocity.setVector(vel);
		vx = vel.x;
		vy = vel.y;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}

}
