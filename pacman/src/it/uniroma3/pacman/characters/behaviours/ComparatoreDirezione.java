package it.uniroma3.pacman.characters.behaviours;

import java.util.Comparator;

import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

/**
 * External comparator for Directions. Every Direction (if used) will move the object of {@link Direction#getDeltaX()} on the x axis and {@link Direction#getDeltaY()} on the
 * y axis. This Comparator assigns every direction a value that is calculated as the distance
 * from startPos plus the movement implied by that direction to targetPos. Hence directions
 * that bring the object closer to the target will be considered to be minor.
 * @author damiano
 *
 */
public class ComparatoreDirezione implements Comparator<Direction> {	
	Point2D startPos;
	Point2D targetPos;
	
	public ComparatoreDirezione(Point2D startPos, Point2D targetPos) {
		this.startPos = startPos;
		this.targetPos = targetPos;
	}


	protected int valutaDirezione(Direction dir) {
		Point2D nextPos = startPos.add(dir.getDeltaX(), dir.getDeltaY());	
		
		return (int) nextPos.distance(targetPos);
		
	}

	@Override
	public int compare(Direction dir1, Direction dir2) {
		return valutaDirezione(dir1) - valutaDirezione(dir2);
	}


	public Point2D getStartPos() {
		return startPos;
	}


	public void setStartPos(Point2D startPos) {
		this.startPos = startPos;
	}


	public Point2D getTargetPos() {
		return targetPos;
	}


	public void setTargetPos(Point2D targetPos) {
		this.targetPos = targetPos;
	}
}
