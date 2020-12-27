//----------------------------------------------------------------------
// Author: Micheal Oyenekan
// Date : 10/23/2020 (Completed)
// File : Game.java
// Description: This program implements a Mario game. It a includes a 
// Mario character that can run and jump when prompted by the user. There
// are also tubes included in this game, which can either be loaded from 
// a json file and added during gameplay. Mario can collide with any of 
// the tubes unless he is on top of them or under them. 
//-----------------------------------------------------------------------
// NOTE: The information in tubetest.json is still in the file, so when 
// the program is run, a print statement appears saying "You've loaded
// a tube at (300, 306)".
//-----------------------------------------------------------------------

//Importing java classes to be used.
import javax.swing.JFrame;
import java.awt.Toolkit;

//----------------------------------------------
// The principle class of the program is Game. 
// This class contains all the components and 
// aspects of the program.
//----------------------------------------------
public class Game extends JFrame
{
	//Declaring the member variables.
	Model model;
	Controller controller;
	View view;
	
	//----------------------------------------------
	// Constructor for the Game class. The constructor
	// initializes the model, controller, and the view.
	//----------------------------------------------
	public Game()
	{
		//Initialization of member variables. 
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		
		//Setting the initial look of the panel.
		this.setTitle("Fireball Mario");
		this.setSize(800, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//Adding the event listeners. 
		view.addMouseListener(controller);
		this.addKeyListener(controller);

		// try{
		// 	Json j = Json.load("map.json");
		// 	model.unmarshal(j);
		// } 
		// catch (Exception e)
		// {
		// 	e.printStackTrace();;
		// 	System.out.print("'There was an error loading your map.'");
		// 	System.exit(0);
		// }
	}
	
	//----------------------------------------------
	// The run method regularly updates the screen
	// as the turtle moves.
	//----------------------------------------------
	public void run()
	{
		while(true)
		{
			controller.update();
			view.repaint(); //Indirectly calls View.paintComponent
			model.update();
			Toolkit.getDefaultToolkit().sync(); //Updates screen
			
			//Go to sleep for 50 milliseconds
			try
			{
				Thread.sleep(50); //20 frames per second
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	//----------------------------------------------
	// Main initializes a game object and runs it. 
	//----------------------------------------------
	public static void main(String[] args)
	{
		Json j = Json.load("map.json");
		//Tube t = new Tube(j);
		
		Game g = new Game();
		g.run();
	}
	
}

