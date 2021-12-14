package tetrisgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/*
 *  SquareShape
 */
public class SquareShape extends AbstractPiece{

	// prevent square shape from rotating
	@Override
	public void rotate() {	
	}
	
	public static int[] rowOffset = {0,0,1,1};
	public static int[] colOffset = {-1,0,-1,0};
	public static Color color = Color.gray;
	
	public SquareShape(int initialRow, int initialColumn, Grid grid) {
		super(initialRow, initialColumn, grid, color, rowOffset, colOffset);
	}
}