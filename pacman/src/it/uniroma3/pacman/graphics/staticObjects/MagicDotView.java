package it.uniroma3.pacman.graphics.staticObjects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * Dot.fx created on 2008-12-21, 21:59:45 <br>
 * Dot.java created October 2011
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class MagicDotView extends DotView {
	private IntegerProperty radius;

	// variables for magic dot's growing/shrinking animation
	private int animationRadius;
	private int delta;
	private Timeline timeline;

	public MagicDotView(int x, int y) {
		super(x, y);
		
		this.radius = new SimpleIntegerProperty(5);
		
		delta = -1;
		animationRadius = 3;
		
		getCircle().radiusProperty().bind(this.radius);
		
		playTimeline();
	}

	private void createTimeline() {
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(Duration.seconds(0.25), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				doOneTick();
			}
		});

		timeline.getKeyFrames().add(kf);
	}

	public void playTimeline() {
		if (timeline == null) {
			createTimeline();
		}

		timeline.play();
	}

	// do the animation
	public final void doOneTick() {

		if (!isVisible()) {
			return;
		}

		animationRadius += delta;
		int x1 = Math.abs(animationRadius) + 3;

		if (x1 > 5) {
			delta = -delta;
		}

		this.radius.set(x1);
	}

}
