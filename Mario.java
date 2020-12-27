//----------------------------------------------------------------------
// Author: Micheal Oyenekan
// Date : 10/23/2020 (Completed)
// File : Mario.java
// Description: The Mario class implements a Mario object with x and y 
// coordinates, the width, and the height. The class also includes methods
// that prevents Mario from entering the tube, allows him to jump, and run. 
//-----------------------------------------------------------------------
// NOTE: The information in tubetest.json is still in the file, so when 
// the program is run, a print statement appears saying "You've loaded
// a tube at (300, 306)".
//-----------------------------------------------------------------------

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Graphics;
import javax.imageio.ImageIO;

class Mario extends Sprite
{
	static BufferedImage[] mario_images;
	int px, py; //Previous x and y locations. 
	int marioOffset;
	int marioImageNum;
	double vert_vel;
	int numFramesInAir;
	boolean flip;

	//-----------------------------------------------------------------------
	// Constructor method for the Mario class. This method loads the Mario
	// images and initializes the coordinates and dimensions of Mario. It 
	// also sets the vertical velocity. 
	//-----------------------------------------------------------------------
	public Mario(int x, int y)
	{
		loadMarioImage();
		this.x = x;
		this.y = y;
		marioOffset = 100;
		w = 60;
		h = 95;
		marioImageNum = 0;
		vert_vel = 12.0;
		flip = false;
		numFramesInAir = 0;
	}
	
	@Override
	boolean isMario()
	{
		return true;
	}
    
    void loadMarioImage()
    {
        if (mario_images == null)
		{	
			mario_images = new BufferedImage[5];
			mario_images[0] = View.loadImage("mario1.png");
			mario_images[1] = View.loadImage("mario2.png");
			mario_images[2] = View.loadImage("mario3.png");
			mario_images[3] = View.loadImage("mario4.png");
			mario_images[4] = View.loadImage("mario5.png");
		}
    }
	
	//-----------------------------------------------------------------------
	//
	//-----------------------------------------------------------------------
	void draw(Graphics g)
	{
		if(flip)
			g.drawImage(mario_images[marioImageNum], marioOffset + w, y, -w, h, null);
		else
			g.drawImage(mario_images[marioImageNum], marioOffset, y, null);
	}
	//-----------------------------------------------------------------------
	// Method that allows Mario to jump when he is on the ground, as well as 
	// when he is standing on top of a tube. 
	//-----------------------------------------------------------------------
	void jump()
	{
		//jump when Mario is still on the ground or when he is on a tube. 
		if (y >= 400 - h || numFramesInAir < 5) 
		{
			vert_vel += -10; 
		}
	}
	
	//----------------------------------------------
	// Method that saves the previous coordinates
	// of Mario. This method will be helpful in 
	// determining whether or not Mario collided
	// with a tube. 
	//----------------------------------------------
	void savePreviousCoordinates()
	{
		px = this.x;
		py = this.y;
	}
	
	//----------------------------------------------
	// Method that allows Mario to stop one he collides
	// with a tube. It will make Mario get out of the
	// tube and always stay in front of the tube, on 
	// top, or beneath it, but never inside. 
	//----------------------------------------------
	void getOutOfTube(Tube t)
	{
		//If Mario is currently in the tube, but was previously on the 
		//left side of the tube. 
		if (x + w >= t.x && px + w <= t.x)
		{
			x = t.x - w;
		}
		//If Mario is currently in the tube, but was previously on the 
		//right side of the tube.
		if (x <= t.x + t.w && px >= t.x + t.w)
		{
			x = t.x + t.w;
		}
		//If Mario is currently in the tube, but was previously above the
		//tube. 
		if (y + h >= t.y && py + h <= t.y)
		{
			y = t.y - h;
			numFramesInAir = 0;
			vert_vel = 0; //Avoids increase in velocity while standing on a tube. 
		}
		//If Mario is currently in the tube, but was previously below the 
		//tube.
		if (y <= t.y + t.h && py >= t.y + t.h)
		{
			y = t.y + t.h;
		}
	}
	
	//----------------------------------------------
    // Method that determines the behavior of Mario
    // as he moves to different areas in the game. 
	//----------------------------------------------
	void update()
	{
		//Making Mario fall to the ground
		vert_vel += 3.0;
		y += vert_vel;
		numFramesInAir++;
		
		//Making Mario stop falling when he reaches the ground
		if (y > 400 - h)
		{
			vert_vel = 0;
			y = 400 - h; //snap back to the ground
			numFramesInAir = 0;
		}
		
		//Ensuring Mario doesn't fly off the window
		if(y < 0)
		{
			y = 0;
			vert_vel += 12;
		}
	}
    
    //----------------------------------------------
    // Method that increments the number of frames
    // as Mario moves. 
    //----------------------------------------------
	void updateImageNum()
	{
		marioImageNum++;
		if (marioImageNum > 4)
			marioImageNum = 0; //Update the number back to zero when it reaches 5.
	}
}

