package it.uniroma3.pacman.staticObjects;

import it.uniroma3.pacman.staticObjectGraphics.MagicDotView;

public class MagicDot extends Dot {

	public MagicDot(int x, int y) {
		super(new MagicDotView(x, y));
	}
	
}
