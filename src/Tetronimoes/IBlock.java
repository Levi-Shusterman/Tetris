package Tetronimoes;

import java.awt.Color;
import java.util.Vector;

public class IBlock extends Tetronimo {
	
	IBlock(){
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
		Normal.add(new Position(0,1));
		Normal.add(new Position(1,1));
		Normal.add(new Position(2,1));
		Normal.add(new Position(3,1));
		
		Right= new Vector<Position>();
		Right.add(new Position(1,0));
		Right.add(new Position(1,1));
		Right.add(new Position(1,2));
		Right.add(new Position(1,3));

		Under = new Vector<Position>();
		Under.add(new Position(0,2));
		Under.add(new Position(1,2));
		Under.add(new Position(2,2));
		Under.add(new Position(3,2));
		
		Left= new Vector<Position>();
		Left.add(new Position(2,0));
		Left.add(new Position(2,1));
		Left.add(new Position(2,2));
		Left.add(new Position(2,3));
		

		/*
		 * Tetronimo starts out in normal configuration
		 */
		normal();
		
		color = Color.RED;
		
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
	

	@Override
	public void rotate() {
		if( Current == Normal)
			right();
		else if( Current == Right)
			under();
		else if( Current == Under)
			left();
		else if( Current == Left)
			normal();
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
		maxLower = 3;
		maxRight = 1;
		
		startRow = 0;
		startCol = 1;
		
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
		Current = Right;
		maxLower = 1;
		maxRight = 3;
		
		startRow = 1;
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
	void under() {
		// TODO Auto-generated method stub
		Current = Under;
		maxLower = 3;
		maxRight = 2;
		
		startRow = 0;
		startCol = 2;
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
		// TODO Auto-generated method stub
		Current = Left;
		maxLower = 2;
		maxRight = 3;
		
		startRow = 2;
		startCol = 0;
		
	}

	@Override
	public Position getStartPos() {
		// TODO Auto-generated method stub
		return new Position(startRow, startCol);
	}


}
