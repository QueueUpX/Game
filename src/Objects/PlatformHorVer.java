package Objects;

//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.util.ArrayList;

import Framework.GameObject;
import Framework.ObjectId;
import Main.Handler;

public class PlatformHorVer extends Platform {
	private float minX, maxX, minY, maxY;

	public PlatformHorVer(float x, float y, float width, float height, float velX, float velY, float minX, float maxX,
			float minY, float maxY, ObjectId id,Handler handler) {
		super(x, y, width, height, id,handler);
		this.velX = velX;
		this.velY = velY;

		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}

	@Override
	public void tick(ArrayList<GameObject> object) {
		x += velX;
		if (x == startX + maxX) {
			velX *= -1;
		} else if (x == startX + minX) {
			velX *= -1;
		}

		y += velY;
		if (y == startY + maxY) {
			velY *= -1;
		} else if (y == startY + minY) {
			velY *= -1;
		}
	}
	/*
	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//g2d.setColor(Color.green);
		//g2d.fillRect((int) x, (int) y, (int) width, (int) height);
		g2d.drawImage(terrain,(int)x,(int)y,null);
	}*/
}
