package it.uniroma3.pacman.characterGraphics;

import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.game.PacmanGame;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;

/**
 * The DyingPacman animation. When PacMan dies this animation is added to the 
 * gameField. When this animation is over if pacman has 0 lives then a new game is started
 * else a new life is started.
 * 
 * @see PacMan#die(PacmanGame)
 * @see PacmanGame#gameOver()
 * @see PacmanGame#startNewLevel()
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class DyingPacManView extends Arc {

	private Timeline timeline;

	public DyingPacManView(final PacmanGame game) {
		setCenterX(0);
		setCenterY(0);
		setRadiusX(13);
		setRadiusY(13);
		setStartAngle(90);
		setLength(360);
		setType(ArcType.ROUND);
		setFill(Color.YELLOW);
		game.getGameField().getChildren().add(this);
		createTimeline(game);
	}

	private void createTimeline(PacmanGame maze) {
		timeline = new Timeline();
		timeline.setCycleCount(1);

		KeyFrame kf1 = new KeyFrame(Duration.millis(600), 
				new KeyValue(startAngleProperty(), 90),
				new KeyValue(lengthProperty(), 360)
				);

		KeyFrame kf2 = new KeyFrame(Duration.millis(1800), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				maze.getGameField().getChildren().remove(DyingPacManView.this);
				if (maze.pacMan.getLives() != 0)
					maze.startNewLife();
				else
					maze.gameOver();
			}

		},
				new KeyValue(startAngleProperty(), 270),
				new KeyValue(lengthProperty(), 0)
				);



		timeline.getKeyFrames().addAll(kf1, kf2);
	}

	public void pause() {
		timeline.pause();
	}

	public void startAnimation(int x, int y) {

		setStartAngle(90);
		setCenterX(x);
		setCenterY(y);

		timeline.playFromStart();
	}

}
