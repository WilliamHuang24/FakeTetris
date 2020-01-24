/*
 * Game.java
 * Name: William H.
 * Date: January 24. 2019
 * 
 * 		Purpose: To create the window and manage the menu and board
 * 
 * 		Methods: main
 * 				 hexToColor
 * 				 start
 */

package ca.william;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {

	//window information
	public static final String name = "Slydris";
	public static final int windowH = 600;
	public static final int windowW = 800;

	public static GraphicsContext graphics;

	//board 
	public static final int boardLeftX = Tile.tileW * 2;
	public static final int boardRightX = Tile.tileW * 12;
	public static final int boardBottomY = Tile.tileW * 12;

	public static final int boardStartH = 48;

	public static final int boardNumRows = 12;
	public static final int boardNumColumns = 10;
	
	//text display
	public Text curScore;
	public Text highScore;
	
	//colours
	public static final Color background = hexToColor("#EFF7FF");
	public static final Color dark = hexToColor("#231123");
	public static final Color aquamarine = hexToColor("#558C8C");
	public static final Color accent = hexToColor("#82204A");

	//sounds
	public final MediaPlayer backMusic = new MediaPlayer(new Media(this.getResource("/sounds/music/backgroundMusic.WAV")));

	//private fields
	private Menu menu;
	private Board board;

	public static Color hexToColor(String hex) {
		//remove '#'
		hex = hex.replace("#", "");
		
		//returns a color
		return Color.rgb(Integer.parseInt(hex.substring(0, 2), 16), 
				Integer.parseInt(hex.substring(2, 4), 16), 
				Integer.parseInt(hex.substring(4, 6), 16));
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		//create canvas
		Canvas canvas = new Canvas(windowW, windowH);
		
		//sets the graphics
		graphics = canvas.getGraphicsContext2D();
		graphics.setFill(background);
		graphics.setStroke(accent);
		
		Pane root = new Pane();
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		
		//initiaises the board and menu
		board = new Board();
		menu = new Menu();
		
		//starts event handler
		EventHandler eventHandler = new EventHandler(menu);
	
		scene.setOnMousePressed(e -> eventHandler.pressEvent(e));
		scene.setOnMouseDragged(e -> eventHandler.dragEvent(e));
		scene.setOnMouseReleased(e -> eventHandler.releaseEvent(e));
		scene.setOnMouseMoved(e -> eventHandler.moveEvent(e));
		
		
		//setting the stage
		stage.setScene(scene);
		stage.setWidth(windowW);
		stage.setHeight(windowH + 40);
		stage.setResizable(false);
		stage.getIcons().add(new Image(this.getResource("/images/game/blueSmallOne.png")));
		stage.setTitle(name);
		stage.show();
		
		
		//starts the music
		backMusic.setAutoPlay(true);
		backMusic.setCycleCount(MediaPlayer.INDEFINITE);

		new Thread() {

			public void run() {
				while(stage.isShowing()) {
					
					menu = new Menu();
					eventHandler.setClickable(menu);
					
					//draws the menu while it is running
					while(stage.isShowing() && menu.isRunning()) {
						Game.graphics.fillRect(0, 0, windowW, windowH);
						
						menu.draw();
						
						try {
							Thread.sleep(25);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if(menu.isExit()) {
						System.exit(0);
					}
					
					//board
					board = new Board();
					eventHandler.setClickable(board);
					
					
					//drass the board once the board is running
					while (stage.isShowing() && board.isRunning()) {
						Game.graphics.fillRect(0, 0, windowW, windowH);
	
						board.draw();
	
						try {
							Thread.sleep(25);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
				
				//exits the thread
				System.exit(0);
			}
		}.start();		
	}

	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}
}
