package it.uniroma3.pacman.maze;

import java.util.HashMap;
import java.util.Map;

import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

/**
 * This class contains informations about blocks in the maze.
 * Methods for getting information about the presence of a block in a given
 * position are also provided. Blocks can be of two types: <b>blocks</b> which can never
 * be crossed and <b>cage boundary blocks</b> which can be only crossed by ghost whose direction
 * is {@link Direction#UP}.
 * To query for block presence two kind of positions can be used: matrix position and absolute
 * position. Matrix position coordinates are used to "index" the underlying matrix (map) whereas 
 * absolute position coordinates are those use by every object in the game and are referred
 * by to the pixels of the screen. Absolute position coordinates are automatically 
 * converted in matrix coordinates dividing them by the size of blocks.<br /><br />
 * Methods taking {@link Point2D} typed parameters use absolute position while methods taking
 * a couple of integer coordinates use matrix position.
 * @author damiano
 *
 */
public class MazeBlockMatrix {
	private static final Integer BLOCK = 1;
	private static final Integer CAGE_BOUNDARY_BLOCK = 2;
	
	/** a matrix whose dimensions don't have do be specified before having read
	* the whole maze file. <b>Note: Point2D here is used for matrix position</b>
	*/
	private Map<Point2D, Integer> blockMatrix;
	private int width;
	private int height;
	
	public MazeBlockMatrix() {
		blockMatrix = new HashMap<>();
		width = 0;
		height = 0;
	}
	
	/**
	 * return true if pos is a valid position that is pos.x and pos.y can be converted
	 * in matrix position.
	 * @param pos
	 * @return
	 */
	private boolean check(Point2D pos) {
		int x = (int)pos.getX();
		int y = (int)pos.getY();
		boolean validPosition = (x % SharedMazeData.GRID_GAP == 0 && y % SharedMazeData.GRID_GAP == 0);
		return validPosition && getAt(pos) != null;
	}
	
	/** matrix position */
	private Integer getAt(int x, int y) {
		return blockMatrix.get(new Point2D(x, y));
	}
	
	private void setAt(int x, int y, int type) {
		blockMatrix.put(new Point2D(x, y), type);
		width = x > width ? x : width;
		height = y > height ? y : height;
	}
	
	/** absolute position */
	private Integer getAt(Point2D position) {
		int x = (int)position.getX() / SharedMazeData.GRID_GAP;
		int y = (int)position.getY() / SharedMazeData.GRID_GAP;
		return getAt(x, y);
	}
	
	public void setBlockAt(int x, int y) {
		setAt(x, y, BLOCK);
	}
	
	public void setCageBoundaryBlockAt(int x, int y) {
		setAt(x, y, CAGE_BOUNDARY_BLOCK);
	}
	
	/**
	 * Return true if there is a block or a cage boundary block at position
	 * <em>position</em>
	 */
	public boolean isGenericBlockAtPosition(Point2D position) {
		if (!check(position)) return false;
		return getAt(position) != 0;
	}
	
	public boolean isBlockAt(int x, int y) {
		return getAt(x, y) == BLOCK;
	}
	
	public boolean isBlockAtPosition(Point2D position) {
		if (!check(position)) return false;
		return getAt(position) == BLOCK;
	}
	
	public boolean isCageBoundaryBlockAtPosition(Point2D position) {
		if (!check(position)) return false;
		return getAt(position) == CAGE_BOUNDARY_BLOCK;
	}
	
	public boolean isCageBoundaryAt(int x, int y) {
		return getAt(x, y) == CAGE_BOUNDARY_BLOCK;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}
