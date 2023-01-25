package game.demos;

import engine.physics.RigidBody;
import engine.physics.maths.Vector2f;
import engine.state.GameState;
import engine.system.Renderer;

public class SpringForce extends GameState
{
	
	RigidBody ball1, ball2;
	Vector2f grav;

	public void init()
	{
		ball1 = new RigidBody(Engine.getWidth()/2, Engine.getHeight()/2, 2000);
		ball2 = new RigidBody(Engine.getWidth()/4, Engine.getHeight()/2, 100);
		ball1.setFriction(50);
		grav = new Vector2f(0, 2.5f);
	}

	public void update()
	{	
		ball2.setPosition(Engine.getMousePos());
		ball1.springTo(ball2, 0.05f, 100);
		ball1.accelerate(grav);
		ball2.update();
		ball1.update();
	}

	public void render(Renderer renderer)
	{
		renderer.clear(0xDAE6CF);
		renderer.drawLine((int) ball1.x, (int) ball1.y, (int) ball2.x, (int) ball2.y, 0x809E64, true);
		renderer.fillCircle((int) ball1.x, (int) ball1.y, 90, 0x4766E6, true);
		renderer.fillCircle((int) ball2.x, (int) ball2.y, 20, 0xEB7957, true);
	}

	public void dispose()
	{
	}
}
