/*
 * Button.java
 * Name: William H.
 * Date: January 24. 2019
 * 
 * 		Purpose: to act as a custom button, using custom images
 * 
 * 		Methods: Button()
 * 				 center() - to center his image
 * 				 draw() - it draws a selected and unselected image based on the mouse x y
 * 				 getX
 * 				 getY
 * 				 setX
 * 				 setY
 * 
 *  */

package ca.william;

import javafx.scene.image.Image;

public class Button {
	//fields
	private int x, y;
	private int w, h;;
	private final Image pressed;
	private final Image unpressed;
	
	
	//constructor
	public Button(int x, int y, String unpressed, String pressed) {
		this.x = x;
		this.y = y;
		
		this.pressed = new Image(this.getResource(pressed));
		this.unpressed = new Image(this.getResource(unpressed));
		
		w = (int) this.pressed.getWidth();
		h = (int) this.pressed.getHeight();
	}
	
	
	//checks if a given x, y is within the button
	public boolean isWithin(int mx, int my) {
		if(mx > x && mx < x + w) {
			if(my > y && my < y + h) {
				return true;
			}
		}
		
		return false;
	}
	
	//center the button in the game window
	public void center() {
		x = (Game.windowW - w) / 2;
		y = (Game.windowH - h) / 2;
	}
	
	//draw based on the x and y
	public void draw(int mx, int my) {
		if(isWithin(mx, my)) {
			Game.graphics.drawImage(pressed, x, y);
		} else {
			Game.graphics.drawImage(unpressed, x, y);
		}
	}
			
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}
	
	
	//getter + setters
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
