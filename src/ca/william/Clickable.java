/*
 * Clickable.java
 * Name: William H.
 * Date: January 24. 2019
 * 
 * 		Purpose: To have methods to be implemented on classes that use the mouse
 * 
 * 		Methods: pressEvent
 * 				 dragEvent
 * 				 releaseEvent
 * 				 moveEvent
 */

package ca.william;

public interface Clickable {

	public void pressEvent(int mx, int my);	
	public void dragEvent(int mx, int my);
	public void releaseEvent(int mx, int my);
	public void moveEvent(int mx, int my);
}
