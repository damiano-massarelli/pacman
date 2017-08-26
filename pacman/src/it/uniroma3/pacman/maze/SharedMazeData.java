package it.uniroma3.pacman.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniroma3.pacman.staticObjects.Teleport;
import it.uniroma3.pacman.staticObjects.Dot;
import javafx.geometry.Point2D;

/**
 * SharedMazeData is used for fast and shared access to maze data, maze element types and 
 * informations related to maze dimension and positions.
 * 
 * Maze data can be queried using both grid units and absolute units.
 * Using grid units means that a point (x, y) refers directly to the maze data matrix,
 * so x can range between 0 and GRID_WIDTH - 1 and y can range between 0 and GRID_HEIGHT - 1.
 * 
 * Moving objects usually use absolute units to represent their position on the screen, to
 * access maze data using absolute position use the method with "ForPosition" suffix or read
 * their documentation to find out which units the use.
 */
public final class SharedMazeData {

	public static final int INVALID_POINT_IN_MAZE = -1;
	public static final int EMPTY = 0;
	public static final int TELEPORT = 1;
	public static final int NORMAL_DOT = 2;
	public static final int MAGIC_DOT = 3;
	
	public static final int CAGE_BOUNDARY_LIMIT = 4;
	public static final int BLOCK = 5;
	
	
	public static final int GRID_GAP = 16;

	private static Dot[][] mazeDots;
	private static int[][] mazeData;

	private static Map<Point2D, Teleport> position2teleport = new HashMap<>();

	private static int dotTotal = 0;
	public static int gridWidth = 0;
	public static int gridHeight = 0;
	
	/**
	 * Private constructor to prevent instantiation.
	 */
	private SharedMazeData() { };
	
	/**
	 * Sets the dimension (in grid units) of the maze
	 * @param width 
	 * @param height
	 */
	public static void create(int width, int height) {
		gridWidth = width;
		gridHeight = height;
		mazeDots = new Dot[gridWidth][gridHeight];
		mazeData = new int[gridWidth][gridHeight];
	}

	public static int getGridWidth() {
		return gridWidth;
	}
	
	public static int getGridHeight() {
		return gridHeight;
	}
	
	/**
	 * Converts absolute units to grid units
	 * @param v value in absolute units
	 * @return v converted to grid units
	 */
	private static int abs2Grid(int v) {
		return v/GRID_GAP;
	}
	
	/**
	 * Converts grid units to absolute units for the x axis
	 * @param x coordinate in grid units
	 * @return x coordinate in absolute units
	 */
	public static int xPositionForGridX(int x) {
		return GRID_GAP * x;
	}

	/**
	 * Converts grid units to absolute units for the y axis
	 * @param y coordinate in grid units
	 * @return y coordinate in absolute units
	 */
	public static int yPositionForGridY(int y) {
		return GRID_GAP * y;
	}
	
	/**
	 * Checks if a point (x, y) given in absolute units i valid point in the maze.
	 * To be a valid point both x and y must be a multiple of GRID_GAP
	 * @param x x coordinate in absolute units
	 * @param y y coordinate in absolute units
	 * @return true if (x, y) is a valid point, false otherwise
	 */
	private static boolean isValidPoint(int x, int y) {
		boolean notOutOfBoundsX = x >= 0 && x < mazeData.length * GRID_GAP;
		boolean notOutOfBuondsY = y >= 0 && y < mazeData[0].length * GRID_GAP;
		return (notOutOfBoundsX && notOutOfBuondsY && x  % GRID_GAP == 0 && y % GRID_GAP == 0);
	}

	/**
	 * Gets data at the given point (x, y)
	 * This methods has the same behavior of {@link SharedMazeData#getData} but it uses
	 * absolute units
	 * @param x x coordinate in absolute units
	 * @param y y coordinate in absolute units
	 * @return data at (x, y)
	 */
	public static int getDataForPosition(int x, int y) {
		if (!isValidPoint(x, y))
			return INVALID_POINT_IN_MAZE;
		int mazeX = abs2Grid(x);
		int mazeY = abs2Grid(y);
		return getData(mazeX, mazeY);
	}
	
	/**
	 * Gets data at the given point (x, y)
	 * @param x x coordinate in grid units
	 * @param y y coordinate in grid units
	 * @return data at (x, y)
	 */
	public static int getData(int x, int y) {
		return mazeData[x][y];
	}

	/**
	 * Sets data at (x, y)
	 * @param x x coordinate in grid units
	 * @param y y coordinate in grid units
	 * @param value maze element (e.g. {@link SharedMazeData#BLOCK}) to set at (x, y)
	 */
	public static void setData(int x, int y, int value) {
		if (value == NORMAL_DOT)
			dotTotal++;
		mazeData[x][y] = value;
	}

	/**
	 * @param x x coordinate in absolute units
	 * @param y y coordinate in absolute units
	 * @return dot at (x, y) if present, null otherwise
	 */
	public static Dot getDotForPosition(int x, int y) {
		if (!isValidPoint(x, y))
			return null;
		int mazeX = abs2Grid(x);
		int mazeY = abs2Grid(y);
		return mazeDots[mazeX][mazeY];
	}
	
	public static Dot getDot(int x, int y) {
		return mazeDots[x][y];
	}

	/**
	 * Sets a dot at (x, y)
	 * @param x x coordinate in grid units
	 * @param y y coordinate in grid units
	 * @param dot the dot to add
	 */
	public static void setDot(int x, int y, Dot dot) {
		mazeDots[x][y] = dot;
	}
	
	/**
	 * Changes eaten dots to "uneaten" so that a new level can start
	 */
	public static void resetDots() {
		for (Dot[] row : mazeDots)
			for (Dot dot : row)
				if (dot != null)
					dot.setEaten(false);
	}
	
	public static List<Dot> getDots() {
		List<Dot> dots = new ArrayList<>();
		for (Dot[] row : mazeDots)
			for (Dot dot : row)
				if (dot != null)
					dots.add(dot);
		
		return dots;
	}
	
	/**
	 * Check if a given point in the maze is a block
	 * @param x x position in grid units
	 * @param y y position in grid units
	 * @return true if there is a BLOCK at the given point: (x, y)
	 */
	public static boolean isBlock(int x, int y) {
		return getData(x, y) == BLOCK;
	}
	
	/**
	 * adds a new teleport in the maze
	 * @param teleport the teleport to add
	 */
	public static void setTeleport(it.uniroma3.pacman.staticObjects.Teleport teleport) {
		Point2D teleportPosition = new Point2D(teleport.getSprite().getX(), teleport.getSprite().getY());
		position2teleport.put(teleportPosition, teleport);
	}
	
	/**
	 * Gets (if present) the teleport at point (x, y)
	 * @param x x position in absolute units
	 * @param y y position in absolute units
	 * @return teleport at position (x, y) if present, null otherwise
	 */
	public static Teleport getTeleportForPosition(int x, int y) {
		return position2teleport.get(new Point2D(x, y));
	}

	/** 
	 * @return the number of NORMAL_DOT(s) in the maze
	 */
	public static int getDotTotal() {
		return dotTotal;
	}
}
