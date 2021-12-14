package tetrisgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/*
 *  LShape
 */
public class LShape extends AbstractPiece{

	public static int[] rowOffset= {-1,0,1,1};
	public static int[] colOffset= {0,0,0,1};
	public static Color color = Color.magenta;
	
	public LShape(int initialRow, int initialColumn, Grid grid) {
		super(initialRow, initialColumn, grid, color, rowOffset, colOffset);
	}
}
