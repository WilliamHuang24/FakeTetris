/*
 * EventHandler.java
 * Name: William H.
 * Date: January 24. 2019
 * 
 * 		Purpose: to handle events and pass them to the right object
 * 
 * 		Methods: pressEvent
 * 				 dragEvent
 * 				 releaseEvent
 * 				 moveEvent
 */

package ca.william;

import javafx.scene.input.MouseEvent;

public class EventHandler {
	Clickable o;
	
	//sets the object
	public EventHandler(Clickable o) {
		this.o = o;
	}
	
	//passes the x y to the stored object
	public void moveEvent(MouseEvent e) {
		o.moveEvent((int) e.getSceneX(), (int) e.getSceneY());
	}
	
	//passes the x y to the stored object
	public void pressEvent(MouseEvent e) {
		o.pressEvent((int) e.getSceneX(), (int) e.getSceneY());
	}
	
	//passes the x y to the stored object
	public void releaseEvent(MouseEvent e) {
		o.releaseEvent((int) e.getSceneX(), (int) e.getSceneY());
	}
	
	//passes the x y to the stored object
	public void dragEvent(MouseEvent e ) {
		o.dragEvent((int) e.getSceneX(), (int) e.getSceneY());
	}
	
	
	//sets the clickable object
	public void setClickable(Clickable c) {
		o = c;
	}
}
