package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.Rectangle;

import Main.Game;

@SuppressWarnings("serial")
public class Button extends Rectangle {
	private Font font,selectedFont;
	private Color color,selectedColor;
	private boolean selected;
	private String text;
	private int textY;
	
	public Button(String text, int textY, Font font, Font selectedFont, Color color, Color selectedColor) {
		this.text = text;
		this.textY = textY;
		this.font = font;
		this.selectedFont = selectedFont;
		this.color = color;
		this.selectedColor = selectedColor;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
	public void render(Graphics g) {
		//Graphics2D g2d=(Graphics2D) g;
		if(selected) {
			Fonts.drawString(g, selectedFont, selectedColor, text,(Game.WIDTH-500)/2,textY+selectedFont.getSize());
		}
		else {
			Fonts.drawString(g, font, color, text,(Game.WIDTH-500)/2,textY+font.getSize());
		}
		this.x=(Game.WIDTH-500)/2;
		this.y=textY;
		this.width=g.getFontMetrics().stringWidth(text);
		this.height=g.getFontMetrics().getHeight();
		//g2d.drawRect(x, y, width, height);
	}
	/*
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,width,height);
	}*/
}
