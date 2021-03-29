package Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import Framework.GameObject;
import Framework.ObjectId;
import Main.Handler;

public class Bullet extends GameObject {
	private Handler handler;
	private long timeStart;

	public Bullet(float x, float y, float width, float height, Handler handler, ObjectId id) {
		super(x, y, width, height, id);
		this.handler = handler;
		timeStart = System.currentTimeMillis();
	}

	@Override
	public void tick(ArrayList<GameObject> object) {
		x += velX;
		y += velY;

		Collision();

		if (System.currentTimeMillis() - timeStart > Player.travelDistance) {
			handler.removeObject(this);
		}
	}

	private void Collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			if (getBounds().intersects(handler.object.get(i).getBounds())
					&& (handler.object.get(i).getId() == ObjectId.Block
					|| handler.object.get(i).getId() == ObjectId.PlatformHorizontal
					|| handler.object.get(i).getId() == ObjectId.PlatformVertical
					|| handler.object.get(i).getId() == ObjectId.PlatformHorVer)) {
				handler.removeObject(this);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.blue);
		g2d.fillRect((int) x, (int) y, (int) width, (int) height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}

}
