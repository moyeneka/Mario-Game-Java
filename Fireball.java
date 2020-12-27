//----------------------------------------------------------------------
// Author: Micheal Oyenekan
// Date : 10/23/2020 (Completed)
// File : Fireball.java
//----------------------------------------------------------------------
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.File;
import javax.imageio.ImageIO;

public class Fireball extends Sprite
{
    static BufferedImage image;
    Model model;
    double vert_vel, speed;

    public Fireball(int x, int y, Model m)
    {
        this.x = x;
        this.y = y;
        w = 47;
        h = 47;
        this.model = m;
        vert_vel = 6.0;
        speed = 10.0;
        if(image == null)
            image = View.loadImage("fireball.png");
    }

    @Override
    boolean isFireball()
    {
        return true;
    }

    void update()
    {
        this.x += speed;
        this.y += vert_vel;
        if (this.y >= 400 - h)
        {
            vert_vel *= -1;
        }

        if(this.y < 0)
		{
			this.y = 0;
			vert_vel += 6.0;
		}
    }

    void draw(Graphics g)
    {
        g.drawImage(image, x - model.mario.x + model.mario.marioOffset, y, null);
    }
}