package tetrisgame;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class TetrisUnitTest {
	
	
	/* 
	 * testRotation()
	 * Creates test grid and pieces
	 * Rotates them next to obstruction such as walls and other pieces, checks not rotate
	 * Rotates them in open grid, checks does rotate
	 */	
	@Test
	public void testRotation() {
		// create fresh & new grid
		Grid g = new Grid();

		
		// create LShape in middle
		LShape l = new LShape(1, 4, g);
		
		// define piece position arrays
		int[] pieceRows = new int[4];
		int[] pieceColumns = new int[4];
		
		// populate Shape row and columns
		for(int i = 0; i < 4; i++) {
			pieceRows[i] = l.getSquareTest(i).getRow();
			pieceColumns[i] = l.getSquareTest(i).getCol();
		}
		
		// rotate
		l.rotate();

		// test if rotated
		assertTrue(l.rotated);
		
		// create LShape in left col of grid		
		l = new LShape(1, 0, g);
		
		// define piece position arrays
		pieceRows = new int[4];
		pieceColumns = new int[4];
		
		// populate Shape row and columns
		for(int i = 0; i < 4; i++) {
			pieceRows[i] = l.getSquareTest(i).getRow();
			pieceColumns[i] = l.getSquareTest(i).getCol();
		}
		
		// rotate
		l.rotate();

		// test if it did not rotate
		assertFalse(l.rotated);
		
		
		// create SquareShape	
		SquareShape s = new SquareShape(4, 4, g);
		
		// define piece position arrays
		pieceRows = new int[4];
		pieceColumns = new int[4];
		
		// populate Shape row and columns
		for(int i = 0; i < 4; i++) {
			pieceRows[i] = l.getSquareTest(i).getRow();
			pieceColumns[i] = l.getSquareTest(i).getCol();
		}
		
		// rotate
		s.rotate();

		// test if it did not rotate
		assertFalse(s.rotated);
		
		
		BarShape b = new BarShape(1, 4, g);
		
		// define piece position arrays
		pieceRows = new int[4];
		pieceColumns = new int[4];
		
		// populate Shape row and columns
		for(int i = 0; i < 4; i++) {
			pieceRows[i] = l.getSquareTest(i).getRow();
			pieceColumns[i] = l.getSquareTest(i).getCol();
		}
		
		// rotate
		b.rotate();

		// test if it rotated
		assertTrue(b.rotated);
		
		
		// add squares to the grid, then make sure an adjacent piece does not rotate
		g.set(0, 3, Color.GREEN);
		g.set(1, 3, Color.GREEN);
		g.set(2, 3, Color.GREEN);
		g.set(3, 3, Color.GREEN);
		
		// create LShape in left col of grid		
		l = new LShape(1, 4, g);
		
		// define piece position arrays
		pieceRows = new int[4];
		pieceColumns = new int[4];
		
		// populate Shape row and columns
		for(int i = 0; i < 4; i++) {
			pieceRows[i] = l.getSquareTest(i).getRow();
			pieceColumns[i] = l.getSquareTest(i).getCol();
		}
		
		// rotate
		l.rotate();

		// test if it did not rotate
		assertFalse(l.rotated);
	}
	
	
	/* 
	 * testCheckRows2()
	 * Creates a grid, populates it with an entire grid
	 * Runs the checkRows command to remove full rows
	 * Tests the resulting grid to check if blocks have dropped as expected
	 */
	@Test
	public void testCheckRows2() {
		// create fresh & new grid
		Grid g = new Grid();

		// a full grid
		for (int r = 0; r < g.HEIGHT; r++) {
			for (int c = 0; c < g.WIDTH; c++) {
				g.set(r, c, Color.MAGENTA);
			}
		}

		// empty the row at index 10
		for (int i = 0; i < g.WIDTH; i++) {
			g.set(10, i, g.EMPTY);
		}
		// except for two squares on that row at indexes 3 and 9
		g.set(10, 3, Color.GREEN);
		g.set(10, 9, Color.GREEN);

		g.checkRows();

		// The grid should become empty except for the two squares
		// on row 10 that are moved to the bottom row.
		for (int r = 0; r < g.HEIGHT; r++) {
			for (int c = 0; c < g.WIDTH; c++) {
				//System.out.println("r = " + r + ", c = " + c + ", isSet = " + g.isSet(r, c));
				if (r == g.HEIGHT - 1 && (c == 3 || c == 9)) {
					assertTrue(g.isSet(r, c));
				} else {
					assertFalse(g.isSet(r, c));
				}
			}
		}
	}
	
	
	/* 
	 * testCheckRows1()
	 * Creates a grid, populates it with full rows and random blocks
	 * Runs the checkRows command to remove full rows
	 * Tests the resulting grid to check if blocks have dropped as expected
	 */
	@Test
	public void testCheckRows1() {

		// create grid object
		Grid g = new Grid();
		
		// fills in rows 19 and 18 (bottom two) of the 20x10 grid
		for (int col = 0; col < Grid.WIDTH; col++) {
			g.set(19, col, Color.RED); // bottom row
			g.set(18, col, Color.RED); // one up from bottom
		}
		
		// a full grid
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < g.WIDTH; c++) {
				g.set(r, c, Color.RED);
			}
		}
		
		
		// add extra square on row 12, column 6
		// arbitrary floating position not found in game, but useful for testing
		g.set(12, 6, Color.GREEN);
		
		// add extra square on row 16, column 3
		g.set(16, 3, Color.BLUE);
		
		// add extra square on row 17, column 3
		g.set(17, 3, Color.GREEN);
		
		// run the row removal method
		g.checkRows();
		
		// test extra square on row 12, column 6
		assertTrue(g.isSet(14, 6));
		
		// test extra square on row 16, column 3	
		assertTrue(g.isSet(18, 3));

		// test extra square on row 17, column 3
		assertTrue(g.isSet(19, 3));
		
		// test for false positives on bottom row
		assertFalse(g.isSet(19, 5));
	}
	

	/* 
	 * testMoveSquare()
	 * Creates two square objects, one true and one false for boolean mobile
	 * Attempt to move both left and right
	 * Check that the non-mobile square does not move, and the mobile one does move as expected
	 */
	@Test
	public void testMoveSquare() {
		
		// create grid object		
		Grid g = new Grid();
		
		// Square constructor format
		// Square(Grid g, int row, int col, Color c, boolean mobile)
		
		// create movable square at row 0, col 2
		Square squareMobile = new Square(g, 0, 2, Color.GREEN, true);
		
		// create non-movable square at row 3, col 5		
		Square squareNotMobile = new Square(g, 3, 5, Color.GREEN, false);
		
		//attempt to move both squares to the left
		squareMobile.move(Direction.LEFT);
		squareNotMobile.move(Direction.LEFT);
		
		// check if the movable square went to col 1
		assertTrue(squareMobile.getRow() == 0 && squareMobile.getCol() == 1);
		
		// check if the non-movable square stayed in place	
		assertTrue(squareNotMobile.getRow() == 3 && squareNotMobile.getCol() == 5);
				
		//attempt to move both squares to the right
		squareMobile.move(Direction.RIGHT);
		squareNotMobile.move(Direction.RIGHT);
		
		// check if the movable square went to col 2
		assertTrue(squareMobile.getRow() == 0 && squareMobile.getCol() == 2);
		
		// check if the non-movable square stayed in place	
		assertTrue(squareNotMobile.getRow() == 3 && squareNotMobile.getCol() == 5);
	}
		
	
	/* 
	 * testMoveLShape()
	 * Create arrays representing the columns and rows of each block of the piece, to record original location
	 * Attempt to move the piece, check to see if it has move properly, and then reset the location values for the next test
	 */
	@Test
	public void testMoveLShape() {
		
		// create grid object			
		Grid g = new Grid();
		
		// LShape constructor format
		// LShape(int r, int c, Grid g)
		
		// instantiate LShape object at row 1 column 4
		LShape l = new LShape(1, 4, g);
		
		// define piece position arrays
		int[] pieceRows = new int[4];
		int[] pieceColumns = new int[4];
		
		// populate Shape row and columns
		for(int i = 0; i < 4; i++) {
			pieceRows[i] = l.getSquareTest(i).getRow();
			pieceColumns[i] = l.getSquareTest(i).getCol();
		}
		
		// simulate a left move
		l.move(Direction.LEFT);

		// test if it moved left
		testShapeLeft(l, pieceRows, pieceColumns);
		
		// update shape row and columns
		for(int i = 0; i < 4; i++) {
			pieceRows[i] = l.getSquareTest(i).getRow();
			pieceColumns[i] = l.getSquareTest(i).getCol();
		}
		
		// simulate a right shape move
		l.move(Direction.RIGHT);
		
		// test if it moved right
		testShapeRight(l, pieceRows, pieceColumns);

		// update shape row and columns
		for(int i = 0; i < 4; i++) {
			pieceRows[i] = l.getSquareTest(i).getRow();
			pieceColumns[i] = l.getSquareTest(i).getCol();
		}
		
		//attempt a move right 4 times
		for(int i = 0; i < 5; i++) {
			l.move(Direction.RIGHT);
		}
		
		// create a new array for list of new columns they should be at
		int[] cols = {8, 8, 8, 9};
		
		// check to see if the shape moved right only 3 times, and is at the end of the board
		testSquares(l, pieceRows, cols);
	}
	
	
	/* 
	 * testDropDown()
	 * 
	 */
	@Test
	public void testDropDown() {
		
		// create grid object	
		Grid g = new Grid();
		
		// LShape constructors
		// LShape(int r, int c, Grid g)
				
		// instantiate LShape object at row 1 column 4
		LShape l = new LShape(1, 4, g);
		
		// define piece position arrays
		int[] pieceRows = new int[4];
		int[] pieceColumns = new int[4];
		
		// populate Shape row and columns
		for(int i = 0; i < 4; i++) {
			pieceRows[i] = l.getSquareTest(i).getRow();
			pieceColumns[i] = l.getSquareTest(i).getCol();
		}
		
		// update shape row and columns
		for(int i = 0; i < 4; i++) {
			pieceRows[i] = l.getSquareTest(i).getRow();
			pieceColumns[i] = l.getSquareTest(i).getCol();
		}
		
		// simulate the affect of the dropdown
		while(l.canMove(Direction.DOWN)) {
			l.move(Direction.DOWN);
		}
		
		// create a new array for list of new columns they should be at
		int[] rows = {17, 18, 19, 19};
		int[] cols = {4, 4, 4, 5};

		// check to see if the shape moved right only 3 times, and is at the end of the board
		testSquares(l, rows, cols);
	}
	
	
	/**
	 * @param l l shape we're testing
	 * @param Row rows it should be at
	 * @param Col columns it should be at
	 */
	public void testSquares(LShape l, int[] Row, int[] Col) {
		for(int i = 0; i < 4; i++) {
			assertTrue(l.getSquareTest(i).getRow() == Row[i]);
			assertTrue(l.getSquareTest(i).getCol() == Col[i]);
		}
	}
	
	public void testShapeLeft(LShape l, int[] Row, int[] Col) {
		for(int i = 0; i < 4; i++) {
			assertTrue(l.getSquareTest(i).getRow() == Row[i]);
			assertTrue(l.getSquareTest(i).getCol() == Col[i] - 1);
		}
	}
	
	public void testShapeRight(LShape l, int[] Row, int[] Col) {
		for(int i = 0; i < 4; i++) {
			assertTrue(l.getSquareTest(i).getRow() == Row[i]);
			assertTrue(l.getSquareTest(i).getCol() == Col[i] + 1);
		}
	}

	
}