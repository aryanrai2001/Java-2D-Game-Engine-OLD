package engine.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import engine.physics.maths.Vector2f;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{

	private final int NUM_KEYS = 256, NUM_BUTTONS = 5;
	
	public boolean[] keys;
	public boolean[] lastKeys;
	public boolean[] buttons;
	public boolean[] lastButtons;
	public int mouseX, mouseY, scroll;
	public Vector2f mousePos;
	public int scale;
	
	public Input(int scale)
	{
		this.scale = scale;
		this.mouseX = 0;
		this.mouseY = 0;
		this.mousePos = new Vector2f(0, 0);
		this.reset();
	}
	
	public final void reset()
	{
		this.keys = new boolean[NUM_KEYS];           
		this.lastKeys = new boolean[NUM_KEYS];       
		this.buttons = new boolean[NUM_BUTTONS];     
		this.lastButtons = new boolean[NUM_BUTTONS];
	}
	
	public final void update()
	{
		for (int i = 0; i < NUM_KEYS; i++)
			lastKeys[i] = keys[i];
		for (int i = 0; i < NUM_BUTTONS; i++)
			lastButtons[i] = buttons[i];
		scroll = 0;
		mousePos.setVector(mouseX, mouseY);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		scroll = e.getWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		mouseX = (e.getX()/scale);
		mouseY = (e.getY()/scale);
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		mouseX = (e.getX()/scale);
		mouseY = (e.getY()/scale);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		buttons[e.getButton()] = false;
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
	}

}
