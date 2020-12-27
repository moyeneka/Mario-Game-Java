//----------------------------------------------------------------------
// Author: Micheal Oyenekan
// Date : 10/23/2020 (Completed)
// File : Tube.java
// Description: The Tube class simply determines the attributes of a tube
// object and what methods act on a single tube. 
//-----------------------------------------------------------------------
// NOTE: The information in tubetest.json is still in the file, so when 
// the program is run, a print statement appears saying "You've loaded
// a tube at (300, 306)".
//-----------------------------------------------------------------------

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Comparator;
import javax.imageio.ImageIO;
import java.awt.Graphics;

class Tube extends Sprite
{
	static BufferedImage image;
	Model model;

	//-----------------------------------------------------------------------
	// Constructor with the x and y positions as parameters. This initializes
	// the x and y coordinates of the tube. 
	//-----------------------------------------------------------------------
	public Tube(int pos_x, int pos_y, Model m)
	{
		this.x = pos_x;
		this.y = pos_y;
		w = 55;
		h = 400;
		model = m;
		loadTubeImage();
	}

	@Override
	boolean isTube()
	{
		return true;
	}
	
	//-----------------------------------------------------------------------
	//
	//-----------------------------------------------------------------------
    static void loadTubeImage()
    {
        if (image == null)
        {
            image = View.loadImage("tube.png");
        }
	}
	
	//-----------------------------------------------------------------------
	//
	//-----------------------------------------------------------------------
	void draw(Graphics g) 
	{
		g.drawImage(image, x - model.mario.x + model.mario.marioOffset, y, null);
	}

	//-----------------------------------------------------------------------
	//
	//-----------------------------------------------------------------------
	void update()
	{

	}
	
	//-----------------------------------------------------------------------
	// Method for storing tube attributes to a Json file
	//-----------------------------------------------------------------------
	Json marshal()
	{
		Json ob = Json.newObject();
		ob.add("x", x);
		ob.add("y", y);
		return ob;
	}
	
	//-----------------------------------------------------------------------
	// Method for loading tube attributes from a Json file.
	//-----------------------------------------------------------------------
	public Tube(Json ob, Model m)
	{
		x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = 55;
		h = 400;
		model = m;
		if (image == null)
        	loadTubeImage();
		System.out.println("You've loaded a tube at (" + x + ", " + y + ")");
	}
	
	//-----------------------------------------------------------------------
	// Method that determines whether or not a tube has been clicked.
	//-----------------------------------------------------------------------
	public boolean tubeClicked(int mouse_x, int mouse_y)
	{
		if (mouse_x < x)
			return false;
		if (mouse_x > (x + w))
			return false;
		if (mouse_y < y)
			return false;
		if (mouse_y > (y + h))
			return false;
		return true;
	}
}