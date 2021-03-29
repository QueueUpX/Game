package Objects;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Framework.GameObject;
import Framework.ObjectId;
import Main.BufferedImageLoader;
import Main.Handler;

public class Block extends GameObject {
	protected BufferedImage terrain;
	protected static GameObject player = null;
	protected Handler handler;

	public Block(float x, float y, float width, float height, ObjectId id, Handler handler) {
		super(x, y, width, height, id);
		this.handler = handler;
		BufferedImageLoader loader = new BufferedImageLoader();
		terrain = loader.loadImage("/Terrain.png");
		findPlayer();
	}

	protected void findPlayer() {
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ObjectId.Player) {
				player = handler.object.get(i);
				break;
			}
		}
	}

	@Override
	public void tick(ArrayList<GameObject> object) {

	}

	@Override
	public void render(Graphics g) {
		if (player != null && Math.abs(x - player.getX()) < 900 && Math.abs(y-player.getY())<550) {
			Graphics2D g2d = (Graphics2D) g;
			// g2d.setColor(Color.red);
			// g2d.fillRect((int) x, (int) y, (int) width, (int) height);
			g2d.drawImage(terrain, (int) x, (int) y, null);
		} else {
			findPlayer();
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}
}
