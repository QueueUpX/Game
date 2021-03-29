package Objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Framework.GameObject;
import Framework.KeyInput;
import Framework.ObjectId;
import Framework.State;
import Main.BufferedImageLoader;
import Main.Game;
import Main.Handler;
import Menu.Fonts;

//// Standard Mobility=max 10 blocks(32x32)
public class Player extends GameObject {
	private float gravity = 0.6f;
	private float _acc = 1f, _dcc = 0.5f;
	private boolean falling = true, jumping = false;
	public static float maxHP = 1500;
	public static float hp = maxHP;
	public static float score = 0;
	public static float Damage = 6.4f, travelSpeed = 10, Mobility = 5f, Armour = 0, Shield = 0, currShield = Shield;
	public static long travelDistance = 2000;
	private long timer;
	private boolean shieldRecharging = false;
	private int upgradeScale = 1, upgradeScore = 50, perkScale = 1, perkScore = 100;
	public static boolean END = false;
	// private Fonts font;

	private Handler handler;
	private KeyInput input;
	private BufferedImage playerDown, playerUp, playerLeft, playerRight, playerLeftUp, playerRightUp;

	public Player(float x, float y, float width, float height, Handler handler, ObjectId id, KeyInput input) {
		super(x, y, width, height, id);
		this.handler = handler;
		this.input = input;
		BufferedImageLoader loader = new BufferedImageLoader();
		playerDown = loader.loadImage("/PlayerDown.png");
		playerUp = loader.loadImage("/PlayerUp.png");
		playerLeft = loader.loadImage("/PlayerLeft.png");
		playerRight = loader.loadImage("/PlayerRight.png");
		playerLeftUp = loader.loadImage("/PlayerLeftUp.png");
		playerRightUp = loader.loadImage("/PlayerRightUp.png");
	}

	@Override
	public void tick(ArrayList<GameObject> object) {
		if (END && Enemy.EnemyCount == 0) {
			Game.gameState = State.End;
		}
		if (hp == 0) {
			Game.gameState = State.Restart;
		}
		if ((int) score % (upgradeScore * upgradeScale) == 0 && (int) score % (perkScore * perkScale) != 0
				&& score != 0) {
			upgradeScale++;
			Game.gameState = State.Upgrade;
		}
		if ((int) score % (perkScore * perkScale) == 0 && (int) score % (upgradeScore * upgradeScale) != 0
				&& score != 0) {
			perkScale++;
			Game.gameState = State.Perks;
		}
		if (currShield == 0 && Shield != 0 && !shieldRecharging) {
			timer = System.currentTimeMillis();
			shieldRecharging = true;
		}

		x += velX;
		y += velY;

		if (input.getKeys()[2]) { // right
			velX += _acc;
		} else if (input.getKeys()[1]) { // left
			velX -= _acc;
		}
		if (!input.getKeys()[2] && velX > 0) {
			velX -= _dcc;
			if (velX < 0) {
				velX = 0;
			}
		} else if (!input.getKeys()[1] && velX < 0) {
			velX += _dcc;
			if (velX > 0) {
				velX = 0;
			}
		}
		if (input.getKeys()[0] && !jumping && !falling) {
			velY = -50;
			jumping = true;
		}
		if (!input.getKeys()[0]) {
			velY += gravity;
			falling = true;
		}

		velX = clamp(velX, Mobility, -Mobility);
		velY = clamp(velY, Mobility * 4, -Mobility * 4);

		if (falling || jumping) {
			// jumping=true; //buggy
			velY += gravity;
		}

		// Collision(object);
		Collision();
		shot();
	}

	@Override
	public void render(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		// Collision Boxes
		// g2d.setColor(Color.magenta);
		// g2d.fillRect((int) x, (int) y, (int) width, (int) height);

		if (input.getKeys()[0]) {
			if (input.getKeys()[2]) {
				g2d.drawImage(playerRightUp, (int) x, (int) y, null);
			} else if (input.getKeys()[1]) {
				g2d.drawImage(playerLeftUp, (int) x, (int) y, null);
			} else {
				g2d.drawImage(playerUp, (int) x, (int) y, null);
			}
		}

		if (input.getKeys()[2]) {
			g2d.drawImage(playerRight, (int) x, (int) y, null);
		} else if (input.getKeys()[1]) {
			g2d.drawImage(playerLeft, (int) x, (int) y, null);
		} else {
			g2d.drawImage(playerDown, (int) x, (int) y, null);
		}
		// g2d.setColor(Color.red);
		// g2d.drawRect((int)x-800, (int)y-450, 100, 100);

		Fonts.drawString(g2d, new Font("Arial", Font.PLAIN, 36), Color.green, "Health=" + hp, (int) x - 800,
				(int) y - 450 + 36);
		Fonts.drawString(g2d, new Font("Arial", Font.PLAIN, 24), Color.white, "Score=" + (int) score, (int) x - 800,
				(int) y - 450 + 24 + 36);
		if (Shield > 0) {
			Fonts.drawString(g2d, new Font("Arial", Font.PLAIN, 24), Color.blue, "Shield=" + currShield,
					(int) x - 800 + 250, (int) y - 450 + 30);
		}
		/*
		 * g2d.setColor(Color.cyan); g2d.fill(getBoundsH()); g2d.setColor(Color.white);
		 * g2d.draw(getBoundsV());
		 */

		// Old Collision boxes
		/*
		 * g.setColor(Color.black); g2d.draw(getBounds()); g2d.draw(getBoundsLeft());
		 * g2d.draw(getBoundsRight()); g2d.draw(getBoundsTop());
		 */

	}

