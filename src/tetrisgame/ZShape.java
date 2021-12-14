package tetrisgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/*
 *  ZShape
 */
public class ZShape extends AbstractPiece{

	public static int[] rowOffset = {0,0,1,1};
	public static int[] colOffset = {-1,0,0,1};
	public static Color color = Color.red;
	
	public ZShape(int initialRow, int initialColumn, Grid grid) {
		super(initialRow, initialColumn, grid, color, rowOffset, colOffset);
	}
}
