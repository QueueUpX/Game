package Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Framework.GameObject;
import Framework.ObjectId;
import Main.BufferedImageLoader;
import Main.Game;
import Main.Handler;
import Menu.Options;

public class Enemy extends GameObject {
	public static int EnemyCount=0;
	private float distance, diffX, diffY;
	private float HP = 64, bulletVel = 7, AS = 500;
	public static long travelDistance=2000;
	private int i = 0;
	// private boolean colliding = false;
	private GameObject tempObject;
	private long timeStart, animationStart;
	private boolean animation = false;
	// private boolean up=false,down=false,right=false,left=false;;
	// private boolean falling,jumping;

	private Handler handler;
	private BufferedImage enemy;
	private BufferedImage[] explosion = new BufferedImage[5];

	public Enemy(float x, float y, float width, float height, Handler handler, ObjectId id) {
		super(x, y, width, height, id);
		this.handler = handler;
		BufferedImageLoader loader = new BufferedImageLoader();
		EnemyCount++;
		enemy = loader.loadImage("/Enemy.png");
		explosion[0] = loader.loadImage("/frame2.png");
		explosion[1] = loader.loadImage("/frame3.png");
		explosion[2] = loader.loadImage("/frame4.png");
		explosion[3] = loader.loadImage("/frame5.png");
		explosion[4] = loader.loadImage("/frame6.png");
	}

