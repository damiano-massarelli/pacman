package it.uniroma3.pacman.movingObjects;

import it.uniroma3.pacman.maze.SharedMazeData;

public enum Direction {
	
	WEST (-1, 0) {

		@Override
		public Direction getInverse() {
			return EST;
		}
		
	},
	
	NORTH (0, -1) {

		@Override
		public Direction getInverse() {
			return Direction.SOUTH;
		}
		
	},
	
	EST (1, 0) {

		@Override
		public Direction getInverse() {
			return WEST;
		}
		
	},
	
	SOUTH (0, 1) {

		@Override
		public Direction getInverse() {
			return Direction.NORTH;
		}
		
	};
	
	
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

	public int getDirX() {
		return dirX;
	}


	public int getDirY() {
		return dirY;
	}
	
	public int getDeltaX() {
		return SharedMazeData.GRID_GAP * this.dirX;
	}
	
	public int getDeltaY() {
		return SharedMazeData.GRID_GAP * this.dirY;
	}
	
	public abstract Direction getInverse();
		
}

