package engine.physics;

import engine.physics.maths.Vector2f;

public class RigidBody extends Particle
{
	private Vector2f gravity;
	private Vector2f springForce;
	private float mass;
	public RigidBody(float x, float y, float mass)
	{
		super(x, y);
		gravity = new Vector2f();
		springForce = new Vector2f();
		this.mass = mass;
	}
	public void gravitateTo(RigidBody p)
	{
		float dist = distanceTo(p);
		gravity.setLength(p.mass/(dist*dist));
		gravity.setAngle((float)(Math.atan2(p.y - y, p.x - x) * (180.0/3.14159265359)));
		this.accelerate(gravity);
	}
	public void springTo(RigidBody p, float k, float length)
	{
		springForce.setVector(p.x - x, p.y - y);
		float distance = springForce.length();
		float springForceMag = (distance - length) * k;
		this.accelerate(springForce.div(distance).mul(springForceMag));
	}
}
