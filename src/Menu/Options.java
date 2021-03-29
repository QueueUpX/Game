package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Audio.MusicPlayer;
import Framework.State;
import Main.BufferedImageLoader;
import Main.Game;

public class Options {
	protected Game game;
	private BufferedImage menuBackground = null;
	protected ArrayList<Button> options;
	protected int x;
	protected int y;
	protected boolean clicked = false;
	protected int currentSelection = -1;
	protected static int music = -1;
	public static int effect = 1;
	// public static boolean Preference;

	public Options(Game game) {
		BufferedImageLoader loader = new BufferedImageLoader();
		menuBackground = loader.loadImage("/MenuBackground.png");
		options = new ArrayList<Button>();
		init();
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

	protected void init() {
		options.add(new Button("Music ON", 240 + 0 * 80, new Font("Arial", Font.PLAIN, 32),
				new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		options.add(new Button("Sound effects ON", 240 + 1 * 80, new Font("Arial", Font.PLAIN, 32),
				new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		options.add(new Button("Back", 240 + 2 * 80, new Font("Arial", Font.PLAIN, 32),
				new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
	}

	public void tick() {
		for (int i = 0; i < options.size(); i++) {
			if (options.get(i).getBounds().intersects(new Rectangle(x, y, 1, 1))) {
				currentSelection = i;
				if (clicked) {
					select();
					if (i == 0) {
						music *= -1;
					} else if (i == 1) {
						effect *= -1;
					}
				}
			}
		}
		clicked = false;
		if (music > 0) {
			options.set(0, new Button("Music OFF", 240 + 0 * 80, new Font("Arial", Font.PLAIN, 32),
					new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		} else {
			options.set(0, new Button("Music ON", 240 + 0 * 80, new Font("Arial", Font.PLAIN, 32),
					new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		}
		if (effect < 0) {
			options.set(1, new Button("Sound effects OFF", 240 + 1 * 80, new Font("Arial", Font.PLAIN, 32),
					new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		} else {
			options.set(1, new Button("Sound effects ON", 240 + 1 * 80, new Font("Arial", Font.PLAIN, 32),
					new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		}
	}

	public void render(Graphics g) {
		// g.setColor(Color.black);
		// g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.drawImage(menuBackground, 0, 0, null);
		Fonts.drawString(g, new Font("Arial", Font.BOLD, 60), Color.orange, "MilkyWayExplorer", (Game.WIDTH - 500) / 2,
				70);

		for (int i = 0; i < options.size(); i++) {
			if (i == currentSelection) {
				options.get(i).setSelected(true);
			} else {
				options.get(i).setSelected(false);
			}
			options.get(i).render(g);
		}
	}

	protected void select() {
		switch (currentSelection) {
		case 0:
			if (music < 0) {
				Game.background.stop();
			} else if (music > 0) {
				Game.background = new MusicPlayer("MenuMusic");
				Game.background.start();
			}
			// System.out.println("Music");
			break;
		case 1:
			// System.out.println(effect);
			break;
		case 2:
			// System.out.println("back");
			// System.out.println("Exit");
			// game.stop();
			// System.exit(0);
			Game.gameState = State.Menu;
			break;
		}
	}
}
