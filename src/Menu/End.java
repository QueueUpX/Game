package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Main.Game;
import Main.Handler;

public class End {
	private Game game;
	private boolean clicked = false;
	private int x, y;
	private Button button;
	private Handler handler;

	public End(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;

		button = new Button("YES, I CHEATED", 240 + 6 * 80, new Font("Arial", Font.PLAIN, 48),
				new Font("Arial", Font.BOLD, 38), Color.white, Color.white);

		this.game.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}
		});

		this.game.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// if (e.getButton() == MouseEvent.BUTTON1) {
				// clicked = true;
				// }
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					clicked = true;
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				clicked = false;

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}

	public void tick() {
		if (button.getBounds().intersects(new Rectangle(x, y, 1, 1))) {
			if (clicked) {
				handler.object.clear();
				handler.object.addAll(Game.map);
				//Player.END = false;
				System.exit(0);
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		// g.drawImage(menuBackground, 0, 0, null);
		Fonts.drawString(g, new Font("Arial", Font.BOLD, 60), Color.orange, "YOU CHEATED? GET OUT", (Game.WIDTH - 500) / 2,
				70);
		button.render(g);
	}
}
