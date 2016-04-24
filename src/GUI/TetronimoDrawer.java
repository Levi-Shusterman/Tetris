package GUI;

import java.awt.Color;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import javax.swing.JComponent;

import com.sun.glass.events.KeyEvent;

import Tetronimoes.*;

public class TetronimoDrawer {
	/*
	 * Array on which to draw
	 */
	int ROWS;
	int COLS;
	JComponent[][] Array;
	boolean[][]    Filled;
	
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
	
	private Semaphore available;
	
	TetronimoDrawer(JComponent[][] array, int rows, int cols){
		available = new Semaphore(1);
		Array = array;
		ROWS = rows; COLS = cols;
		
		
		Factory = new TetronimoFactory();
		
		resetTetrisPiece();
		
		Filled = new boolean[ROWS][COLS];
		for( int i = 0; i < ROWS;i++)
			for(int j = 0; j < COLS; j++)
				Filled[i][j] = false;
	}
	
	/**
	 * Get a new piece and reset the position vars
	 */
	private void resetTetrisPiece(){
		Tet = Factory.getNewTetronimo();
		currentPos = Tet.getCurrentPos();
		currentMax = Tet.getMaxExtent();
		
		currentRow = -1;
		currentCol = (COLS/2) - 1;
	}
	
	public void Next(){
		try {
			available.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if( atBottom){
			fill();
			
			resetTetrisPiece();
			
			atBottom = false;
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
		if(!atBottom){
			if( !canMoveDown() ){
				atBottom = true;
			}
		}
		
		available.release();
	}
	
	/**
	 * Fill the current spot the tetronimo is at,
	 * indicating it is done moving
	 */
	private void fill() {
		for( Position pos : currentPos){
			
			Filled[currentRow + pos.Row][currentCol + pos.Col] = true;
		}
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
	private boolean canMoveDown() {
		for( Position pos : currentPos){
			
			if( Filled[currentRow + 1 + pos.Row][currentCol + pos.Col])
				return false;
		}
		
		return true;
	}
	
	/**
	 * Checks if the piece can be moved to the side
	 * 
	 * @return false if cannot be, true if can be
	 */
	private boolean canMoveSide(int offset) {
		for( Position pos : currentPos){
			
			if( Filled[currentRow + pos.Row][currentCol + pos.Col])
				return false;
		}
		
		return true;
	}
	
	
	//TODO Change Left and Right to derivatives of Next
	
	/**
	 * Shift the piece right
	 */
	public void Right() {
		try {
			available.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( currentCol + 1 + currentMax.Col < COLS ){
			
			/*
			 * if you can shift right
			 */
			currentCol++;
			if( canMoveSide(1)){
				
				// undraw first
				currentCol--;
				undraw();
				
				// then shift and draw
				currentCol++;
				draw();
				if( !canMoveDown())
					fill();
			}
		}
		available.release();
	}
	
	/**
	 * Shift the piece left
	 */
	public void Left() {
		try {
			available.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if( currentCol != 0){
			
			/*
			 * if you can shift left
			 */
			currentCol--;
			if( canMoveSide(-1)){
				
				// undraw first
				currentCol++;
				undraw();
				
				// then shift and draw
				currentCol--;
				draw();
				if( !canMoveDown())
					fill();
			}
		}
		available.release();
	}
}
