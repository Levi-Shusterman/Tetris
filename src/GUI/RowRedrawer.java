package GUI;

import java.awt.Color;
import java.util.Arrays;

import javax.swing.JComponent;

public class RowRedrawer {
	
	JComponent[][] Array;
	int ROWS;
	int COLS;
	
	/*
	 * I envision this as keeping track of which rows
	 * are being filled up. When one of the array indices
	 * hits COLS in value, that row is filled
	 */
	int[] rowComplete;
	
	boolean[][] Filled;
	
	RowRedrawer(JComponent[][] array, int rows, int cols,
			boolean[][] filled){
		ROWS = rows;
		COLS = cols;
		Array = array;
		Filled = filled;
		
		rowComplete = new int[ROWS];

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
				
				rowComplete[x] = rowComplete[x-1];
				Filled[x] = Filled[x-1];

				
				for( i = 1; i < COLS; i++){
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
			
			// and empty of pieces
			rowComplete[0] = 0;
			
			Filled[0] = new boolean[COLS];
			for( int i = 0; i < COLS; i++)
				Filled[0][i] = false; 
			
			System.out.println(rowComplete[ROWS-1]);
	}
	
	 /**
	  * Adds one to a row index, when it is ten,
	  * it is time to redraw
	  * 
	  * @param row
	 * @throws Exception 
	  */
//	void updateRow(int row) throws Exception{
//		  rowComplete[row]++;
//		  
//		  if( rowComplete[row] > COLS)
//			  throw new Exception("Error in RowRedrawer.updateRow."
//			  		+ "\n Row index " + row + 
//			  		" " + rowComplete[row]);
//	}
//	
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
				Redraw(row);
			}
		}
	}
}
