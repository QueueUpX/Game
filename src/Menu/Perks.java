package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import Framework.State;
import Main.Game;
import Objects.Player;

public class Perks {
	private Game game;
	private ArrayList<Button> options;
	private int x, y, currentSelection = -1;
	private boolean clicked = false;
	private final float mobility = 0.2f, armour = 1, shield = 10;
	private boolean m = false, a = false, s = false;
	private Button select;

	public Perks(Game game) {
		this.game = game;
		options = new ArrayList<Button>();
		init();
		back();

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

	private void init() {
		options.add(new Button("Mobility=" + Player.Mobility + "(+" + mobility + ")", 240 + 0 * 80,
				new Font("Arial", Font.PLAIN, 32), new Font("Arial", Font.BOLD, 38), Color.white, Color.cyan));
		options.add(new Button("Armour=" + Player.Armour + "(+" + armour + ")", 240 + 1 * 80,
				new Font("Arial", Font.PLAIN, 32), new Font("Arial", Font.BOLD, 38), Color.white, Color.orange));
		options.add(new Button("Shield=" + Player.Shield + "(+" + shield + ")", 240 + 2 * 80,
				new Font("Arial", Font.PLAIN, 32), new Font("Arial", Font.BOLD, 38), Color.white, Color.blue));
	}

	private void back() {
		select = new Button("SELECT", 240 + 6 * 80, new Font("Arial", Font.PLAIN, 48), new Font("Arial", Font.BOLD, 38),
				Color.white, Color.white);
	}

	private void update() {
		options.set(0, new Button("Mobility=" + Player.Mobility + "(+" + mobility + ")", 240 + 0 * 80,
				new Font("Arial", Font.PLAIN, 32), new Font("Arial", Font.BOLD, 38), Color.white, Color.cyan));
		options.set(1, new Button("Armour=" + Player.Armour + "(+" + armour + ")", 240 + 1 * 80,
				new Font("Arial", Font.PLAIN, 32), new Font("Arial", Font.BOLD, 38), Color.white, Color.orange));
		options.set(2, new Button("Shield=" + Player.Shield + "(+" + shield + ")", 240 + 2 * 80,
				new Font("Arial", Font.PLAIN, 32), new Font("Arial", Font.BOLD, 38), Color.white, Color.blue));
	}

	private void upgrade() {
		if (m) {
			Player.Mobility += mobility;
		}
		if (a) {
			Player.Armour += armour;
		}
		if (s) {
			Player.Shield += shield;
		}
	}

	private void select() {
		switch (currentSelection) {
		case 0:
			// Player.Mobility += mobility;
			// update();
			m = true;
			a = false;
			s = false;
			break;
		case 1:
			// Player.Armour += armour;
			// update();
			m = false;
			a = true;
			s = false;
			break;
		case 2:
			// Player.Shield += shield;
			// update();
			m = false;
			a = false;
			s = true;
			break;
		}
	}

	public void tick() {
		if (select.getBounds().intersects(new Rectangle(x, y, 1, 1))) {
			if (clicked && (m || a || s)) {
				upgrade();
				update();
				Game.gameState = State.Game;
			}
		}
		for (int i = 0; i < options.size(); i++) {
			if (options.get(i).getBounds().intersects(new Rectangle(x, y, 1, 1))) {
				currentSelection = i;
				if (clicked) {
					select();
					// Game.gameState = State.Game;
				}
			}
		}
		clicked = false;
	}

	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		// g.drawImage(menuBackground, 0, 0, null);
		Fonts.drawString(g, new Font("Arial", Font.BOLD, 60), Color.orange, "PERKS", (Game.WIDTH - 500) / 2, 70);

		for (int i = 0; i < options.size(); i++) {
			if (i == currentSelection) {
				options.get(i).setSelected(true);
			} else {
				options.get(i).setSelected(false);
			}
			options.get(i).render(g);
		}
		select.render(g);
	}
}
