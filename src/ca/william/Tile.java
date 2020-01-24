/*
 * Tile.java
 * Name: William H.
 * Date: January 24. 2019
 * 
 * 		Purpose: To handle the x and y of the tiles, and thier behaviour
 * 
 * 		Methods: Tile
 * 				 draw
 * 				 equals
 * 				 getColour
 * 				 getH
 * 				 getSize
 * 				 isWithin	
 * 				 setHeld
 * 				 overlap
 * 				 getX
 * 				 getY
 * 				 getW
 * 				 getH
 */


package ca.william;

import java.util.Random;

import javafx.scene.image.Image;

public class Tile {
	//fileds
	public static final int tileW = 48;

	private int x, y;
	private int w, h;	
	private int size;

	private boolean isHeld;
	private String colour;
	private Image tile;

	public Tile(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		
		//gets random colour
		Random r = new Random();
		
		if(r.nextBoolean()) {
			colour = "blue";
		} else {
			colour = "yellow";
		}
		
		//gets the appropriate image
		if(size == 1) {
			tile = new Image(this.getResource("/images/game/" + colour + "SmallOne.png"));
		} else if(size == 2) {
			tile = new Image(this.getResource("/images/game/" + colour + "SmallTwo.png"));
		} else if(size == 3) {
			tile = new Image(this.getResource("/images/game/" + colour + "SmallThree.png"));
		}

		w = (int) tile.getWidth();
		h = (int) tile.getHeight();
		isHeld = false;
	}

	public boolean isWithin(int mx, int my) {
		//checks if the x y is inside the tile
		if(mx >= x && mx <= x + w) {
			if(my >= y && my <= y + h) {
				return true;
			}
		}

		return false;
	}

	public void draw() {
		//draws the tiel
		Game.graphics.drawImage(tile, x, y);
	}

	public void setHeld(boolean b) {
		isHeld = b;
	}

	public boolean isHeld() {
		return isHeld;
	}
	
	public String getColour() {
		return colour;
	}
	
	//equals funtion
	public boolean equals(Object o) {
		if(this == o) 
			return true;
		
		if(o == null)
			return false;
		
		if(this.getClass() != o.getClass())
			return false;
		
		Tile temp = (Tile) o;
		
		return this.x == temp.x
				&& this.y == temp.y
				&& this.w == temp.w
				&& this.h == temp.h
				&& this.size == temp.size;
	}
	
	//checks if the tile overlaps with another
	public boolean overlap(Tile t) {
		for(int i = 0; i < size; i++) {
			int temp = x / 48 + i;
				
			for(int j = 0; j < t.getSize(); j++) {
				
				int temp2 = t.getX() / 48 + j;
				
				if(temp == temp2) {
					return true;
				}			
			}

		}
		
		return false;
	}
	
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}

	//getters and setters
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}
	
	public int getH() {
		return h;
	}

}
