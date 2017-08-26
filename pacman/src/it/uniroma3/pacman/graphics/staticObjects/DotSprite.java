package it.uniroma3.pacman.graphics.staticObjects;

import it.uniroma3.pacman.graphics.Sprite;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 */
public class DotSprite extends Sprite {
	private static final int RADIUS = 1;
	
	Circle circle;
	
	public DotSprite(int x, int y) {
		super(x, y);
		circle = new Circle(x, y, RADIUS, Color.YELLOW);
		circle.centerXProperty().bind(getxProperty());
		circle.centerYProperty().bind(getyProperty());
		getChildren().add(circle);
	}
	
	protected Circle getCircle() {
		return circle;
	}
	
}
