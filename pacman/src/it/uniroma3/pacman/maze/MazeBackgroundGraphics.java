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
	MazeAssets mazeAssets;
	MazeBlockMatrix blockMatrix;
	
	public MazeBackgroundGraphics(MazeAssets mazeAssets) {
		this.mazeAssets = mazeAssets;
		this.blockMatrix = mazeAssets.getBlockMatrix();
	}

	private boolean isCorner(int x, int y) {
		return (!blockMatrix.isBlockAt(x + 1, y + 1) &&
				blockMatrix.isBlockAt(x + 1, y) &&
				blockMatrix.isBlockAt(x, y + 1));
	}

	private boolean shouldDrawWall(int x, int y) {
			return (!isCorner(x, y) && blockMatrix.isBlockAt(x + 1, y)
					&& blockMatrix.isBlockAt(x, y + 1));
	}

	/**
	 * Creates the background using data from SharedMazeData. Make sure to
	 * put data in SharedMazeData before calling this method
	 */
	public void createBackground() {
		int width = MazeConstants.GRID_SIZE;
		int height = MazeConstants.GRID_SIZE;
		for (int x = 0; x < blockMatrix.getWidth(); x++) {
			for (int y = 0; y < blockMatrix.getHeight(); y++) {
				if (blockMatrix.isBlockAt(x, y) && shouldDrawWall(x, y)) {
					Rectangle rect = new Rectangle(x * width , y * height, width, height);
					rect.setFill(Color.CORNFLOWERBLUE);
					getChildren().add(rect);

				}
			}
		}
		for (Dot d : mazeAssets.getDots())
			getChildren().add(d.getSprite());
		for (Teleport t : mazeAssets.getTeleports())
			getChildren().add(t.getSprite());
	}
}