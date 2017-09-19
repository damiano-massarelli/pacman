package it.uniroma3.pacman.movingObjects;

import it.uniroma3.pacman.maze.MazeConstants;

public enum Direction {
	
	LEFT (-1, 0) {

		@Override
		public Direction getInverse() {
			return RIGHT;
		}
		
	},
	
	UP (0, -1) {

		@Override
		public Direction getInverse() {
			return Direction.DOWN;
		}
		
	},
	
	RIGHT (1, 0) {

		@Override
		public Direction getInverse() {
			return LEFT;
		}
		
	},
	
	DOWN (0, 1) {

		@Override
		public Direction getInverse() {
			return Direction.UP;
		}
		
	};
	
	/** 
	 * Returns the direction having its x component equal to x and its y component equal to y
	 * @param x x component of the direction (must be and integer ranging between -1 and 1)
	 * @param y y component of the direction (must be and integer ranging between -1 and 1)
	 * @return a direction having x and y as components, null if there isn't a direction whose components are
	 * (x, y)
	 */
	public static Direction fromXY(int x, int y) {
		for (Direction dir : values())
			if (dir.getDirX() == x && dir.getDirY() == y)
				return dir;
		return null;
	}
	
	private final int dirX;
	private final int dirY;
	
	private Direction(int dirX, int dirY) {
		this.dirX = dirX;
		this.dirY = dirY;
	}

	/**
	 * 
	 * @return x component of this direction
	 */
	public int getDirX() {
		return dirX;
	}

	/**
	 * 
	 * @return y component of this direction
	 */
	public int getDirY() {
		return dirY;
	}
	
	/** 
	 * @return the distance that would be trodden in the x axis if this direction were used
	 */
	public int getDeltaX() {
		return MazeConstants.GRID_SIZE * this.dirX;
	}
	
	/** 
	 * @return the distance that would be trodden in the y axis if this direction were used
	 */
	public int getDeltaY() {
		return MazeConstants.GRID_SIZE * this.dirY;
	}
	
	public abstract Direction getInverse();
		
}


