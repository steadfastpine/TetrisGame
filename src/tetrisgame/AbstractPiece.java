package tetrisgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public abstract class AbstractPiece implements Piece{

	private boolean ableToMove;							// can this piece move
	private Square[] square;								// the squares that make up this piece
	private Grid grid;
	private 	int inR;															// added by Scott Forsberg - 2019-10-02
	private 	int inC;
	private static final int PIECE_COUNT = 4;		// number of squares in one Tetris game piece
	public boolean rotated=false;
	
	/**
	 * Creates pieces of varios shapes
	 * 
	 * @param r
	 *            row location for this piece
	 * @param c
	 *            column location for this piece
	 * @param g
	 *            the grid for this game piece
	 * 
	 */
	public AbstractPiece(int initialRow, int initialColumn, Grid gridin, Color color, int[] rowOffset, int[] colOffset) {
		
		// added by Scott Forsberg - 2019-10-02
		inR = initialRow;
		inC = initialColumn;		
		
		grid = gridin;
		square = new Square[PIECE_COUNT];
		ableToMove = true;
		
		// Create the squares - Square(Grid g, int row, int col, Color c, boolean mobile)
		square[0] = new Square(gridin, initialRow+rowOffset[0], initialColumn+colOffset[0], color, true);
		square[1] = new Square(gridin, initialRow+rowOffset[1], initialColumn+colOffset[1], color, true);
		square[2] = new Square(gridin, initialRow+rowOffset[2], initialColumn+colOffset[2], color, true);
		square[3] = new Square(gridin, initialRow+rowOffset[3], initialColumn+colOffset[3], color, true);
	}
	
	
	// group project addition - 2019-10-04
	public Square getSquareTest(int i) {
		return square[i];
	}
		
	
	/**
	 * Draws the piece on the given Graphics context
	 */
	public void draw(Graphics g) {
		for (int i = 0; i < PIECE_COUNT; i++) {
			square[i].draw(g);
		}
	}
	
	
	/**
	 * Returns an array of the rotated x,y coordinates
	 */
	public int[][] getRotatedCoords() {
		
		int originX, originY;
		int squareX,squareY;	
		int squareCartX,squareCartY;	
		int squareCartRotatedX,squareCartRotatedY;	
		int rotatedOffsetX, rotatedOffsetY;
		
		// get the col and row of the origin (piece 1)
		originX=square[1].getCol();
		originY=square[1].getRow();	
		
		int[][] rotatedCoords;		// square 0-3 | cells 0:x - 1:y
		rotatedCoords=new int[4][2];
		
		// check if rotatable
		for (int i = 0; i < PIECE_COUNT; i++) {
			
			squareX=square[i].getCol();
			squareY=square[i].getRow();		

			//verbose
			//System.out.println("\nsquare "+i+" = c"+square0X+" r"+square0Y);	
			
			// get the Cartesian coordinates of the given square by subtracting the x,y offset of the origin square
			squareCartX=squareX-originX;
			squareCartY=squareY-originY;		
	
			//verbose
			//System.out.println("\nsquare "+i+" cartesian = "+square0CartX+", "+square0CartY);
			
			// achieve rotation by setting y=-x, and x=y
			rotatedOffsetY= squareCartX*(-1);	
			rotatedOffsetX= squareCartY;

			//verbose
			//System.out.println("\nsquare "+i+" cartesian rotated = "+rotatedOffsetX+", "+rotatedOffsetY);
			
			// subtract rotated x,y from origin col,row to get the rotated  col row of the given square
			squareCartRotatedX=originX-rotatedOffsetX;
			squareCartRotatedY=originY-rotatedOffsetY;	
			
			// update the rotated coordinate array
			rotatedCoords[i][0]=squareCartRotatedX;
			rotatedCoords[i][1]=squareCartRotatedY;
		}
		
		return rotatedCoords;
	}	
	
	
	/**
	 * Rotate function
	 */
	public void rotate() {
		int[][] coords = getRotatedCoords();
		
		if (canRotate(coords)) {
			for (int i = 0; i < PIECE_COUNT; i++) {
				square[i].rotate(coords[i][0], coords[i][1]);
			}
			rotated = true;
		}
	}
	
	
	/**
	 * Moves the piece if possible Freeze the piece if it cannot move down
	 * anymore
	 * 
	 * @param direction
	 *            the direction to move
	 */
	public void move(Direction direction) {
		if (canMove(direction)) {
			for (int i = 0; i < PIECE_COUNT; i++)
				square[i].move(direction);
		}
		// if we couldn't move, see if because we're at the bottom
		else if (direction == Direction.DOWN) {
			ableToMove = false;
		}
	}

	/**
	 * Returns the (row,col) grid coordinates occupied by this Piece
	 * 
	 * @return an Array of (row,col) Points
	 */
	public Point[] getLocations() {
		Point[] points = new Point[PIECE_COUNT];
		for (int i = 0; i < PIECE_COUNT; i++) {
			points[i] = new Point(square[i].getRow(), square[i].getCol());
		}
		return points;
	}

	/**
	 * Return the color of this piece
	 */
	public Color getColor() {
		// all squares of this piece have the same color
		return square[0].getColor();
	}

	
	/**
	 * Returns if this piece can move in the given direction
	 */
	public boolean canMove(Direction direction) {
		if (!ableToMove) {
			return false;
		}
		// Each square must be able to move in that direction
		boolean answer = true;
		for (int i = 0; i < PIECE_COUNT; i++) {
			answer = answer && square[i].canMove(direction);
		}

		return answer;
	}

	
	/**
	 * Returns if this piece can rotate to the given coords
	 * array 0-4 | array 0:x, 1:y
	 */
	public boolean canRotate(int[][] coords) {
		
		if (!ableToMove) {
			return false;
		}
		// Each square must be able to move in that direction
		boolean answer = true;
		for (int i = 0; i < PIECE_COUNT; i++) {
			answer = answer && square[i].canRotate(coords[i][0], coords[i][1]);
		}

		return answer;
	}

	
	
}
