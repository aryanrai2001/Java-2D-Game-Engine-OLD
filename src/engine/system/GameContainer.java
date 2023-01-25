package engine.system;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.event.Input;
import engine.graphics.Camera;
import engine.physics.maths.Vector2f;
import engine.state.GameState;
import engine.state.GameStateManager;
import engine.utility.Util;

public class GameContainer implements Runnable
{
	private Thread thread;
	private BufferedImage image;
	private Graphics graphics;
	private Dimension size;
	private JPanel panel;
	private JFrame frame;
	private GameStateManager stateManager;
	private Camera camera;
	private Renderer renderer;
	private Input input;
	private String title;
	private int scale;
	private int fps;
	private int frames, updates;
	private boolean running, isDebugging;
	
	public GameContainer(int width, int height, int scale, String title)
	{
		this.title = title;
		this.scale = scale;
		this.fps = 60;
		this.frames = 0;
		this.updates = 0;
		this.running = false;
		this.isDebugging = false;

		thread = new Thread(this);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		graphics = null;
		size = new Dimension(width*scale, height*scale);
		panel = new JPanel();
		frame = new JFrame();
		stateManager = new GameStateManager();
		camera = new Camera(width, height);
		renderer = new Renderer(image, camera);
		input = new Input(scale);
		
		panel.setMinimumSize(size);
		panel.setPreferredSize(size);
		panel.setMaximumSize(size);
		panel.setDoubleBuffered(true);
		panel.addKeyListener(input);
		panel.addMouseListener(input);
		panel.addMouseMotionListener(input);
		panel.addMouseWheelListener(input);
		panel.setFocusable(true);
	
		frame.setTitle(title);
		frame.setSize(size);
		frame.add(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Util.width = size.width/scale;
		Util.height = size.height/scale;
	}

	public synchronized void start()
	{
		if (running) return;
		running = true;
		if (stateManager.isEmpty())
		{
			if (isDebugging)System.err.println("ERROR: Please add an initial state.");
			System.exit(-1);
		}
		frame.setVisible(true);
		thread.start();
	}
	
	public synchronized void run()
	{
		panel.requestFocus();
		long now, lastTime = System.nanoTime();
		double nsPerTick = 1000000000.0/fps;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while(running)
		{
			now = System.nanoTime();
			delta += (now-lastTime)/nsPerTick;
			lastTime = now;
			while(delta >= 1)
			{
				delta--;
				update();
				updates++;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer >= 1000)
			{
				timer += 1000;
				if (isDebugging) frame.setTitle(title + " | Updates: " + updates + ", Frames: " + frames);
				updates = frames = 0;
			}
		}
	}
	
	public synchronized void stop()
	{
		if (!running) return;
		frame.dispose();
		running = false;
		thread = null;
	}
	
	private void update()
	{
		stateManager.updateCurrentState();
		input.update();
	}
	
	private void render()
	{
		graphics = panel.getGraphics();
		stateManager.renderCurrentState(renderer);
		graphics.drawImage(image, 0, 0, size.width, size.height, null);
		graphics.dispose();
	}
	
	public void addState(GameState state)
	{
		state.Engine = this;
		stateManager.addState(state);
	}
	
	public void removeCurrentState()
	{
		stateManager.removeCurrentState();
		input.reset();
		stateManager.updateCurrentState();
		renderer.clear(0xff000000);
	}
	
	public void alphaEnabled(boolean alpha)
	{
		renderer.setAlpha(alpha);
	}

	public Camera getCamera() {
		return camera;
	}
	
	public boolean isMouseIn(int x1, int y1, int x2, int y2)
	{
		return (input.mouseX >= x1 && input.mouseX < x1+x2 && input.mouseY >= y1 && input.mouseY < y1+y2);
	}

	public Vector2f getMousePos()
	{
		return input.mousePos;
	}
	
	public int getMouseX()
	{
		return input.mouseX;
	}
	
	public int getMouseY()
	{
		return input.mouseY;
	}
	
	public int getScroll()
	{
		return input.scroll;
	}
	
	public boolean isKey(int keyCode)
	{
		return input.keys[keyCode];
	}
	
	public boolean isKeyDown(int keyCode)
	{
		return !input.lastKeys[keyCode] && input.keys[keyCode];
	}
	
	public boolean isKeyUp(int keyCode)
	{
		return input.lastKeys[keyCode] && !input.keys[keyCode];
	}
	
	public boolean isButton(int button)
	{
		return input.buttons[button];
	}
	
	public boolean isButtonDown(int button)
	{
		return !input.lastButtons[button] && input.buttons[button];
	}
	
	public boolean isButtonUp(int button)
	{
		return input.lastButtons[button] && !input.buttons[button];
	}

	public int getWidth()
	{
		return size.width/scale;
	}
	
	public int getHeight()
	{
		return size.height/scale;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public int getFrames()
	{
		return frames;
	}

	public int getUpdates()
	{
		return updates;
	}

	public void setFps(int fps)
	{
		this.fps = fps;
	}
	
	public void debugMode(boolean isDebugging)
	{
		this.isDebugging = isDebugging;
	}

	public void exit()
	{
		this.removeCurrentState();
		this.stop();
		System.exit(0);
	}

}
