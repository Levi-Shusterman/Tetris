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
	
	/*
	 * Indicates what positions are taking up 
	 * by a tetronimo that has been locked in place
	 */
	boolean[][]    Filled; 
	
	/*
	 * Current row and col that a moving tetronimo is on
	 */
	int currentRow;
	int currentCol;
	
	/*
	 * I envision this as keeping track of which rows
	 * are being filled up. When one of the array indices
	 * hits COLS in value, that row is filled
	 */
	int[] rowComplete;
	
	/*
	 * Factory parameters
	 */
	boolean 		 atBottom;
	TetronimoFactory Factory;

	/*
	 * Tetronimo parameters
	 */
	Tetronimo 		 Tet;
	Vector<Position> currentPos;
	Position 		 currentMax;   // maximum extent of the tetronimo
	Position 	     currentStart; // Starting position of the tetronimo
	
	private Semaphore available;
	

	TetronimoDrawer(JComponent[][] array, int rows, int cols){
		available = new Semaphore(1);
		Array = array;
		ROWS = rows; COLS = cols;
		
		
		Factory = new TetronimoFactory();
		
		// Get first tetris piece
		resetTetrisPiece();
		
		
		
		Filled = new boolean[ROWS][COLS];
		for( int i = 0; i < ROWS;i++)
			for(int j = 0; j < COLS; j++)
				Filled[i][j] = false;
		
		rowComplete = new int[ROWS];
		Arrays.fill(rowComplete, 0);
	}
	
	/**
	 * Get a new piece and reset the position vars
	 */
	private void resetTetrisPiece(){
		Tet = Factory.getNewTetronimo();
		currentPos = Tet.getCurrentPos();
		currentMax = Tet.getMaxExtent();
		currentStart = Tet.getStartPos();
		
		currentRow = -1;
		currentCol = (COLS/2) - 1;
	}
	
	/**
	 * Called by the Timer. Shifts the
	 * Tetris piece down by one 
	 * 
	 */
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
		
		/*
		 * Since you're at the bottom just return
		 */
		if( atBottom){
			available.release();
			return;
		}
		
		/*
		 * Now you can move down
		 */
		undraw();
		currentRow++;
		draw();

		available.release();
	}
	
	/**
	 * Rotate the tetronimo piece clockwise
	 * Haven't implemented checking if piece can be rotated
	 */
	public void Rotate(){
		try {
			available.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		undraw();

		Tet.rotate();
		currentPos = Tet.getCurrentPos();
		currentMax = Tet.getMaxExtent();
		currentStart = Tet.getStartPos();
		
		draw();
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
	
	/**
	 * Called by GUI when user hits down key
	 */
	public void Down(){
		Next();
	}
	
	/**
	 * Undraw the tetris piece in prep for next move
	 */
	void undraw(){
		/*
		 * Case when a new tetronimo is coming in
		 */
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
		/*
		 * This case occurs when the event loop reset the piece
		 */
		if( currentRow < 0)
			return false;
		
		// Check that you won't overflow if your shifting right
		if( offset > 0 &&
				currentCol + currentMax.Col + offset > COLS - 1 	)
			// Can't move to the side
			{ return false; }
		
		// Check that you won't underflow if you're shifting left
		if( offset < 0 && currentCol + offset + currentStart.Col < 0)
			{ return false;}
		
		for( Position pos : currentPos){
			
			if( Filled[currentRow + pos.Row][currentCol + offset + pos.Col])
				return false;
		}
		
		return true;
	}
	
		
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
		
		if( atBottom){
			fill();
			
			resetTetrisPiece();
			
			atBottom = false;
			available.release();
			return;
		}
		
		
		/*
		 * if you can shift right
		 */
		if( canMoveSide(1)){
			
			// undraw first
			undraw();
			
			// then shift and draw
			currentCol++;
			draw();
		}
		
		available.release();
	}
	
	
	/**
	 * Shift the piece left
	 */
	public void Left(){
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
			available.release();
			return;
		}
		
		
		/*
		 * if you can shift left
		 */
		if( canMoveSide(-1)){
			
			// undraw first
			undraw();
			
			// then shift and draw
			currentCol--;
			draw();
		}

		available.release();
	}
}
