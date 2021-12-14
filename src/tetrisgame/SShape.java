package tetrisgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/*
 *  SShape
 */
public class SShape extends AbstractPiece{

	public static int[] rowOffset = {0,0,1,1};
	public static int[] colOffset = {1,0,0,-1};
	public static Color color = Color.green;
	
	public SShape(int initialRow, int initialColumn, Grid grid) {
		super(initialRow, initialColumn, grid, color, rowOffset, colOffset);
	}
}