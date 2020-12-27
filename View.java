//----------------------------------------------------------------------
// Author: Micheal Oyenekan
// Date : 10/23/2020 (Completed)
// File : View.java
// Description: The view class controls the user interface i.e. what the
// users sees. This class loads the tube image and draws it on the panel. 
//-----------------------------------------------------------------------
 // NOTE: The information in tubetest.json is still in the file, so when 
// the program is run, a print statement appears saying "You've loaded
// a tube at (300, 306)".
//-----------------------------------------------------------------------

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

//----------------------------------------------
// The View class is in charge of the graphical
// interface in the panel.
//----------------------------------------------
class View extends JPanel
{
	Model model; //instantiating the model
	// int marioOffset; This is the view location only, moving him away from the edge
	
	//----------------------------------------------
	// View constructor method. The constructor 
	// initializes the controller and model, and then
	// load the tube image. 
	//----------------------------------------------
	View(Controller c, Model m)
	{
		model = m;
		c.setView(this);
		// marioOffset = 100;
	}
	
	//----------------------------------------------
	// Method to load an image and catch any errors.
	// Making the method static allows it to be called
	// outside the class. 
	//----------------------------------------------
	static BufferedImage loadImage(String fileName) 
	{
		BufferedImage im = null;
		try
		{
			im = ImageIO.read(new File(fileName));
			System.out.println(fileName + " has been loaded.");
		} 
		//In case the image doens't load, throw out an error. 
		catch(Exception e) 
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return im;
	}

	//----------------------------------------------
	// Method sets the color of the panel and iterates
	// through the arrayList of tubes to draw each tube
	// in the arrayList. 
	//----------------------------------------------
	public void paintComponent(Graphics g)
	{
		//Drawing the sky 
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		//Drawing the ground
		g.setColor(new Color(100, 255, 0));
		g.fillRect(0, 400, 800, 20);
		g.setColor(new Color(155, 118, 83));
		g.fillRect(0, 420, 800, 100);
		
		//Drawing tubes 
		for (int i = 0; i < model.sprites.size(); i++)
		{
			Sprite s = model.sprites.get(i);
			s.draw(g);
			// g.drawImage(Tube.image, model.sprites.get(i).x - model.mario.x + 
			// 		model.mario.marioOffset, model.sprites.get(i).y, null);
		}
		
		//Drawing Mario
		// g.drawImage(model.mario.mario_images[model.mario.marioImageNum], 
		// 		model.mario.marioOffset, model.mario.y, null);
	}
}
 

