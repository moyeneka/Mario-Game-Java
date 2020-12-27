//----------------------------------------------------------------------
// Author: Micheal Oyenekan
// Date : 10/23/2020 (Completed)
// File : Sprite.java
// Description: The Sprite class facilitates the implementation of the 
// Mario and Tube classes. Since both of them require coordinates and 
// dimensions, this class declares the variables for them. The classes
// on the other hand, extend the Sprite class. This prevents redundant 
// declaration of the same variables. It also holds a method that loads
// images. 
//-----------------------------------------------------------------------
// NOTE: The information in tubetest.json is still in the file, so when 
// the program is run, a print statement appears saying "You've loaded
// a tube at (300, 306)".
//-----------------------------------------------------------------------
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Graphics;
import javax.imageio.ImageIO;

abstract class Sprite 
{
	int x, y, w, h;
    //String type;
	abstract void update();
	abstract void draw(Graphics g);

	boolean isTube() { return false; }
	boolean isMario() { return false; }
	boolean isGoomba() { return false; }
	boolean isFireball() { return false; }
	
}
