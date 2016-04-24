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
		
		
		/*
		 * Now you can move down
		 */
		undraw();
		currentRow++;
		draw();
		
		/*
		 * Check if at the bottom
		 */
		if( currentMax.Row + currentRow == ROWS - 1){
			atBottom = true;
		}
		
		/*
		 * Check if about to hit another piece
		 */
//		if( !canMove() ){
//			atBottom = true;
//		}
	}
	
	public void Down(){
		Next();
	}
	
	/**
	 * Undraw the tetris piece in prep for next move
	 */
	void undraw(){
		if (currentRow == -1)
			return;
		
		for( Position pos : currentPos){
			Array[currentRow + pos.Row][currentCol + pos.Col].setBackground(
					Color.black);
		}
	}
	
	/**
	 * Draw the tetris piece on the board
	 */
	void draw(){
		
		for( Position pos : currentPos){
				
				Array[currentRow + pos.Row][currentCol + pos.Col].setBackground(
						Color.yellow);
		}
	}
	
	/**
	 * Checks if the piece can be moved down one more
	 * 
	 * @return false if cannot be, true if can be
	 */
	private boolean canMove() {
		for( Position pos : currentPos){
			
			if( Array[currentRow + currentMax.Row + 1][currentCol + pos.Col].getBackground()
					!= Color.black ){
				
				return false;
			}
		}
		
		return true;
	}
}
