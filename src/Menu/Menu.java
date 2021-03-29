package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import Audio.MusicPlayer;
import Framework.State;
import Main.BufferedImageLoader;
import Main.Game;

public class Menu {
	private final Button[] options; // selection 0,1,2
	private int x, y;
	private boolean clicked = false;

	private BufferedImage menuBackground = null;

	private int currentSelection;

	private Game game;

	public Menu(Game game) {
		BufferedImageLoader loader = new BufferedImageLoader();
		menuBackground = loader.loadImage("/MenuBackground.png");
		options = new Button[3];
		options[0] = new Button("Play", 200 + 0 * 80, new Font("Arial", Font.PLAIN, 32),
				new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow);
		options[1] = new Button("Options", 200 + 1 * 80, new Font("Arial", Font.PLAIN, 32),
				new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow);
		options[2] = new Button("Exit", 200 + 2 * 80, new Font("Arial", Font.PLAIN, 32),
				new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow);
		this.game = game;
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
				if (e.getButton() == MouseEvent.BUTTON1) {
					clicked = true;
				}
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
		for (int i = 0; i < options.length; i++) {
			if (options[i].getBounds().intersects(new Rectangle(x, y, 1, 1))) {
				currentSelection = i;
				if (clicked) {
					select();
				}
			}
		}
		clicked = false;
	}

	public void render(Graphics g) {
		// g.setColor(Color.black);
		// g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.drawImage(menuBackground, 0, 0, null);
		Fonts.drawString(g, new Font("Arial", Font.BOLD, 60), Color.orange, "MilkyWayExplorer", (Game.WIDTH - 500) / 2,
				70);

		for (int i = 0; i < options.length; i++) {
			if (i == currentSelection) {
				options[i].setSelected(true);
			} else {
				options[i].setSelected(false);
			}
			options[i].render(g);
		}
	}

	private void select() {
		switch (currentSelection) {
		case 0:
			// System.out.println("Play");
			Game.gameState = State.Game;
			if (Options.music < 0) {
				Game.background.stop();
				Game.background = new MusicPlayer("InGameMusic");
				Game.background.start();
			}
			break;
		case 1:
			Game.gameState = State.Options;
			break;
		case 2:
			// System.out.println("Exit");
			// game.stop();
			System.exit(0);
			break;
		}
	}
}
