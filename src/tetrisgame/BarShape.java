package tetrisgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/*
 *  BarShape
 */
public class BarShape extends AbstractPiece{

	public static int[] rowOffset = {0,0,0,0};
	public static int[] colOffset = {-1,0,1,2};
	public static Color color = Color.CYAN;
	
	public BarShape(int initialRow, int initialColumn, Grid grid) {
		super(initialRow, initialColumn, grid, color, rowOffset, colOffset);
	}
}