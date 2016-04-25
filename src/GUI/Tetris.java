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

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
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
	
	// Timer and associated labels
	private JLabel timeLabel;
	private JLabel helpLabel;
	private JPanel timerPanel;
	private JLabel rowsLabel;
	private Timer timer; 
	Integer count;
	
	
	
	TetronimoDrawer drawer;
	
	public Tetris(){
		super("Tetris");
		container = getContentPane();
		
	    /*
	     * Set up timer
	     */
	    count = 0;
	    timeLabel = new JLabel();
	    timeLabel.setFocusable(false);
	    helpLabel = new JLabel("Press 'h' for help");
	    rowsLabel = new JLabel("Rows cleared" );
	  
	    timer = new Timer(500, new timerAction());
	    timer.start();
	    
	    timerPanel = new JPanel();
	    timerPanel.setFocusable(false);
	    
	    timerPanel.add(timeLabel, BorderLayout.EAST );
	    timerPanel.add(helpLabel, BorderLayout.WEST);
	    timerPanel.add(rowsLabel, BorderLayout.PAGE_END);
	    container.add(timerPanel, BorderLayout.PAGE_START);
	    
	    // Overall layout of the GUI
	    guiPanel = new JPanel();
	    guiPanel.setFocusable(false);
	    
	    guiPanel.setLayout((LayoutManager) new BoxLayout(guiPanel, BoxLayout.PAGE_AXIS));
	    container.add(guiPanel, BorderLayout.AFTER_LAST_LINE);
	    
	    // Add the game grid that will contain the pieces
	    gameGrid = new JPanel();
	    gameGrid.setFocusable(false);
	    
	    board = new GridLayout(ROWS , COLS);
	    gameGrid.setLayout(board);
	    positions = new JButton[ROWS][COLS];
	    
	    for( int i = 0; i < ROWS; i++)
	    	for(int j = 0; j < COLS; j++){
	    		positions[i][j] = new JButton();
	    		positions[i][j].setBackground(Color.black);
	    		positions[i][j].setVisible(true);
	    		positions[i][j].setFocusable(false);
	    		gameGrid.add(positions[i][j]);
	    	}
	
	    container.add(gameGrid, BorderLayout.CENTER);
	    gameGrid.setVisible(true);
	    
	    
	    this.addKeyListener(new buttonAction());
	    
	    drawer = new TetronimoDrawer(positions, ROWS, COLS,rowsLabel);
	    
	    setSize(500,500);
	    setVisible( true );
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
    		drawer.Next();
    	}
	}
	
	private class buttonAction extends KeyAdapter{
		buttonAction(){
			super();
		}
		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println("Pressed " + e.getKeyChar()  );

			switch( e.getKeyChar()){
			case 'h' : case 'H':
				System.out.println("In if statement " + e.getKeyChar() );
				timer.stop();
				
				 JOptionPane.showMessageDialog(null, 
						"H for help\n"+
						"Left arrow to shift left\n" +
						"P to pause\n" +
						"ESC to exit\n",
						"How to play Tetris",
						JOptionPane.INFORMATION_MESSAGE
						);
				 timer.start();
				 break;
			case 'p': case 'P':
				timer.stop();
				helpLabel.setText("Press enter to resume");
				break;
			default:
				break;
			}
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			switch( e.getKeyCode()){
			case KeyEvent.VK_ESCAPE:
				timer.stop();
				System.exit(0);
			case KeyEvent.VK_DOWN:
				drawer.Down();
				break;
			case KeyEvent.VK_ENTER:
				helpLabel.setText("Press 'h' for help");
				timer.start();
				break;
			case KeyEvent.VK_RIGHT:
//				timer.stop();
				drawer.Right();
//				timer.start();
				break;
			case KeyEvent.VK_LEFT:
//				timer.stop();
				drawer.Left();
//				timer.start();
				break;
			case KeyEvent.VK_UP:
				drawer.Rotate();
				break;
			}

		}
	}
}
