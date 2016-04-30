package GUI;

import java.awt.Color;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
This class handles how rows are labeled as being filled, redrawing rows, and also setting up the game if the new/reset button is pressed.
How we handle clearing rows is by basically copying the row above it to the current row. This continues for each and every row.
Of course, this only happens if the row is "filled".
 */

public class RowRedrawer {
	
	JComponent[][] Array;
	int ROWS;
	int COLS;
	
	int clearedRows;
	boolean[][] Filled;
	
	JLabel rowLabel;
	JLabel TimeLabel;
	Tetris ParentPointer;
	
	RowRedrawer(JComponent[][] array, int rows, int cols,
			boolean[][] filled, JLabel rowsLabel, JLabel timeLabel, Tetris parentPointer){
		ROWS = rows;
		COLS = cols;
		Array = array;
		Filled = filled;
		rowLabel = rowsLabel;
		clearedRows = 0;
		
		TimeLabel = timeLabel;
		ParentPointer = parentPointer;
	}
	
	/**
	 * Redraws the board when a row is filled up
	 * 
	 * @param row
	 */
	 void Redraw(int row){
		
		int x = row;
		while( x >= 1 ){
			
			/*
			 * Set the color of the row to the row on top of it
			 */
			int i = 0;
			try{
				
				Filled[x] = Filled[x-1];

				
				for( i = 0; i < COLS; i++){
					Array[x][i].setBackground(Array[x-1][i].getBackground());
				}	
			}catch( ArrayIndexOutOfBoundsException | NullPointerException ex){
				System.out.println("Out of bounds in RowRedraw");
				System.out.println("Row: " + x);
				System.out.println("Col: " + i);
			}
			x--;
		}
		
		/*
		 * Top row should be black
		 * and completely reset
		 */
			for( int i = 0; i < COLS; i++){
				Array[0][i].setBackground(Color.BLACK);
			}
			
			
			Filled[0] = new boolean[COLS];
			for( int i = 0; i < COLS; i++)
				Filled[0][i] = false; 
			
	}
	
	/**
	 * Checks if any rows need to be redrawn
	 */
	void checkRowsFilledUp() {
		for(int row = 0; row < ROWS; row++){
			
			/*
			 * Lets check if the whole row is filled
			 */
			int fill = 0;
			for(int col= 0; col < COLS; col++){
				if( Array[row][col].getBackground() != Color.black)
					fill++;
			}
			
			/*
			 * The row is filled up with tetris pieces
			 * So we have to do some shifting
			 */
			if( fill == COLS){
				clearedRows++;
				rowLabel.setText("   " + Integer.toString(clearedRows) + "    ");
				
				Redraw(row);
			}
		}
	}
	
	/**
	 * Bring up a message to the user when the game is over
	 * and show her how many rows she has cleared.
	 * 
	 * If she chooses to exit then exit.
	 * Else redraw and reset the board.
	 */
	 void endOfGame() {
		ParentPointer.pauseTimer();
		int yesOrNo = JOptionPane.showOptionDialog(ParentPointer, 
				"The game is over.\n" + "You have cleared " + Integer.toString(clearedRows) + " rows.\n" +
				 "Would you like to play again?",
				"Game Over", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.PLAIN_MESSAGE, 
				null, null, null);
		
		if( yesOrNo == 1){
			System.exit(0);
		}else if( yesOrNo == 0){
			resetBoard();
		}
		ParentPointer.startTimer();
	}
	
	void resetBoard(){
		
		clearedRows = 0;
		ParentPointer.resetGameAndTimer();
		for( int i = 0; i < ROWS; i++)
			for( int j = 0; j < COLS; j++){
				Filled[i][j] = false;
				Array[i][j].setBackground(Color.black);
			}
				
	}
}
