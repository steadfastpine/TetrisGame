/**
 * Handles events for the Tetris Game.  User events (key strokes) as well as periodic timer
 * events.
 * 
 * @author CSC 143
 */
package tetrisgame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

public class EventController extends KeyAdapter implements ActionListener {

	private Game game; // current game: grid and current piece
	private Timer timer;
	private static final double PIECE_MOVE_TIME = 0.8;	// wait 0.8 s every time
	private boolean gameOver;

	/**
	 * Creates an EventController to handle key and timer events.
	 * 
	 * @param game
	 *            the game this is controlling
	 */
	public EventController(Game game) {
		this.game = game;
		gameOver = false;
		double delay = 1000 * PIECE_MOVE_TIME; // in milliseconds
		timer = new Timer((int) delay, this);
		timer.setCoalesce(true); // if multiple events pending, bunch them to
		// 1 event
		timer.start();
	}

	/**
	 * Responds to special keys being pressed.
	 * 
	 * Currently just responds to the space key and the q(uit) key
	 */
	public void keyPressed(KeyEvent e) {
		// if 'Q', quit the game
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			timer.stop();
			((JFrame) e.getSource()).dispose();
		}
		
		if (!gameOver) {
			switch (e.getKeyCode()) {
			
				case KeyEvent.VK_SPACE:
					handleMove(Direction.DROP);
					// added by Scott Forsberg - 2019-10-02
					// prototype alternate dropdown feature
					//Tetris.downPushed = true;
					// verbose output
					//System.out.println("class:EventController - Tetris.downPushed = true");
					break;
				
				case KeyEvent.VK_S:
					handleMove(Direction.DROP);
					break;	
					
					
				// added by Scott Forsberg - 2019-10-02
				case KeyEvent.VK_LEFT:
					handleMove(Direction.LEFT);
					break;	

				case KeyEvent.VK_A:
					handleMove(Direction.LEFT);
					break;						
					
					
				// added by Scott Forsberg - 2019-10-02
				case KeyEvent.VK_RIGHT:
					handleMove(Direction.RIGHT);
					break;				
				
				// alternate right key
				case KeyEvent.VK_D:
					handleMove(Direction.RIGHT);
					break;			
					
					
				// added by Scott Forsberg - 2019-10-07
				case KeyEvent.VK_DOWN:
					handleRotate();
					break;				
						
				// alternate rotate key
				case KeyEvent.VK_R:
					handleRotate();
					break;	
					
				// alternate rotate key
				case KeyEvent.VK_W:
					handleRotate();
					break;					
					
			}
		}
	}

	/** Updates the game periodically based on a timer event */
	public void actionPerformed(ActionEvent e) {
		
		// added by Scott Forsberg - 2019-10-02
		// prototype alternate dropdown
		/*
		if (Tetris.downPushed == true) {
			timer.setDelay(0);
		}else {
			timer.setDelay(800);		
		}
		*/
		
		handleMove(Direction.DOWN);
	}

	/**
	 * Update the game by moving in the given direction
	 */
	private void handleMove(Direction direction) {
		game.movePiece(direction);
		gameOver = game.isGameOver();
		if (gameOver)
			timer.stop();
	}
	
	
	private void handleRotate() {
		game.rotatePiece();
		gameOver = game.isGameOver();
		if (gameOver)
			timer.stop();
	}
}
