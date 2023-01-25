package game.demos;

import java.awt.event.KeyEvent;

import engine.physics.RigidBody;
import engine.physics.maths.Vector2f;
import engine.state.GameState;
import engine.system.Renderer;

public class NewtonianGravity extends GameState
{
	
	RigidBody sun, planet;
	float speed = 2.5f;

	public void init()
	{
		sun = new RigidBody(Engine.getWidth()/2, Engine.getHeight()/2, 2000);
		planet = new RigidBody(Engine.getWidth()/4, Engine.getHeight()/2, 100);
		sun.setFriction(50);
		planet.setSpeed(2.22f);
		planet.setHeading(90);

		Engine.getCamera().setPosition(sun.getX(), sun.getY());
	}

	public void update()
	{	
		if (Engine.isKey(KeyEvent.VK_W))
			Engine.getCamera().moveY(-speed);
		if (Engine.isKey(KeyEvent.VK_A))
			Engine.getCamera().moveX(-speed);
		if (Engine.isKey(KeyEvent.VK_S))
			Engine.getCamera().moveY(speed);
		if (Engine.isKey(KeyEvent.VK_D))
			Engine.getCamera().moveX(speed);
		if (Engine.isKeyDown(KeyEvent.VK_SPACE))
			System.out.println(Engine.getCamera().getPosition());
		sun.setPosition(new Vector2f(Engine.getMouseX(), Engine.getMouseY()));
		planet.gravitateTo(sun);
		planet.update();
		sun.update();
//		Engine.getCamera().setPosition(planet.getX(), planet.getY());
//		Engine.getCamera().setPosition(sun.getX(), sun.getY());
	}

	public void render(Renderer renderer)
	{
		renderer.clear(0);
		renderer.fillCircle((int)sun.x, (int)sun.y, 50, 0xffffff00, true);
		renderer.fillCircle((int)planet.x, (int)planet.y, 10, 0xff0000ff, true);
	}

	public void dispose()
	{
	}
}
