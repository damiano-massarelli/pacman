package it.uniroma3.pacman.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Text object for showing score for eating a ghost; disappears after 2 seconds.
 *
 * ScoreText.fx created on 2009-2-6, 17:52:42 <br>
 * ScoreText.java created October 2011
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class ScoreText extends Text {

	private static final Font SCORE_FONT = new Font(11);

	private static final Color SCORE_FILL = Color.YELLOW;
	private static final int DISPLAY_TIME = 2;

	private Timeline timeline;
	private Pane gameFiled;

	public ScoreText(String s, Pane gameFiled, int x, int y) { //patweb
		super(s);
		setFont(SCORE_FONT);
		setFill(SCORE_FILL);
		createTimeline();
		setX(x);
		setY(y);
		this.gameFiled = gameFiled;
	}

	private void createTimeline() {
		timeline = new Timeline();
		timeline.setCycleCount(1);
		KeyFrame kf = new KeyFrame(Duration.seconds(DISPLAY_TIME), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {	
				gameFiled.getChildren().remove(ScoreText.this);
			}
		});
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}
}
