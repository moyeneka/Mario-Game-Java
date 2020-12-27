//----------------------------------------------------------------------
// Author: Micheal Oyenekan
// Date : 10/23/2020 (Completed)
// File : Model.java
// Description: The Model contains the whole world of the program. It 
// coordinates what happens when a mouse is clicked, when a key is pressed, 
// when a tube is created (where it is stored), and implements how the data
// is stored or loaded to and from a Json file. 
//-----------------------------------------------------------------------
// NOTE: The information in tubetest.json is still in the file, so when 
// the program is run, a print statement appears saying "You've loaded
// a tube at (300, 306)".
//-----------------------------------------------------------------------

import java.util.ArrayList;
import java.util.Iterator;

class Model
{
	//Member variables
	ArrayList<Sprite> sprites;
	Mario mario;
	
	//----------------------------------------------
	// Constructor that initializes an arrayList 
	// of tubes.
	//----------------------------------------------
	Model()
	{
		sprites = new ArrayList<Sprite>();
		mario = new Mario(200, 50);
		sprites.add(mario);
	}
	
	//----------------------------------------------
	// Method that calls the update method from the 
	// Mario class. This ensures the desired outcome
	// implemented in the Mario class is achieved.
	//----------------------------------------------
	public void update()
	{
		//Check for collisions with tubes
		for (int i = 0; i < sprites.size(); i++)
		{
			sprites.get(i).update();
			if (sprites.get(i).isTube())
			{
				Tube t = (Tube)sprites.get(i);
				if (isThereCollision(mario, t))
					mario.getOutOfTube(t);
				//for loop to go through ArrayList again
				//look for Goombas
				for (int j = 0; j < sprites.size(); j++)
				{
					if (sprites.get(j).isGoomba())
					{
						Goomba g = (Goomba)sprites.get(j);
						if(isThereCollision(t, g))
							g.direction *= -1; //Change Goomba's direction. 	
					}
				}
			}
			for (int j = 0; j < sprites.size(); j++)
			{
				if (sprites.get(j).isFireball())
				{
					Fireball fb = (Fireball)sprites.get(j);
					for (int k = 0; k < sprites.size(); k++)
					{
						if (sprites.get(k).isGoomba())
						{
							Goomba g = (Goomba)sprites.get(k);
							if (isThereCollision(g, fb))
								g.isOnFire = true;
						}
					}
				}
				
				if (sprites.get(j).isGoomba())
				{
					Goomba g = (Goomba)sprites.get(j);
					if (g.numFramesOnFire >= 20)
						sprites.remove(g);
				}
			}
		}
	}
	
	//-----------------------------------------------------------------------
	// Method that checks whether or not a collision has occurred. This method
	// returns true when there is a collision. 
	//-----------------------------------------------------------------------
	boolean isThereCollision(Sprite a, Sprite b)
	{
		//Checking the left side
		if (a.x + a.w < b.x)
			return false;
		//Checking the right side
		if (a.x > b.x + b.w)
			return false;
		//Checking the top
		if (a.y + a.h < b.y)
			return false;
		//Checking the bottom
		if (a.y > b.y + b.h)
			return false;
		return true;
	}
	
	//-----------------------------------------------------------------------
	// Method that stores arrayList information to a Json file.
	//-----------------------------------------------------------------------
	Json marshal()
	{
		Json ob = Json.newObject();
		Json spritesOb = Json.newObject();
		Json tmpList = Json.newList();
		ob.add("sprites", spritesOb);
		spritesOb.add("tubes", tmpList);
		for (int i = 0; i < sprites.size(); i++)
		{
			if (sprites.get(i).isTube())
			{
				Tube t = (Tube)sprites.get(i);
				tmpList.add(t.marshal());
			}
		}
		tmpList = Json.newList();
		spritesOb.add("goombas", tmpList);
		for (int i = 0; i < sprites.size(); i++)
		{
			if (sprites.get(i).isGoomba())
			{
				Goomba g = (Goomba)sprites.get(i);
				tmpList.add(g.marshal());
			}
		}
		return ob;
	}
	
	//-----------------------------------------------------------------------
	// Method that loads arrayList information from a Json file.
	//-----------------------------------------------------------------------
	void unmarshal(Json ob)
	{
		 sprites = new ArrayList<Sprite>();
		 sprites.add(mario);
		 Json jsonList = ob.get("sprites");
		 Json tubesList = jsonList.get("tubes");
		 Json goombasList = jsonList.get("goombas");
		 // Adding tubes back into sprites.
		 for (int i = 0; i < tubesList.size(); i++)
		 {
			 sprites.add(new Tube(tubesList.get(i), this));
		 }
		 // Addinng Goombas back into sprites. 
		 for (int i = 0; i < goombasList.size(); i++)
		 {
			sprites.add(new Goomba(goombasList.get(i), this));
		 }
	}
	
	//----------------------------------------------
	// Method that adds a tube to an empty location
	// and removes it if it has been clicked on. 
	//----------------------------------------------
	public void addTube(int x, int y)
	{
		
		//Boolean variable used to check whether the tube is clicked or not.
		boolean isTheTubeClicked = false;
		Iterator<Sprite> tubeIterator = sprites.iterator();
		while(tubeIterator.hasNext())
		{
			Sprite s = tubeIterator.next();
			if (s.isTube())
			{
				System.out.println("We have a tube.");
				Tube temp = (Tube) s;
				//Removes the tube if it is clicked.
				if (temp.tubeClicked(x, y))
				{
					sprites.remove(temp);
					isTheTubeClicked = true;
					break;
				}	
			}
		}
		//Adds a tube if there is none. 
		if (!isTheTubeClicked)
		{
			Tube t = new Tube(x, y, this);
			sprites.add(t);
			System.out.println("Tube drawn at (" + t.x + "," + t.y + ")");
		}
	}

	public void addGoomba(int x, int y)
	{
		Goomba g = new Goomba(x, y, this);
		sprites.add(g);
	}

	public void lauchFireball(int x, int y)
	{
		Fireball fb = new Fireball(x, y, this);
		sprites.add(fb);
	}
}

