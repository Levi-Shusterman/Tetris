package GUI;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.Timer;



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
	private JButton[][] positions;
	private GridLayout board;
	
	// Timer
	private JLabel timeLabel;
	private JLabel helpLabel;
	private JPanel timerPanel;
	private Timer timer; 
	Integer count;
	
	public Tetris(){
		super("Tetris");
		container = getContentPane();
		
	    /*
	     * Set up timer
	     */
	    count = 0;
	    timeLabel = new JLabel();
	    helpLabel = new JLabel("Press 'h' for help");
	  
	    timer = new Timer(2000, new timerAction());
	    timer.start();
	    timerPanel = new JPanel();
	    timerPanel.add(timeLabel, BorderLayout.EAST );
	    timerPanel.add(helpLabel, BorderLayout.WEST);
	    container.add(timerPanel, BorderLayout.PAGE_START);
	    
	    // Overall layout of the GUI
	    guiPanel = new JPanel();
	    guiPanel.setLayout((LayoutManager) new BoxLayout(guiPanel, BoxLayout.PAGE_AXIS));
	    container.add(guiPanel, BorderLayout.AFTER_LAST_LINE);
	    
	    // Add the game grid that will contain the pieces
	    gameGrid = new JPanel();
	    board = new GridLayout(ROWS , COLS);
	    gameGrid.setLayout(board);
	    positions = new JButton[ROWS][COLS];
	    
	    for( int i = 0; i < ROWS; i++)
	    	for(int j = 0; j < COLS; j++){
	    		positions[i][j] = new JButton();
	    		positions[i][j].setBackground(Color.black);
	    		positions[i][j].setVisible(true);
	    		gameGrid.add(positions[i][j]);
	    	}
	
	    container.add(gameGrid, BorderLayout.CENTER);
	    gameGrid.setVisible(true);
	    
	    
	    setSize(700,700);
	    setVisible( true );
	    addKeyListener(new buttonAction());
	}
	
	public static void main(String[] args) {
		Tetris gui = new Tetris();
	    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	private class timerAction implements ActionListener{
		timerAction(){
			super();
		}
		@Override
    	public void actionPerformed(ActionEvent e) {
    		timeLabel.setText(count.toString());
    		count += 1;
    	}
		
	}
	
	private class buttonAction extends KeyAdapter{
		buttonAction(){
			super();
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if( e.getKeyChar() == 'h' ){
				timer.stop();
				
				
				 JOptionPane.showMessageDialog(null, 
						"H for help\n"+
						"Left arrow to shift left\n",
						"How to play Tetris",
						JOptionPane.INFORMATION_MESSAGE
						);
				 timer.start();
			}
		}
	
	}
}
