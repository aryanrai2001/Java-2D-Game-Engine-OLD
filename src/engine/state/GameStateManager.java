package engine.state;

import java.util.Stack;

import engine.system.Renderer;

public class GameStateManager
{
	private final Stack<GameState> states = new Stack<GameState>();
	public final void addState(GameState state)
	{
		state.init();
		states.push(state);
	}
	public final void removeCurrentState()
	{
		states.pop().dispose();
	}
	public final void updateCurrentState()
	{
		states.peek().update();
	}
	public final void renderCurrentState(Renderer renderer)
	{
		states.peek().render(renderer);
	}
	public final boolean isEmpty()
	{
		return states.isEmpty();
	}
}
