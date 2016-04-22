package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Tetris extends JFrame {
	
	/*
	 * Game board parameters
	 */
	static int ROWS = 20;
	static int COLS = 10;
	
	/*
	 * Gui objects 
	 */
	private JPanel guiPanel;
	private java.awt.Container container;
	
	// The grid that holds individual positions
	private JPanel gameGrid;
	private JLabel[][] positions;
	
	public Tetris(){
		super("Tetris");
		container = getContentPane();
		
		// Overall layout of the GUI
		guiPanel = new JPanel();
	    guiPanel.setLayout((LayoutManager) new BoxLayout(guiPanel, BoxLayout.PAGE_AXIS));
	    container.add(guiPanel, BorderLayout.AFTER_LINE_ENDS);
	    
//	     add the game grid that will contain the pieces
//	    gameGrid = new JPanel();
//	    gameGrid.setLayout(new GridLayout(ROWS , COLS));
//	    positions = new JLabel[ROWS][COLS];
//	    
//	    for( int i = 0; i < ROWS; i++)
//	    	for(int j = 0; j < ROWS; j++){
//	    		positions[i][j] = new JLabel();
//	    		positions[i][j].setBackground(Color.black);
////	    		positions[i][j].setVisible(true);
//	    		gameGrid.add(positions[i][j]);
//	    	}
//	
//	    container.add(gameGrid, BorderLayout.CENTER);
//	    gameGrid.setVisible(true);
//	    
	    setSize(700,700);
	    setVisible( true );
	}
	
	public static void main(String[] args) {
		Tetris gui = new Tetris();
	    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
