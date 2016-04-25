package Tetronimoes;

import java.awt.Color;
import java.util.Vector;

public class OBlock extends Tetronimo {
	OBlock(){
		/*
		 * Set up configurations
		 */
		
		/*
		 * How a piece is positioned
		 * 
		 * 0,0  0,1  0,2  0,3
		 * 1,0  1,1  1,2  1,3
		 * 2,0  2,1  2,2  2,3
		 * 3,0  3,1  3,2  3,3
		 * 
		 * Note, some later tetronimos will need
		 * a 4 by 4 representation
		 */
		Normal = new Vector<Position>();
		Normal.add(new Position(0,0));
		Normal.add(new Position(0,1));
		Normal.add(new Position(1,1));
		Normal.add(new Position(1,0));
		
		
		/*
		 * Tetronimo starts out in normal configuration
		 */
		normal();
		
		color = Color.BLUE;
		
	}
	
	@Override
	public Vector<Position> getCurrentPos() {
		// TODO Auto-generated method stub
		return Current;
	}
	
	@Override
	public Position getMaxExtent() {
		// TODO Auto-generated method stub
		return new Position(maxLower, maxRight);
	}
	
	/**
	 * A square doesn't rotate anyway
	 */
	@Override
	public void rotate() {
	}

	/*
	 * How a piece is positioned
	 * 
	Visual aid
	 * 0,0  0,1  0,2  0,3
	 * 1,0  1,1  1,2  1,3
	 * 2,0  2,1  2,2  2,3
	 * 3,0  3,1  3,2  3,3
	 */ 
	@Override
	void normal() {
		Current = Normal;
		maxLower = 1;
		maxRight = 1;
		
		startRow = 0;
		startCol = 0;
	}

	/*
	 * How a piece is positioned
	 * 
	Visual aid
	 * 0,0  0,1  0,2  0,3
	 * 1,0  1,1  1,2  1,3
	 * 2,0  2,1  2,2  2,3
	 * 3,0  3,1  3,2  3,3
	 */ 
	@Override
	void right() {
	}
	
	/*
	 * How a piece is positioned
	 * 
	Visual aid
	 * 0,0  0,1  0,2  0,3
	 * 1,0  1,1  1,2  1,3
	 * 2,0  2,1  2,2  2,3
	 * 3,0  3,1  3,2  3,3
	 */ 
	@Override
	void under() {
	}

	/*
	 * How a piece is positioned
	 * 
	Visual aid
	 * 0,0  0,1  0,2  0,3
	 * 1,0  1,1  1,2  1,3
	 * 2,0  2,1  2,2  2,3
	 * 3,0  3,1  3,2  3,3
	 */ 
	@Override
	void left() {
		
	}

	@Override
	public Position getStartPos() {
		// TODO Auto-generated method stub
		return new Position(startRow, startCol);
	}


}
