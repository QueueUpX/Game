package Menu;

import java.awt.Color;
import java.awt.Font;
//import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Fonts {
	//private static int X,Y;
	
	public static void drawString(Graphics g, Font f, Color c, String text, int x, int y) {
		Graphics2D g2d=(Graphics2D) g;
		//X=x;
		//Y=y;
		g2d.setColor(c);
		g2d.setFont(f);
		g2d.drawString(text, x, y);
	}
/*
	public static void drawString(Graphics g, Font f, Color c, String text) {
		FontMetrics fm = g.getFontMetrics();
		int x = (Game.WIDTH - fm.stringWidth(text)) / 2; // horizontal center
		int y = ((Game.HEIGHT - fm.getHeight()) / 2) + fm.getAscent(); // vertical center
		drawString(g, f, c, text, x, y);
	}

	public static void drawString(Graphics g, Font f, Color c, String text, double x) {
		FontMetrics fm = g.getFontMetrics();
		int y = ((Game.HEIGHT - fm.getHeight()) / 2) + fm.getAscent(); // vertical center
		drawString(g, f, c, text, (int) x, y);
	}

	public static void drawString(Graphics g, Font f, Color c, String text, int y) {
		FontMetrics fm = g.getFontMetrics();
		int x = (Game.WIDTH - fm.stringWidth(text)) /2; // horizontal center
		drawString(g, f, c, text, x, y);
	}*/
}
