package it.uniroma3.pacman.graphics.characters;

import it.uniroma3.pacman.game.PacmanGame;
import it.uniroma3.pacman.graphics.View;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;

/**
 * The DyingPacman animation. When PacMan dies this animation is added to the 
 * gameField. When this animation is over the animationEndListener is notified (if it is set).
 * The animationEndListener could be {@link PacmanGame} since it has all the information needed
 * to decide if the game is over or a new life has to be started.
 * <br />
 * This Sprite doesn't have a corresponding domain class
 */
public class DyingPacManSprite extends View {

	private static final int RADIUS = 13;
	
	private Timeline timeline;
	
	private Arc dyingPacMan;
	
	private OnAnimationEndListener animationEndListener;
	
	private Pane gameField; // So that DyingPacManSprite can remove itself

	public DyingPacManSprite(Point2D pos, Pane gameField) {
		super((int)pos.getX(), (int)pos.getY(), RADIUS);
		this.gameField = gameField;
		
		dyingPacMan = new Arc();
		dyingPacMan.centerXProperty().bind(super.getxProperty());
		dyingPacMan.centerYProperty().bind(super.getyProperty());
		dyingPacMan.setRadiusX(getCollisionRadius());
		dyingPacMan.setRadiusY(getCollisionRadius());
		dyingPacMan.setStartAngle(90);
		dyingPacMan.setLength(360);
		dyingPacMan.setType(ArcType.ROUND);
		dyingPacMan.setFill(Color.YELLOW);
		getChildren().add(dyingPacMan);
		playAnimation();
	}

	private void playAnimation() {
		timeline = new Timeline();
		timeline.setCycleCount(1);

		KeyFrame kf1 = new KeyFrame(Duration.millis(600), 
				new KeyValue(dyingPacMan.startAngleProperty(), 90),
				new KeyValue(dyingPacMan.lengthProperty(), 360)
				);

		KeyFrame kf2 = new KeyFrame(Duration.millis(1800), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				gameField.getChildren().remove(DyingPacManSprite.this);
				if (animationEndListener != null)
					animationEndListener.onAnimationEnd(); // notify
			}

		},
				new KeyValue(dyingPacMan.startAngleProperty(), 270),
				new KeyValue(dyingPacMan.lengthProperty(), 0)
				);



		timeline.getKeyFrames().addAll(kf1, kf2);
		timeline.playFromStart();
	}
	
	/* The animationEndListener should be PacManGame */
	public void setOnAnimationEndListener(OnAnimationEndListener animationEndListener) {
		this.animationEndListener = animationEndListener;
	}

}
