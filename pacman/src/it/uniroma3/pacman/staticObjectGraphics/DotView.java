package it.uniroma3.pacman.staticObjectGraphics;

import it.uniroma3.pacman.graphics.View;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 */
public class DotView extends View {
	private static final int RADIUS = 1;
	
	Circle circle;
	
	public DotView(int x, int y) {
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