	@Override
	public void tick(ArrayList<GameObject> object) {
		if (!animation) {
			x += velX;
			y += velY;

			if (HP < 64) {
				HP += 0.066f;
			}

			if (HP > 64) {
				HP = 64;
			}

			for (int i = 0; i < handler.object.size(); i++) {
				if (handler.object.get(i).getId() == ObjectId.Player) {
					tempObject = handler.object.get(i);
					diffX = x - handler.object.get(i).getX() - width;
					diffY = y - handler.object.get(i).getY() - height;
					distance = (float) Math.sqrt((x - handler.object.get(i).getX()) * (x - handler.object.get(i).getX())
							+ (y - handler.object.get(i).getY()) * (y - handler.object.get(i).getY()));
					// System.out.println(distance);
				}
			}

			if ((int) distance < 1000 && (int) distance > 250) {
				velX = ((-1 / distance) * (diffX)) * 5;
				velY = ((-1 / distance) * (diffY)) * 5;
				// velY+=gravity;
				if (System.currentTimeMillis() - timeStart > (long) (AS - 1000 / HP)) {
					shoot();
					timeStart += (long) (AS - 1000 / HP);
				}
			} else if ((int) distance < 200) {
				velX = -(((-1 / distance) * (diffX)) * 5);
				velY = -(((-1 / distance) * (diffY)) * 5);
				if (System.currentTimeMillis() - timeStart > (long) (AS - 1000 / HP)) {
					shoot();
					timeStart += (long) (AS - 1000 / HP);
				}
			} else if ((int) distance == 250) {
				velX = 0;
				velY = 0;
				if (System.currentTimeMillis() - timeStart > (long) (AS - 1000 / HP)) {
					shoot();
					timeStart += (long) (AS - 1000 / HP);
				}
			} else if ((int) distance > 1005) {
				velX = 0;
				velY = 0;
			}

			// if(falling) {
			// velY+=gravity;
			// }

			// Collision();
			shot();
		}
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (animation == false)
			g2d.drawImage(enemy, (int) x, (int) y, null);
		else
			g2d.drawImage(explosion[i], (int) x, (int) y, null);
		g2d.setColor(Color.red);
		g2d.fillRect((int) (x), (int) (y + height), (int) HP, 10);
		try {
			if (HP < 0 && animation == false) {
				animationStart = System.currentTimeMillis();
				animation = true;
				if (Options.effect > 0) {
					Game.effect.playOnceSound("./Audio/explosion.wav", 6);
				}
			}
			if (animation == true && System.currentTimeMillis() - animationStart > 100) {

				i++;
				animationStart += 100;
				// handler.object.remove(this);
			}

			if (i == 5) {
				EnemyCount--;
				handler.object.remove(this);
				Player.score += 10;
				i = 0;
				animation = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// g.setColor(Color.pink);
		// g.fillRect((int) x, (int) y, (int) width, (int) height);
	}

	private void shot() {
		try {
			// if (HP < 0) {
			// handler.object.remove(this);
			// }
			for (int i = 0; i < handler.object.size(); i++) {

				if (handler.object.get(i).getId() == ObjectId.Bullet
						&& getBounds().intersects(handler.object.get(i).getBounds())) {
					handler.removeObject(handler.object.get(i));
					HP -= Player.Damage;
				}

			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Happened in enemy while shot!");
		}
	}

	private void shoot() {
		timeStart = System.currentTimeMillis();
		EnemyBullet tempBullet = new EnemyBullet(x, y, 8, 8, handler, ObjectId.EnemyBullet);
		EnemyBullet[] axBullets = new EnemyBullet[4];
		axBullets[0] = new EnemyBullet(x, y, 8, 8, handler, ObjectId.EnemyBullet);
		axBullets[1] = new EnemyBullet(x, y, 8, 8, handler, ObjectId.EnemyBullet);
		axBullets[2] = new EnemyBullet(x, y, 8, 8, handler, ObjectId.EnemyBullet);
		axBullets[3] = new EnemyBullet(x, y, 8, 8, handler, ObjectId.EnemyBullet);

		handler.addObject(tempBullet);
		if (Options.effect > 0) {
			Game.effect.playOnceSound("./Audio/enemylaser.wav", 0);
		}

		float angle = (float) Math.atan2(tempObject.getY() + tempObject.getHeight() / 2 - y,
				tempObject.getX() + tempObject.getWidth() / 2 - x);
		bulletVel = 10;
		tempBullet.setVelX((float) ((bulletVel + (100 / HP)) * Math.cos(angle)));
		tempBullet.setVelY((float) ((bulletVel + (100 / HP)) * Math.sin(angle)));
		if (HP < 43) {
			handler.addObject(axBullets[0]);
			handler.addObject(axBullets[1]);
			handler.addObject(axBullets[2]);
			handler.addObject(axBullets[3]);
			
			axBullets[0].setVelX((float) ((bulletVel) * Math.cos(1)));
			axBullets[0].setVelY((float) ((bulletVel) * Math.sin(0)));
			
			//axBullets[1].setVelX((float) ((bulletVel + (100 / HP)) * Math.cos(0)));
			axBullets[1].setVelY((float) ((bulletVel) * Math.sin(1)));
			
			//axBullets[2].setVelX((float) ((bulletVel + (100 / HP)) * Math.cos(-1)));
			axBullets[2].setVelY((float) ((bulletVel) * Math.sin(1)*-1));
			
			axBullets[3].setVelX((float) ((bulletVel) * Math.cos(1)*-1));
			axBullets[3].setVelY((float) ((bulletVel) * Math.sin(0)*-1));
		}
	}
	/*
	 * private void Collision() {
	 * 
	 * //colliding = false; for (int i = 0; i < handler.object.size(); i++) { try {
	 * if (handler.object.get(i).getId() == ObjectId.Enemy) { if
	 * (getBoundsH().intersects(handler.object.get(i).getBounds())) { if
	 * (handler.object.get(i).getVelX() < 0 && x < handler.object.get(i).getX() +
	 * handler.object.get(i).getWidth() / 2) {// block // left x =
	 * handler.object.get(i).getX() - width; } else if
	 * (handler.object.get(i).getVelX() > 0 && x > handler.object.get(i).getX() +
	 * handler.object.get(i).getWidth() / 2) {// block // right x =
	 * handler.object.get(i).getX() + handler.object.get(i).getWidth(); }
	 * 
	 * // right=false;
	 * 
	 * if (velX > 0) { // right velX = 0; x = handler.object.get(i).getX() - width;
	 * System.out.println("EnemyRiGHT"); //colliding = true; } // left=false; if
	 * (velX < 0) { // left velX = 0; x = handler.object.get(i).getX() +
	 * handler.object.get(i).getWidth(); System.out.println("EnemyLeft");
	 * //colliding = true; } }
	 * 
	 * if (getBoundsV().intersects(handler.object.get(i).getBounds())) { if
	 * (handler.object.get(i).getVelY() < 0 && y < handler.object.get(i).getY() +
	 * handler.object.get(i).getHeight() / 2) { // block // up y =
	 * handler.object.get(i).getY() - height; } else if
	 * (handler.object.get(i).getVelY() > 0 && y > handler.object.get(i).getY() +
	 * handler.object.get(i).getHeight() / 2) {// block // down y =
	 * handler.object.get(i).getY() + handler.object.get(i).getHeight(); } //
	 * down=false;
	 * 
	 * if (velY > 0) { // down velY = 0; y = handler.object.get(i).getY() - height;
	 * System.out.println("Enemydown"); //colliding = true; // falling = false; //
	 * jumping = false; } // else if(velY==0){ // falling=true; // } if (velY < 0) {
	 * // up velY = 0; y = handler.object.get(i).getY() +
	 * handler.object.get(i).getHeight(); System.out.println("EnemyUP"); //colliding
	 * = true; } } } } catch (IndexOutOfBoundsException e) {
	 * System.out.println("Happened in enemy!"); } } }
	 */
	/*
	 * private float clamp(float value, float max, float min) { if (value >= max) {
	 * value = max; } else if (value <= min) { value = min; } return value; }
	 */

	public Rectangle getBoundsH() {
		float bx = x + velX;
		float by = y + 1;
		float bw = width + velX / 2;
		float bh = height - 2;
		return new Rectangle((int) bx, (int) by, (int) bw, (int) bh);
	}

	public Rectangle getBoundsV() {
		float bx = x + 1;
		float by = y + velY;
		float bw = width - 2;
		float bh = height + velY / 2;
		return new Rectangle((int) bx, (int) by, (int) bw, (int) bh);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}
}
