package it.uniroma3.pacman.maze;

import it.uniroma3.pacman.movingObjects.Direction;

/**
 * This enum contains all the kinds of blocks that a maze can have.
 * 
 * @author damiano
 *
 */
public enum BlockType {
	/** A wall */
	BLOCK,
	/** A wall that can be crossed only by ghost whose direction is {@link Direction#UP} */
	CAGE_BOUNDARY,
	/** Used when the position used to query {@link MazeBlockMatrix} is not divisible by  {@link MazeConstants#GRID_GAP}*/
	IVALID,
	/** An empty slot, no walls */
	EMPTY;
}
