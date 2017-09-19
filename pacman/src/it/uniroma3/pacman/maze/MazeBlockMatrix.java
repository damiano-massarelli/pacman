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
	/** a matrix whose dimensions don't have do be specified before having read
	* the whole maze file. <b>Note: Point2D here is used for matrix position</b>
	*/
	private Map<Point2D, BlockType> blockMatrix;
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
		double x = pos.getX();
		double y = pos.getY();
		boolean validPosition = (x % MazeConstants.GRID_SIZE == 0 && y % MazeConstants.GRID_SIZE == 0);
		return validPosition && getAtPosition(pos) != null;
	}
	
	/** matrix position */
	private BlockType getAt(int x, int y) {
		BlockType blockType = blockMatrix.get(new Point2D(x, y));
		if (blockType == null)
			blockType = BlockType.EMPTY;
		return blockType;
	}
	
	private void setAt(int x, int y, BlockType type) {
		blockMatrix.put(new Point2D(x, y), type);
		width = x > width ? x : width;
		height = y > height ? y : height;
	}
	
	/** absolute position */
	private BlockType getAtPosition(Point2D position) {
		int x = (int)position.getX() / MazeConstants.GRID_SIZE;
		int y = (int)position.getY() / MazeConstants.GRID_SIZE;
		return getAt(x, y);
	}
	
	public void setBlockAt(int x, int y) {
		setAt(x, y, BlockType.BLOCK);
	}
	
	public void setCageBoundaryBlockAt(int x, int y) {
		setAt(x, y, BlockType.CAGE_BOUNDARY);
	}
	
	/**
	 * Returns the block type at the given <em>position</em>
	 * @param position
	 * @return {@link BlockType#BLOCK} if a block is placed in position<br> {@link BlockType#CAGE_BOUNDARY}
	 * if a cage boundary is placed in position<br> {@link BlockType#IVALID} if the coordinates of position are not a multiple of
	 * {@link MazeConstants#GRID_SIZE}
	 */
	public BlockType getBlockTypeAtPosition(Point2D position) {
		if (!check(position)) return BlockType.IVALID;
		else return getAtPosition(position);
	}
	
	/**
	 * returns true if a block is placed in (x, y).
	 * @param x
	 * @param y
	 * @return true of a block is placed in (x, y)
	 */
	public boolean isBlockAt(int x, int y) {
		return getAt(x, y) == BlockType.BLOCK;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}
