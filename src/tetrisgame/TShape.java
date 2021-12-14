package tetrisgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/*
 *  TShape
 */
public class TShape extends AbstractPiece{

	public static int[] rowOffset = {0,0,0,1};
	public static int[] colOffset = {-1,0,1,0};
	public static Color color = Color.yellow;
	
	public TShape(int initialRow, int initialColumn, Grid grid) {
		super(initialRow, initialColumn, grid, color, rowOffset, colOffset);
	}
}