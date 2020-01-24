/*
 * Board.java
 * Name: William H.
 * Date: January 24. 2019
 * 
 * 		Purpose: To act as a board and store the tiles in the game. It handles all the inputs
 * 
 * 		Methods: Board()
 * 				 clearRows
 * 				 drawBoard
 * 				 drawGrid
 * 				 endTurn
 * 				 generateRow
 * 				 getResource - gets absolute file path
 * 				 getRow(int)
 * 				 isLost() boolean
 * 				 isRunning() - boolean
 * 			
 * 
 * 				From Clickable:
 * 				 moveEvent
 * 				 pressEvent
 * 				 releaseEvent
 * 				 dragEvent
 * 				 
 */

package ca.william;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class Board implements Clickable {

	//images
	public Image loseImage = new Image(this.getResource("/images/game/GameOver.png"));
	
	//private fields
	private ArrayList<Tile> tiles = new ArrayList<Tile>();

	private Button exit;
	private Button restart;
	
	private int score;
	private int relX;
	private boolean gameRunning;
	private boolean isLost;
	private boolean drawGrid;
	
	private int mX, mY;
	
	private final HighscoreReader highscores = new HighscoreReader(new File("highscore.txt"));
	
	//constructor
	public Board() {
		score = 0;
		gameRunning = true;
		isLost = false;
		drawGrid = false;
		
		exit = new Button(Game.boardRightX + 60, Game.boardBottomY - 50, "/images/menu/exit.png", "/images/menu/exitPressed.png");
		restart = new Button(Game.boardRightX + 90, Game.boardStartH + 10, "/images/menu/restart.png", "/images/menu/restart.png");
		
		generateRow();
	}

	@Override
	public void pressEvent(int mx, int my) {
		//checks exit button
		if(exit.isWithin(mx, my)) {
			gameRunning = false;
		}
		
		//checks restart button
		if(restart.isWithin(mx, my)) {
			reset();
		}
		
		//waits for a click event to exit the game
		if(isLost) {
			gameRunning = false;
		}
		
		//checks if any tiles are clicked
		for(Tile t : tiles) {
			if (t.isWithin(mx, my)) {
				t.setHeld(true);
				relX = t.getX() - mx;
			} 
		}
	}

	private void reset() {
		//restarts the game
		score = 0;
		gameRunning = true;
		isLost = false;
		drawGrid = false;
		
		tiles.clear();
		
		generateRow();
		
	}

	@Override
	public void dragEvent(int mx, int my) {
		for(Tile t : tiles) {
			if(t.isHeld()) {
				//jump to the closest grid square
				//in this case the closest multiple of 48 (squares are 48 * 48)
				int tX = mx + relX;

				//integer division, then multiplied by 48 always gives a multiple of 48
				tX = (tX / 48) * 48;

				//default left and right bounds
				int leftBound = Game.boardLeftX;
				int rightBound = Game.boardRightX;
				
				//gets the row of the tile
				ArrayList<Tile> temp = getRow(t.getY() / 48);
				
				//goes through the tiles in the same row
				for(Tile tile : temp) {
					if(tile.equals(t)) {
						continue;
					}
					
					//left bound
					if(tile.getX() + tile.getW() <= t.getX() && tile.getX() + tile.getW() > leftBound)
						leftBound = tile.getX() + tile.getW();
						
					//right bound
					if(tile.getX() >= t.getX() + t.getW() && tile.getX() < rightBound) {
						rightBound = tile.getX();
					}
				}


				//limit the left bounds
				if(tX < leftBound) {
					tX = leftBound;
				}

				//limit the right bounds
				if(tX + t.getW() > rightBound) {
					tX = rightBound - t.getW();
				}

				t.setX(tX);
			}
		}
	}

	@Override
	public void releaseEvent(int mx, int my) {
		//if the tile is held, then release it
		for(int i = 0; i < tiles.size(); i++) {
			if(tiles.get(i).isHeld()) {
				tiles.get(i).setHeld(false);
				
				//turn is ended after a tile is moved / clicked on
				endTurn();
				generateRow();
			}
		}
	}

	@Override
	public void moveEvent(int mx, int my) {
		//stores the mouse location
		mX = mx;
		mY = my;
	}
	
	public void gravity() {
		//for all tiles, find the closest that is below		
		int tempY;
		
		ArrayList<Tile> temp = new ArrayList<>();
		ArrayList<Tile> temp2 = new ArrayList<>(tiles);
		
		
		//checks all the rows starting from the bottom
		//it moves the bottom row down if possible, then each subsequent row above
		for(int i = 0; i < 12; i++) {

			temp = getRow(12 - i);

			for(int j = 0; j < temp.size(); j++) {
				int closestY = 12 * 48;
				
				for(Tile t : tiles) {

					//find a tile that it is overlapping w/
					//find the closest one that is below
					//drop if possible
					
					if(t.equals(temp.get(j))) {
						continue;
					}
					
					if(!temp.get(j).overlap(t)) {				
						continue;
					}

					//tempY = tiles.get(i).getY();
					tempY = temp.get(j).getY();

					if(t.getY() > tempY && t.getY() < closestY) {
						closestY = t.getY();
					} 
				}
					
				//moves the tile to the lowest point possible
				if(temp.get(j).getY() + temp.get(j).getH() != closestY) {
					temp.get(j).setY(closestY - temp.get(j).getH());
				}
			}
		}
		
		//apply gravity until no other changes are possible
		if(!temp2.equals(tiles)) {
			clearRows();
			gravity();
		}
	}
	
	//draws the board
	public void drawBoard() {
		Game.graphics.setStroke(Game.dark);
		Game.graphics.setLineWidth(2);

		//draw board 
		Game.graphics.strokeLine(Game.boardLeftX, Tile.tileW, Game.boardLeftX, Game.boardBottomY);
		Game.graphics.strokeLine(Game.boardRightX, Tile.tileW, Game.boardRightX, Game.boardBottomY);

		Game.graphics.strokeLine(Game.boardLeftX, Game.boardBottomY, Game.boardRightX, Game.boardBottomY);
	}

	
	public void endTurn() {
		gravity();
		clearRows();
		
		//checks if game over
		isLost = isLost();
	}
	
	public boolean isRunning() {
		return gameRunning;
	}
	
	public void generateRow() {
		Random r = new Random(); 

		int prevX = Game.boardLeftX;
		int spaceLeft = 10;

		//generate num of tiles (1 - 3)
		int numTiles = r.nextInt(3) + 1;

		//space them
		ArrayList<Tile> temp = new ArrayList<>();

		for(int i = 0; i < numTiles; i++) {
			//genertes random size and spacing
			int randSize = r.nextInt(3) + 1;
			int randSpace = r.nextInt(3);

			//reduces the remaining space
			spaceLeft -= randSize + randSpace;

			//if there is negative space, the tile will be out of bounds
			if(spaceLeft <= 0) {
				break;
			}

			//if the tile is not out of bounds, it is added
			temp.add(new Tile(prevX + randSpace * Tile.tileW, Game.boardStartH, randSize));

			//reference for the x is changed
			prevX += (randSpace + randSize) * Tile.tileW;
		}

		//tiles are added to the main arraylist
		tiles.addAll(temp);
	}
	
	public void clearRows() {
		//if the rows are full i.e. if the total lengths is ten, it gets deleted 
		for(int i = 0; i < Game.boardNumRows; i++) {

			ArrayList<Tile> temp = getRow(i);
			
			if(temp.isEmpty()) {
				continue;
			}

			int sum = 0;

			for(Tile t : temp) {
				sum += t.getSize();
			}

			if(sum == 10) {
				//if the row is the same colour, bonus points arise
				boolean combo = true;
				String color = temp.get(0).getColour();
				
				//remove row
				for(int j = 0; j < tiles.size(); j++) {
					for(Tile t : temp) {
						if(t.equals(tiles.get(j))) {
							tiles.remove(j);
						}
						
						if(!color.equals(t.getColour())) {
							combo = false;
						}
					}
				}
				
				//adds 10 points for each row removed
				score += 10;
				
				//if it is a combo, 20 more poins are awarder
				if(combo) {
					score += 20;
				}
				
				//if the score is greater than the highscore, then it becomes the highscore
				if(score > highscores.getHighscore()) {
					highscores.writeHighScore(score);
				}
				
				//prints score to console
				System.out.println("Score: " + score);
				System.out.println("Highscore: " + highscores.getHighscore());
				System.out.println();
				
			}
		}
		
		gravity();
	}
	
	public ArrayList<Tile> getRow(int index) {
		//return rows
		ArrayList<Tile> temp = new ArrayList<>();

		for(Tile t : tiles) {
			if(t.getY() == index * Tile.tileW) {
				temp.add(t);
			}
		}

		return temp;	
	}
	
	public boolean isLost() {
		return !this.getRow(1).isEmpty();
	}

	public void draw() {
		//draws buttons
		exit.draw(mX, mY);
		restart.draw(mX, mY);
		
		//draws tile
		for(int i = 0; i < tiles.size(); i++) {
			tiles.get(i).draw();
		}
		
		//draws board
		drawBoard();
		
		if(drawGrid) {
			drawGrid();
		}
		
		//draws gameover if te game is over
		if(isLost) {
			Game.graphics.drawImage(loseImage, (Game.boardRightX - Game.boardLeftX - loseImage.getWidth()) / 2 + Game.boardLeftX, (Game.windowH - loseImage.getHeight()) / 2);
		}
	}
	
	public void drawGrid() {
		int width = 48;
		Game.graphics.setStroke(Game.dark);
		Game.graphics.setLineWidth(1); 

		for(int i = 0; i < 16; i++) {
			Game.graphics.strokeLine(i * width, 0, i * width, Game.windowH);
			Game.graphics.strokeLine(0, i * width, Game.windowW, i * width);
		}
	}
	
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}

}
