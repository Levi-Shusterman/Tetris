package GUI;

import java.awt.Color;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.sun.glass.events.KeyEvent;

import Tetronimoes.*;

public class TetronimoDrawer {
	/*
	 * Array on which to draw
	 */
	int ROWS;
	int COLS;
	JComponent[][] Array;
	RowRedrawer rowRedrawer;
	
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
	

	TetronimoDrawer(JComponent[][] array, int rows, int cols, JLabel rowsLabel,
			JLabel timeLabel, Tetris parentPointer){
		available = new Semaphore(1);
		Array = array;
		ROWS = rows; COLS = cols;
		
		
		Factory = new TetronimoFactory();
		
		Filled = new boolean[ROWS][COLS];
		for( int i = 0; i < ROWS;i++)
			for(int j = 0; j < COLS; j++)
				Filled[i][j] = false;

		// Get first tetris piece
		rowRedrawer = new RowRedrawer(Array, ROWS, COLS, Filled,rowsLabel,timeLabel,parentPointer);
		resetTetrisPiece();
		
	}
	
	/**
	 * Get a new piece and reset the position vars
	 * 
	 * Richard: In this method, when we acquire a new tetris piece
	 * because the last one was locked in place, we should prob
	 * check if a row has been filled up.
	 */
	private void resetTetrisPiece(){
		try {
			Tet = Factory.getNewTetronimo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * See if we have to redraw any rows
		 */
		rowRedrawer.checkRowsFilledUp();
		
		/*
		 * Get a new tetris piece
		 */
		currentPos = Tet.getCurrentPos();
		currentMax = Tet.getMaxExtent();
		currentStart = Tet.getStartPos();
		
		/*
		 * Reset the currrent indices
		 */
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
			e.printStackTrace();
		}
		/*
		 * Indication of game over
		 */
		if( atBottom && currentRow == -1){
			atBottom = false;
			rowRedrawer.endOfGame();
			available.release();
			return;
		}
		
		/*
		 * Tetronimo can no longer move
		 */
		if( atBottom){
			fill();
			
			resetTetrisPiece();
			
			atBottom = false;
			available.release();
			return;
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
		 * Now you can move down if you haven't hit anything
		 */
		undraw();
		currentRow++;
		draw();

		available.release();
	}
	
	/**
	 * Rotate the tetronimo piece clockwise
	 */
	public void Rotate(){
//		if(atBottom)
//			return;
//		
		try {
			available.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		undraw();
		
		rotateTet();
		
		draw();
		available.release();
	}
		
	/**
	 * Check that you can rotate
	 * so that you don't hit another piece
	 */
	private boolean canRotate(){ 
		if(currentRow<0)
			return false;
		
		for( Position pos: currentPos){
			if(Filled[currentRow + pos.Row][currentCol + pos.Col])
				return false;
		}
		
		return true;
	}
	
	private void rotateTet(){
		/*
		 * If we find out that we can't rotate later in this method 
		 * because of other blocks, we will reset to these values
		 */
		int cCol = currentCol;
		int cRow = currentRow;
		
		/*
		 * Rotate and reset position parameters
		 */
		Tet.rotate();
		currentPos = Tet.getCurrentPos();
		currentMax = Tet.getMaxExtent();
		currentStart = Tet.getStartPos();
		
		// Check that you won't overflow to the right
		if( currentCol + currentMax.Col  > COLS - 1 	)
			// Can't move to the side
		{ currentCol -= currentMax.Col -1; }
		
		// Check that you won't overflow to the left
		if( currentCol + currentStart.Col < 0)
			{ currentCol++;}
		
		/*
		 * If you can't rotate, 
		 * reset everything
		 */
		if(!canRotate()){
			Tet.unRotate();
			currentPos = Tet.getCurrentPos();
			currentMax = Tet.getMaxExtent();
			currentStart = Tet.getStartPos();
			currentCol = cCol;
			currentRow = cRow;
		}
	}
	
	/**
	 * Fill the current spot the tetronimo is at,
	 * indicating it is done moving
	 * 
	 */
	private void fill() {
		
		for( Position pos : currentPos){
			try{
				Filled[currentRow + pos.Row][currentCol + pos.Col] = true;
			}catch( ArrayIndexOutOfBoundsException ex){
				/*
				 * End of game should happen here...
				 * We may have to redo this though in case other bugs in this section
				 * pop up that don't correspond to the end of the game
				 */
				ex.printStackTrace();
				System.out.println(atBottom);
//				atBottom = false;
//				rowRedrawer.endOfGame();
				
//				System.out.println("Out of bounds in fill");
//				System.out.println("Row: " + currentRow);
//				System.out.println("Col: " + currentCol);
			}
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
			try{
				Array[currentRow + pos.Row][currentCol + pos.Col].setBackground(
						Color.black);
			}catch( NullPointerException | ArrayIndexOutOfBoundsException ex){
				System.out.println("CurrentRow: " + currentRow);
				System.out.println("CurrentCol: " + currentCol);
				
				System.out.println("TetMaxRow: " + currentMax.Row);
				System.out.println("TetMaxCol: " + currentMax.Col);
			}
		}
	}
	
	/**
	 * Draw the tetris piece on the board
	 */
	void draw(){
		
		for( Position pos : currentPos){
				
			try{
				Array[currentRow + pos.Row][currentCol + pos.Col].setBackground(
						Tet.color);
			}catch( NullPointerException | ArrayIndexOutOfBoundsException ex){
				System.out.println("CurrentRow: " + currentRow);
				System.out.println("CurrentCol: " + currentCol);
				
				System.out.println("TetMaxRow: " + currentMax.Row);
				System.out.println("TetMaxCol: " + currentMax.Col);
				ex.printStackTrace();
				///////
				undraw();
				return;
			}
		}
	}
	
	/**
	 * Checks if the piece can be moved down one more
	 * 
	 * @return false if cannot be, true if can be
	 */
	private boolean canMoveDown() {
		/*
		 * We are at bottom
		 */
		if( currentRow + 1 + currentMax.Row > ROWS - 1)
			return false;
		
		for( Position pos : currentPos){
			
			try{
				/*
				 * Checks if the next row is filled with a different block
				 */
				if( Filled[currentRow + 1 + pos.Row][currentCol + pos.Col])
					return false;
			}catch( NullPointerException | ArrayIndexOutOfBoundsException ex){
				System.out.println("CurrentRow: " + currentRow);
				System.out.println("CurrentCol: " + currentCol);
				
				System.out.println("TetMaxRow: " + currentMax.Row);
				System.out.println("TetMaxCol: " + currentMax.Col);
				ex.printStackTrace();
			}
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