	private void shot() {
		if (System.currentTimeMillis() - timer > 5000 && shieldRecharging) {
			currShield = Shield;
			shieldRecharging = false;
		}
		for (int i = 0; i < handler.object.size(); i++) {
			try {
				if (handler.object.get(i).getId() == ObjectId.EnemyBullet
						&& getBounds().intersects(handler.object.get(i).getBounds())) {
					currShield -= 10;
					if (currShield < 0) {
						hp -= (-currShield - Armour);
					}
					handler.removeObject(handler.object.get(i));
				}
			} catch (IndexOutOfBoundsException e) {
				// System.out.println("Happened in player while shot!");
			}
		}
		if (currShield < 0) {
			currShield = 0;
			shieldRecharging = true;
			timer = System.currentTimeMillis();
		}
	}

	// Old Collision System
	/*
	 * private void Collision(LinkedList<GameObject> object) { for (int i = 0; i <
	 * handler.object.size(); i++) { GameObject tempObject = handler.object.get(i);
	 * if (tempObject.getId() == ObjectId.Block) { // Top if
	 * (getBoundsTop().intersects(tempObject.getBounds())) { y = tempObject.getY() +
	 * 35; velY = 0; }
	 * 
	 * // Bottom if (getBounds().intersects(tempObject.getBounds())) { y =
	 * tempObject.getY() - height; velY = 0; falling = false; jumping = false; }
	 * else { falling = true; }
	 * 
	 * // Right if (getBoundsRight().intersects(tempObject.getBounds()) ||
	 * getBoundsTop().intersects(tempObject.getBounds())) { x = tempObject.getX() -
	 * width; }
	 * 
	 * // Left if (getBoundsLeft().intersects(tempObject.getBounds()) ||
	 * getBoundsTop().intersects(tempObject.getBounds())) { x = tempObject.getX() +
	 * 31; } } } }
	 */

	// Old Collision Recatangles
	/*
	 * @Override public Rectangle getBounds() { return new Rectangle((int) x + (int)
	 * (width / 2) - (int) ((width / 2) / 2), (int) y + (int) (height / 2) + 1,
	 * (int) width / 2, (int) height / 2); }
	 * 
	 * public Rectangle getBoundsRight() { return new Rectangle((int) x + (int)
	 * (width - 5), (int) y + 2, (int) 5, (int) height - 5); }
	 * 
	 * public Rectangle getBoundsLeft() { return new Rectangle((int) x, (int) y + 5,
	 * (int) 5, (int) height - 10); }
	 * 
	 * public Rectangle getBoundsTop() { return new Rectangle((int) x + (int) (width
	 * / 2) - (int) ((width / 2) / 2), (int) y, (int) width / 2, (int) height / 2);
	 * }
	 */

	private void Collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			try {
				if (handler.object.get(i).getId() == ObjectId.EndGame) {
					if (getBoundsH().intersects(handler.object.get(i).getBounds())) {
						END = true;
						// Game.gameState = State.End;
					}
				}
				if (handler.object.get(i).getId() == ObjectId.Block
						|| handler.object.get(i).getId() == ObjectId.PlatformVertical
						|| handler.object.get(i).getId() == ObjectId.PlatformHorizontal
						|| handler.object.get(i).getId() == ObjectId.PlatformHorVer) {
					if (getBoundsH().intersects(handler.object.get(i).getBounds())) {
						if (handler.object.get(i).getVelX() < 0
								&& x < handler.object.get(i).getX() + handler.object.get(i).getWidth() / 2) {// block
																												// left
							x = handler.object.get(i).getX() - width;
						} else if (handler.object.get(i).getVelX() > 0
								&& x > handler.object.get(i).getX() + handler.object.get(i).getWidth() / 2) {// block
																												// right
							x = handler.object.get(i).getX() + handler.object.get(i).getWidth();
						}

						if (velX > 0) { // right
							velX = 0;
							x = handler.object.get(i).getX() - width;
						}
						if (velX < 0) { // left
							velX = 0;
							x = handler.object.get(i).getX() + handler.object.get(i).getWidth();
						}
					}

					if (getBoundsV().intersects(handler.object.get(i).getBounds())) {
						if (handler.object.get(i).getVelY() < 0
								&& y < handler.object.get(i).getY() + handler.object.get(i).getHeight() / 2) { // block
																												// up
							y = handler.object.get(i).getY() - height;
						} else if (handler.object.get(i).getVelY() > 0
								&& y > handler.object.get(i).getY() + handler.object.get(i).getHeight() / 2) {// block
																												// down
							y = handler.object.get(i).getY() + handler.object.get(i).getHeight();
						}

						if (velY > 0) { // down
							velY = 0;
							y = handler.object.get(i).getY() - height;
							falling = false;
							jumping = false;
						}
						// else if(velY==0){
						// falling=true;
						// }
						if (velY < 0) { // up
							velY = 0;
							y = handler.object.get(i).getY() + handler.object.get(i).getHeight();
						}
					}
				}
			} catch (IndexOutOfBoundsException e) {
				// System.out.println("Happened in player!");
			}
		}
	}

	public Rectangle getBoundsH() {
		float bx = x + velX;
		float by = y + 2;
		float bw = width + velX / 2;
		float bh = height - 4;
		return new Rectangle((int) bx, (int) by, (int) bw, (int) bh);
	}

	public Rectangle getBoundsV() {
		float bx = x + 2;
		float by = y + velY;
		float bw = width - 4;
		float bh = height + velY / 2;
		return new Rectangle((int) bx, (int) by, (int) bw, (int) bh);
	}

	private float clamp(float value, float max, float min) {
		if (value >= max) {
			value = max;
		} else if (value <= min) {
			value = min;
		}
		return value;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}

}
