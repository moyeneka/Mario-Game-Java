//----------------------------------------------------------------------
// Author: Micheal Oyenekan
// Date : 10/23/2020 (Completed)
// File : Controller.java
// Description: The Controller class is in charge of different manipulations
// done namely the mouse click, keys pressed, and so on. This class specifically
// allows the user to execute an event on a mouse click or key press. 
//-----------------------------------------------------------------------
// NOTE: The information in tubetest.json is still in the file, so when 
// the program is run, a print statement appears saying "You've loaded
// a tube at (300, 306)".
//-----------------------------------------------------------------------

//Importing java classes to be used. 
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
	// Declaring the member variables.
	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean spaceBar;
	boolean addTubesEditor = true; //set this to "false" to prevent the user from adding more tubes
	boolean addGooombasEditor = false; 
	boolean ctrl;
	//boolean moving;
	//----------------------------------------------
	// Constructor for the Controller class. It 
	// initializes the model. 
	//----------------------------------------------
	Controller(Model m)
	{
		model = m;
	}
	
	//----------------------------------------------
	// setView method initializes the view variable.
	//----------------------------------------------
	void setView(View v)
	{
		view = v; 
	}
	
	//----------------------------------------------
	// [EMPTY METHOD]
	//----------------------------------------------
	public void actionPerformed(ActionEvent e)
	{
	}
	
	//----------------------------------------------
	// Method that handles mouse clicks by calling
	// the addTube method from the model.
	//----------------------------------------------
	public void mousePressed(MouseEvent e)
	{
		if (addTubesEditor)
			model.addTube(e.getX() + model.mario.x - model.mario.marioOffset, e.getY());
		else if(addGooombasEditor)
			model.addGoomba(e.getX() + model.mario.x - model.mario.marioOffset, e.getY());
	}
	
	//----------------------------------------------
	// Methods that control different mouse events.
	//----------------------------------------------
	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseClicked(MouseEvent e) { }
	
	//----------------------------------------------
	// Method that determines whether or not the 
	// arrow keys are pressed.
	//----------------------------------------------
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_RIGHT: keyRight = true; break;
		case KeyEvent.VK_LEFT: keyLeft = true; break;
		case KeyEvent.VK_UP: keyUp = true; break;
		case KeyEvent.VK_DOWN: keyDown = true; break;
		case KeyEvent.VK_SPACE: spaceBar = true; break;
		case KeyEvent.VK_CONTROL: ctrl = true; break;
		}
	}
	
	//----------------------------------------------
	// Method that determines whether or not the 
	// arrow keys are released. This method also
	// saves the tube information when the key 's'
	// is pressed and loads information when the 
	// key 'l' is pressed. 
	//----------------------------------------------
	public void keyReleased(KeyEvent e)
	{
		//When the arrow key is released, stop scrolling. 
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_RIGHT: keyRight = false; break;
		case KeyEvent.VK_LEFT: keyLeft = false; break;
		case KeyEvent.VK_UP: keyUp = false; break;
		case KeyEvent.VK_SPACE: spaceBar = false; break;
		case KeyEvent.VK_CONTROL: ctrl = false; break;
		}
		
		char c = e.getKeyChar();
		//Allowing the user to save to a Json file by pressing 's'.
		if (c == 's')
		{
			model.marshal().save("map.json");
			System.out.println("You have saved map.json");
		}
		//Allowing the user to load from a Json file by pressing 'l'.
		if (c == 'l')
		{
			Json j = Json.load("map.json");
			model.unmarshal(j);
			System.out.println("You have loaded from map.json");
		}
		//Allowing to add tubes when the key 't' is pressed.
		if(c == 't')
		{
			addGooombasEditor = false;
			addTubesEditor = !addTubesEditor;
		}
		//Allowing to add Goombas when the key 'g' is pressed.
		if(c == 'g')
		{
			addTubesEditor = false;
			addGooombasEditor = !addGooombasEditor;
		}
	}
	
	//----------------------------------------------
	// [EMPTY METHOD]
	//----------------------------------------------
	public void keyTyped(KeyEvent e)
	{
	}
	
	//----------------------------------------------
	// Method that updates the view when the left 
	// and right arrow keys are pressed. This method
	// also determines the scrolling speed. 
	//----------------------------------------------
	void update()
	{
		model.mario.savePreviousCoordinates();
		if(keyRight) 
		{
			for (int i = 0; i < model.sprites.size(); i++)
			{
				if (model.sprites.get(i).isMario())
				{
					Mario mario = (Mario)model.sprites.get(i);
					mario.flip = false;
					mario.x += 8;
					mario.updateImageNum();
				}
			}
		}
		if (keyLeft) 
		{
			for (int i = 0; i < model.sprites.size(); i++)
			{
				if (model.sprites.get(i).isMario())
				{
					Mario mario = (Mario)model.sprites.get(i);
					mario.flip = true;
					mario.x -= 8;
					mario.updateImageNum();
				}
			}
		}
		if (keyUp || spaceBar)
		{
			for (int i = 0; i < model.sprites.size(); i++)
			{
				if (model.sprites.get(i).isMario())
				{
					Mario mario = (Mario)model.sprites.get(i);
					mario.jump();
				}
			}
		}

		if (ctrl)
		{
			model.lauchFireball(model.mario.x , model.mario.y);
		}
	}
}

