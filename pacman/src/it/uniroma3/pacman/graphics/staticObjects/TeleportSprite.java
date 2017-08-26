package it.uniroma3.pacman.graphics.staticObjects;

import it.uniroma3.pacman.graphics.Sprite;
import it.uniroma3.pacman.maze.SharedMazeData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TeleportSprite extends Sprite {
	
	Rectangle rect;

	public TeleportSprite(int x, int y) {
		super(x, y);
		rect = new Rectangle(SharedMazeData.GRID_GAP * 2, SharedMazeData.GRID_GAP * 2, Color.RED);
		rect.xProperty().bind(getxProperty().subtract(rect.widthProperty().divide(2)));
		rect.yProperty().bind(getyProperty().subtract(rect.heightProperty().divide(2)));
		getChildren().add(rect);
	}
	

}
