package it.uniroma3.pacman.maze;

import it.uniroma3.pacman.staticObjects.Dot;
import it.uniroma3.pacman.staticObjects.Teleport;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The background of the maze: contains walls and Dots
 * @author damiano
 *
 */
public class MazeBackgroundGraphics extends Group {
	public MazeBackgroundGraphics() {
	}

	private boolean isCorner(int x, int y) {
		return (!SharedMazeData.isBlock(x + 1, y + 1) &&
				SharedMazeData.isBlock(x + 1, y) &&
				SharedMazeData.isBlock(x, y + 1));
	}

	private boolean shouldDrawWall(int x, int y) {
		try {	
			return (!isCorner(x, y) && SharedMazeData.isBlock(x + 1, y)
					&& SharedMazeData.isBlock(x, y + 1));

		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * Creates the background using data from SharedMazeData. Make sure to
	 * put data in SharedMazeData before calling this method
	 */
	public void createBackground() {
		int width = SharedMazeData.GRID_GAP;
		int height = SharedMazeData.GRID_GAP;
		for (int x = 0; x < SharedMazeData.getGridWidth(); x++) {
			for (int y = 0; y < SharedMazeData.getGridHeight(); y++) {
				Dot dot = SharedMazeData.getDot(x, y);
				Teleport teleport = SharedMazeData.getTeleportForPosition(x * SharedMazeData.GRID_GAP, y * SharedMazeData.GRID_GAP);
				if (dot != null)
					getChildren().add(dot.getView());
				if (teleport != null)
					getChildren().add(teleport.getView());
				if (SharedMazeData.isBlock(x, y) && shouldDrawWall(x, y)) {
					Rectangle rect = new Rectangle(x * width , y * height, width, height);
					rect.setFill(Color.CORNFLOWERBLUE);
					getChildren().add(rect);

				}
			}
		}
	}
}