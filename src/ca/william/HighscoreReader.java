/*
 * HighscoreReader.java
 * Name: William H.
 * Date: January 24. 2019
 * 
 * 		Purpose: To read and write the highscore to a txt file
 * 
 * 		Methods: getHighscore
 * 				 HighScoreReader
 * 				 writeHighScore
 * 				 fileReadMethod
 */

package ca.william;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HighscoreReader {
	
	private File f;
	
	public HighscoreReader(File f) {
		this.f = f;
	}
	
	private String fileReadMethod() {	
		String output = "";
		
		try {
			//creates a reader
			BufferedReader in = new BufferedReader(new FileReader(f));
			String s;
			
			
			//loops until end of file
			while((s = in.readLine()) != null) {
				//uses the string that it read to instantiate a CandyStoreItem
				output += s;
			}
			
			in.close();
			
		} catch (FileNotFoundException e) { 
            System.out.println("Error: Cannot open file for reading."); 
        } catch (EOFException e) { 
            System.out.println("Error: EOF encountered, file may be corrupted."); 
        } catch (IOException e) { 
            System.out.println("Error: Cannot read from file."); 
        } 
		
		return output;
	}
	
	public void writeHighScore(int hs) {
		try {
			//creates a new writer
			PrintWriter out = new PrintWriter(new FileWriter(f));
			
			//writes hs
			out.println(hs);
			
			out.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: cannot open file for writing");
		}
		catch (IOException e) {
			System.out.println("Error: Cannot write to file.");
		}
	}
	
	public int getHighscore() {
		if(!fileReadMethod().isEmpty()) {
			return Integer.parseInt(fileReadMethod());
		} else {
			return 0;
		}
	}
	
	
	
	
}
