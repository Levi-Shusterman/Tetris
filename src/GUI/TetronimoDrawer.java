package GUI;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JComponent;

import Tetronimoes.*;

public class TetronimoDrawer {
	/*
	 * Array on which to draw
	 */
	int ROWS;
	int COLS;
	JComponent[][] Array;
	
	int currentRow;
	int currentCol;
	
	/*
	 * Factory parameters
	 */
	boolean 		 atBottom;
	TetronimoFactory Factory;
	Tetronimo 		 Tet;
	Vector<Position> currentPos;
	Position 		 currentMax;
	
	TetronimoDrawer(JComponent[][] array, int rows, int cols){
		Array = array;
		ROWS = rows; COLS = cols;
		
		
		Factory = new TetronimoFactory();
		
		atBottom = true;
		resetStartPos();
		// to start out game
	}
	
	void resetStartPos(){
		currentRow = -1;
		currentCol = (COLS/2) - 1;
	}
	
	public synchronized void Next(){
		if( atBottom){
			Tet = Factory.getNewTetronimo();
			currentPos = Tet.getCurrentPos();
			currentMax = Tet.getMaxExtent();
			
			atBottom = false;
			
			resetStartPos();
		}

		undraw();
		currentRow++;
		draw();
		
		
		if( currentMax.Row + currentRow == ROWS - 1){
			atBottom = true;
		}
		
	}
	
	public void Down(){
		Next();
	}
	
	void undraw(){
		if (currentRow == -1)
			return;
		
		for( Position pos : currentPos){
			Array[currentRow + pos.Row][currentCol + pos.Col].setBackground(
					Color.black);
		}
	}
	void draw(){
		
		for( Position pos : currentPos){
			Array[currentRow + pos.Row][currentCol + pos.Col].setBackground(
					Color.yellow);
		}
	}
}
