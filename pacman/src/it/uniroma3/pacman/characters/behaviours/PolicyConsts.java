package it.uniroma3.pacman.characters.behaviours;

public interface PolicyConsts {
	/** Circa 20 secondi (22 mosse al secondo) */
	public static final int CHASE_MOVES_LIMIT = 450; 
	/** Circa 7 secondi (22 mosse al secondo) */
	public static final int SCATTER_MOVES_LIMIT = 150; 
	/** Circa 8.5 secondi, quando un fantasma e' <em>frightened</em> esegue circa 8 mosse al secondo (è più lento) */
	public static final int FRIGHTENED_MOVES_LIMIT = 65; 
	public static final int FALSING_FRIGHTENED_MOVES_THRESHOLD = 25;
	
}
