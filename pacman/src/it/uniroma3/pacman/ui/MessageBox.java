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
	
	
	private Text textMessage;
	
	public MessageBox(String message, int x, int y) {
		super();
		
		Rectangle rectMessage = new Rectangle(x - WIDTH/2, y, WIDTH, HEIGHT);
		rectMessage.setStroke(Color.RED);
		rectMessage.setStrokeWidth(5);
		rectMessage.setFill(Color.CYAN);
		rectMessage.setOpacity(0.75);
		rectMessage.setArcWidth(25);
		rectMessage.setArcHeight(25);
		
		textMessage = new Text(x - WIDTH/2. + 10,
				y + HEIGHT / 2.,
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
