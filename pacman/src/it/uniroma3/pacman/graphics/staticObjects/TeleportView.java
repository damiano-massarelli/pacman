package it.uniroma3.pacman.graphics.staticObjects;

import it.uniroma3.pacman.graphics.View;
import it.uniroma3.pacman.maze.SharedMazeData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TeleportView extends View {
	
	Rectangle rect;

	public TeleportView(int x, int y) {
		super(x, y);
		rect = new Rectangle(SharedMazeData.GRID_GAP * 2, SharedMazeData.GRID_GAP * 2, Color.RED);
		rect.xProperty().bind(getxProperty().subtract(rect.widthProperty().divide(2)));
		rect.yProperty().bind(getyProperty().subtract(rect.heightProperty().divide(2)));
		getChildren().add(rect);
	}
	

}
