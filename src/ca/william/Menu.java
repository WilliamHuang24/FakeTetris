/*
 * Menu.java
 * Name: William H.
 * Date: January 24. 2019
 * 
 * 		Purpose: To draw the menu and handle the inputs
 * 
 * 		Methods: Menu()
 * 				 isExit
 * 				 isRunning
 * 				 draw
 * 				 pressEvent
 * 				 dragEvent
 * 				 releaseEvent
 * 				 moveEvent
 */

package ca.william;

import javafx.scene.image.Image;

public class Menu implements Clickable {
	//fields
	private boolean isRunning;
	private boolean isExit;
	
	private Button play;
	private Button exit;
	
	private int mX, mY;
	
	public final Image slydris;
	
	public Menu() {
		//intialises fields
		isRunning = true;
		isExit = false;
		
		slydris = new Image(this.getResource("/images/menu/slydris.png"));
		
		play = new Button(350, 280, "/images/menu/play.png", "/images/menu/playPressed.png");
		exit = new Button(350, 340, "/images/menu/exit.png", "/images/menu/exitPressed.png");
	}
	
	
	public void draw() {
		//draws all the buttons and images
		Game.graphics.drawImage(slydris, (Game.windowW - slydris.getWidth()) / 2, 100);
		
		play.draw(mX, mY);
		exit.draw(mX, mY);
		
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public boolean isExit() {
		return isExit;
	}


	@Override
	public void pressEvent(int mx, int my) {
		//checks if the buttons are pressed
		if(play.isWithin(mx, my)) {
			this.isRunning = false;
		}
		
		if(exit.isWithin(mx, my)) {
			this.isRunning = false;
			this.isExit = true;
		}
	}


	@Override
	public void dragEvent(int mx, int my) {
		//do nothing
	}


	@Override
	public void releaseEvent(int mx, int my) {
		//do nothing
	}


	@Override
	public void moveEvent(int mx, int my) {
		//stores the x and y of the mouse
		this.mX = mx;
		this.mY = my;
	}
	
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}
}
