package game.demos;

import java.awt.event.MouseEvent;

import engine.state.GameState;
import engine.system.Renderer;

public class Menu extends GameState
{
	int x, y;

	public void init()
	{
		x = (Engine.getWidth()+Engine.getHeight())/20;
		y = Engine.getHeight()/2 - 2*x;
		Engine.getCamera().setPosition(Engine.getWidth()/2, Engine.getHeight()/2);
	}

	public void update()
	{
		if (Engine.isButtonDown(MouseEvent.BUTTON1))
		{
			if (Engine.isMouseIn(x, x, Engine.getWidth() - (2*x), y))
			{
				Engine.removeCurrentState();
			}
			else if (Engine.isMouseIn(x, Engine.getHeight() - (x + y), Engine.getWidth() - (2*x), y))
			{
				Engine.exit();
			}
		}
	}

	public void render(Renderer renderer)
	{
		renderer.fillRect(x, x, Engine.getWidth() - (2*x), y,
				Engine.isMouseIn(x, x, Engine.getWidth() - (2*x), y) ? 0xff0560ff : 0xff056070, true);
		renderer.fillRect(x, Engine.getHeight() - (x + y), Engine.getWidth() - (2*x), y,
				Engine.isMouseIn(x, Engine.getHeight() - (x + y), Engine.getWidth() - (2*x), y) ? 0xffff0506
						: 0xff700506,
				true);
	}

	public void dispose()
	{
	}

}
