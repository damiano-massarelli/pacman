package it.uniroma3.pacman.ui;

import javafx.beans.binding.StringBinding;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CustomText extends Text {
	public CustomText(StringBinding binding) {
		super(binding.get());
		
		textProperty().bind(binding);
		setFont(new Font(20));
		setFill(Color.YELLOW);
		setCache(true);
	}
}
