package game.demos;

import engine.state.GameState;
import engine.system.Renderer;

public class SquaresOverlap extends GameState
{

	@Override
	public void init() 
	{
		
	}

	@Override
	public void update() 
	{
		
	}

	@Override
	public void render(Renderer renderer) 
	{
		renderer.clear(0x00);
		renderer.fillRect(100, 100, 200, 200, 0xaa990000, true);
		renderer.fillRect(700, 100, 200, 200, 0xaa000099, true);
		renderer.fillRect(100, 400, 200, 200, 0xaa999900, true);
		renderer.fillRect(700, 400, 200, 200, 0xaa990099, true);
		renderer.fillRect(Engine.getMouseX() - 100, Engine.getMouseY() - 100, 200, 200, 0xaaffffff, true);
		renderer.fillRect(400, 100, 200, 200, 0xaa009900, true);
		renderer.fillRect(400, 400, 200, 200, 0xaa009999, true);
	}

	@Override
	public void dispose() 
	{
		
	}

}
