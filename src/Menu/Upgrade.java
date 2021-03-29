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

public class Upgrade {
	private Game game;
	private ArrayList<Button> options;
	private int x, y, currentSelection = -1;
	private boolean clicked = false;
	private final float dmg = 1.2f, travSpeed = 1, travDist = 250, mHP = 100;
	private boolean d = false, ts = false, td = false, mh = false;
	private Button select;

	public Upgrade(Game game) {
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
		options.add(new Button("Atack Damage=" + Player.Damage + "(+" + dmg + ")", 240 + 0 * 80,
				new Font("Arial", Font.PLAIN, 32), new Font("Arial", Font.BOLD, 38), Color.white, Color.red));
		options.add(new Button("Bullet Speed=" + Player.travelSpeed + "(+" + travSpeed + ")", 240 + 1 * 80,
				new Font("Arial", Font.PLAIN, 32), new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		options.add(new Button("Bullet Distance=" + Player.travelDistance + "(+" + travDist + ")", 240 + 2 * 80,
				new Font("Arial", Font.PLAIN, 32), new Font("Arial", Font.BOLD, 38), Color.white, Color.lightGray));
		options.add(new Button("Health Points=" + Player.maxHP + "(+" + mHP + ")", 240 + 3 * 80,
				new Font("Arial", Font.PLAIN, 32), new Font("Arial", Font.BOLD, 38), Color.white, Color.green));
	}

	private void update() {
		options.set(0, new Button("Atack Damage=" + Player.Damage + "(+" + dmg + ")", 240 + 0 * 80,
				new Font("Arial", Font.PLAIN, 32), new Font("Arial", Font.BOLD, 38), Color.white, Color.red));
		options.set(1, new Button("Bullet Speed=" + Player.travelSpeed + "(+" + travSpeed + ")", 240 + 1 * 80,
				new Font("Arial", Font.PLAIN, 32), new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		options.set(2, new Button("Bullet Distance=" + Player.travelDistance + "(+" + travDist + ")", 240 + 2 * 80,
				new Font("Arial", Font.PLAIN, 32), new Font("Arial", Font.BOLD, 38), Color.white, Color.lightGray));
		options.set(3, new Button("Health Points=" + Player.maxHP + "(+" + mHP + ")", 240 + 3 * 80,
				new Font("Arial", Font.PLAIN, 32), new Font("Arial", Font.BOLD, 38), Color.white, Color.green));
	}

	private void upgrade() {
		if (d) {
			Player.Damage += dmg;
		} else if (ts) {
			Player.travelSpeed += travSpeed;
		} else if (td) {
			Player.travelDistance += travDist;
		} else if (mh) {
			Player.maxHP += mHP;
			Player.hp += mHP;
		}
	}

	private void back() {
		select = new Button("SELECT", 240 + 6 * 80, new Font("Arial", Font.PLAIN, 48), new Font("Arial", Font.BOLD, 38),
				Color.white, Color.white);
	}

	private void select() {
		switch (currentSelection) {
		case 0:
			// Player.Damage += dmg;
			// update();
			d = true;
			ts = false;
			td = false;
			mh = false;
			break;
		case 1:
			// Player.travelSpeed += travSpeed;
			// update();
			d = false;
			ts = true;
			td = false;
			mh = false;
			break;
		case 2:
			// Player.travelDistance += travDist;
			// update();
			d = false;
			ts = false;
			td = true;
			mh = false;
			break;
		case 3:
			// Player.maxHP += mHP;
			// Player.hp+=mHP;
			// update();
			d = false;
			ts = false;
			td = false;
			mh = true;
			break;
		}
	}

	public void tick() {
		if (select.getBounds().intersects(new Rectangle(x, y, 1, 1))) {
			if (clicked && (d || ts || ts || mh)) {
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
		Fonts.drawString(g, new Font("Arial", Font.BOLD, 60), Color.orange, "UPGRADES", (Game.WIDTH - 500) / 2, 70);

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
