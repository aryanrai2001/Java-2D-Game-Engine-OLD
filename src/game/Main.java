package game;

import engine.system.GameContainer;
import game.demos.*;

public class Main
{
	public static void main(String[] args)
	{
		GameContainer gc = new GameContainer(960, 640, 1, "MyGame");
		gc.debugMode(true);
		gc.setFps(60);
		gc.alphaEnabled(false);
		gc.addState(new Pseudo3D());
		gc.addState(new Menu());
		gc.start();
	}
}
