package tetrisgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/*
 *  JShape
 */
public class JShape extends AbstractPiece{

	public static int[] rowOffset = {-1,0,1,1};
	public static int[] colOffset = {0,0,0,-1};
	public static Color color = Color.blue;
	
	public JShape(int initialRow, int initialColumn, Grid grid) {
		super(initialRow, initialColumn, grid, color, rowOffset, colOffset);
	}
}