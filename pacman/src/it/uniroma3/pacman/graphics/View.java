package it.uniroma3.pacman.graphics;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

/**
 * This class is used to create visual objects based on an image in fact it extends {@link ImageView}
 * adding some useful methods. Keep in mind that X and Y refer to the top-left corner but {@link View#getCenterPosition()} can
 * be used to get the position of the center.
 * @author damianomassarelli
 *
 */
public class View extends ImageView {
	DoubleProperty x, y;

	public View(double x, double y) {
		super();
		setX(x);
		setY(y);
	}
	
	public Point2D getPosition() {
		return new Point2D(getX(), getY());
	}
	
	public Point2D getCenterPosition() {
		double cx = getX() + getImage().getWidth()/2;
		double cy = getY() + getImage().getHeight()/2;
		return new Point2D(cx, cy);
	}
	
}
