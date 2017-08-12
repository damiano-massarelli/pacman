package it.uniroma3.pacman.staticObjects;


import it.uniroma3.pacman.graphics.CollidableModelEntity;
import it.uniroma3.pacman.graphics.View;
import it.uniroma3.pacman.staticObjectGraphics.DotView;

public class Dot implements CollidableModelEntity {
	
	boolean eaten;
	private DotView dotView;
	
	public Dot(int x, int y) {
		this(new DotView(x, y));
	}
	
	public Dot(DotView dotView) {
		eaten = false;
		this.dotView = dotView;
	}
	
	
	public boolean isEaten() {
		return eaten;
	}

	public void setEaten(boolean eaten) {
		if (eaten)
			getView().setVisible(false);
		else
			getView().setVisible(true);
		this.eaten = eaten;
	}

	@Override
	public View getView() {
		return this.dotView;
	}
}
