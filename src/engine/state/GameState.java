package engine.state;

import engine.system.GameContainer;
import engine.system.Renderer;

public abstract class GameState
{
	public GameContainer Engine;
	public abstract void init();
	public abstract void update();
	public abstract void render(Renderer renderer);
	public abstract void dispose();
}
