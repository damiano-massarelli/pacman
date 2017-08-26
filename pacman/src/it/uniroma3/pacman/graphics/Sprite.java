package it.uniroma3.pacman.graphics;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.Parent;

public class Sprite extends Parent {
	private IntegerProperty xProperty, yProperty;
	private int collisionRadius;

	public Sprite(int x, int y) {
		this(x, y, 10);
	}
	
	public Sprite(int x, int y, int collisionRadius) {
		super();
		this.collisionRadius = collisionRadius;
		this.xProperty = new SimpleIntegerProperty(x);
		this.yProperty = new SimpleIntegerProperty(y);
	}

	public IntegerProperty getxProperty() {
		return xProperty;
	}

	public void setxProperty(IntegerProperty xProperty) {
		this.xProperty = xProperty;
	}

	public IntegerProperty getyProperty() {
		return yProperty;
	}

	public void setyProperty(IntegerProperty yProperty) {
		this.yProperty = yProperty;
	}

	public int getX() {
		return xProperty.get();
	}

	public void setX(int x) {
		this.xProperty.set(x);;
	}

	public int getY() {
		return yProperty.get();
	}

	public void setY(int y) {
		this.yProperty.set(y);
	}
	
	public Point2D getPosition() {
		return new Point2D(getX(), getY());
	}
	
	public void setPosition(Point2D position) {
		setX((int)position.getX());
		setY((int)position.getY());
	}
	
	public int getCollisionRadius() {
		return this.collisionRadius;
	}
	
	public void setCollisionRadius(int radius) {
		collisionRadius = radius;
	}
	
}
