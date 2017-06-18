package it.uniroma3.pacman.staticObjects;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Dot.fx created on 2008-12-21, 21:59:45 <br>
 * Dot.java created October 2011
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class Dot extends Parent {
	private static final int RADIUS = 1;
	
	private boolean eaten = false;
	
	Circle circle;
	
	public Dot(int x, int y) {
		circle = new Circle(x, y, RADIUS, Color.YELLOW);
		getChildren().add(circle);
	}
	
	public boolean isEaten() {
		return eaten;
	}

	public void setEaten(boolean eaten) {
		if (eaten)
			setVisible(false);
		else
			setVisible(true);
		this.eaten = eaten;
	}


	protected Circle getCircle() {
		return circle;
	}
	
}
