package it.uniroma3.pacman.characters.behaviours;

import it.uniroma3.pacman.maze.SharedMazeData;
import javafx.geometry.Point2D;

public interface GhostConsts {
	public static final Point2D TOP_LEFT_CORNER = new Point2D(0, 0);
	
	public static final Point2D BOTTOM_LEFT_CORNER = new Point2D(0,
			SharedMazeData.getGridWidth() * SharedMazeData.GRID_GAP);

	public static final Point2D TOP_RIGHT_CORNER = new Point2D(SharedMazeData.getGridWidth() * SharedMazeData.GRID_GAP, 0);
	
	public static final Point2D BOTTOM_RIGHT_CORNER = new Point2D(SharedMazeData.getGridWidth() * SharedMazeData.GRID_GAP,
			SharedMazeData.getGridHeight() * SharedMazeData.GRID_GAP);

	
	
	public static final int CHASE_MOVES_LIMIT = 450; // Circa 20 secondi (22 mosse al secondo)
	public static final int SCATTER_MOVES_LIMIT = 150; // Circa 7 secondi
	public static final int FRIGHTENED_MOVES_LIMIT = 65;
	public static final int FALSING_FRIGHTENED_MOVES_LIMIT = FRIGHTENED_MOVES_LIMIT - 25;
	
}
