package Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Framework.GameObject;
import Framework.ObjectId;
import Main.Handler;

public class EnemyBullet extends Bullet{
	private Handler handler;
	private long timeStart;
	
	public EnemyBullet(float x, float y, float width, float height, Handler handler, ObjectId id) {
		super(x, y, width, height, handler, id);
		this.handler=handler;
		timeStart=System.currentTimeMillis();
	}
	
	@Override
	public void tick(ArrayList<GameObject> object) {
		x += velX;
		y += velY;

		//Collision();

		if (System.currentTimeMillis() - timeStart > Enemy.travelDistance) {
			handler.removeObject(this);
		}
	}
	/*
	private void Collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			if (getBounds().intersects(handler.object.get(i).getBounds())
					&& handler.object.get(i).getId() == ObjectId.Block) {
				handler.removeObject(this);
			}
		}
	}
	*/
	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.red);
		g2d.fillRect((int) x, (int) y, (int) width, (int) height);
	}
}
