//----------------------------------------------------------------------
// Author: Micheal Oyenekan
// Date : 10/23/2020 (Completed)
// File : Goomba.java
//----------------------------------------------------------------------
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.File;
import javax.imageio.ImageIO;
import javax.lang.model.element.ModuleElement.DirectiveVisitor;

public class Goomba extends Sprite 
{
    static BufferedImage image, fire_image;
    Model model;
    int px, py, direction;
    double vert_vel, speed;
    int numFramesOnFire;
    boolean isOnFire;

    public Goomba(int x, int y, Model m)
    {
        //update();
        direction = 1;
        vert_vel = 12.0;
        speed = 7.0;
        this.x = x;
        this.y = y;
        w = 99;
        h = 118;
        this.model = m;
        
        numFramesOnFire = 0;
        isOnFire = false;
        if(image == null)
            image = View.loadImage("goomba.png");
        if (fire_image == null)
            fire_image = View.loadImage("goomba_fire.png");
    }

    public Goomba(Json ob, Model m)
	{
        direction = 1;
        vert_vel = 12.0;
        speed = 7.0;
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = 99;
        h = 118;
        model = m;
        numFramesOnFire = 0;
        isOnFire = false;
        if (image == null)
            image = View.loadImage("goomba.png");
        if (fire_image == null)
            fire_image = View.loadImage("goomba_fire.png");
		System.out.println("You've loaded Goomba at (" + x + ", " + y + ")");
	}

    @Override
    boolean isGoomba()
    {
        return true;
    }

    //----------------------------------------------
	// Method that saves the previous coordinates
	// of Goomba. This method will be helpful in 
	// determining whether or not Goomba collided
	// with a tube. 
	//----------------------------------------------
	void savePreviousCoordinates()
	{
		px = this.x;
		py = this.y;
	}

    void update()
    {  
        this.y += vert_vel;
        this.x += speed * direction; 

        if (this.y > 400 - h)
        {
            vert_vel = 0;
            this.y = 400 - h; //snap back to the ground
        }
        
        if(this.y < 0)
        {
            this.y = 0;
            vert_vel += 12;
        }

    }

    void draw(Graphics g)
    {
        if (isOnFire)
        {
            g.drawImage(fire_image, x - model.mario.x + model.mario.marioOffset, y, null);
            numFramesOnFire++;
            speed = 0;
        }
        else
        {
            g.drawImage(image, x - model.mario.x + model.mario.marioOffset, y, null);  
            isOnFire = false;
        }  
    }

    Json marshal()
	{
		Json ob = Json.newObject();
		ob.add("x", x);
		ob.add("y", y);
		return ob;
	}
}