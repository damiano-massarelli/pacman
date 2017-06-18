package it.uniroma3.pacman.ui;

import it.uniroma3.pacman.maze.SharedMazeData;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MessageBox extends Parent {
	
	private static final double WIDTH = 300;
	private static final double HEIGHT = 85;
	private static final double X = (SharedMazeData.getGridWidth() * SharedMazeData.GRID_GAP)/2. - WIDTH/2.;
	private static final double Y = (SharedMazeData.getGridHeight() * SharedMazeData.GRID_GAP)/2. - HEIGHT/2.;
	
	private Text textMessage;
	
	public MessageBox(String message) {
		super();
		
		Rectangle rectMessage = new Rectangle(X, Y, WIDTH, HEIGHT);
		rectMessage.setStroke(Color.RED);
		rectMessage.setStrokeWidth(5);
		rectMessage.setFill(Color.CYAN);
		rectMessage.setOpacity(0.75);
		rectMessage.setArcWidth(25);
		rectMessage.setArcHeight(25);
		
		textMessage = new Text(X + 10,
				Y + HEIGHT / 2.,
				message);
		textMessage.setFont(new Font(18));
		textMessage.setFill(Color.RED);
		
		
		getChildren().add(rectMessage);
		getChildren().add(textMessage);
	}
	
	public void setText(String message) {
		textMessage.setText(message);
	}
}
