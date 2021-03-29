package Objects;

//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.util.ArrayList;

import Framework.GameObject;
import Framework.ObjectId;
import Main.Handler;

public class PlatformHorizontal extends Platform {
	private float maxX, minX;

	public PlatformHorizontal(float x, float y, float width, float height, float velX, float minX, float maxX,
			ObjectId id,Handler handler) {
		super(x, y, width, height, id,handler);
		this.velX = velX;

		this.minX = minX;
		this.maxX = maxX;
	}

	@Override
	public void tick(ArrayList<GameObject> object) {
		x += velX;
		if (x == startX + maxX) {
			velX *= -1;
		} else if (x == startX + minX) {
			velX *= -1;
		}
	}
	/*
	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//g2d.setColor(Color.yellow);
		//g2d.fillRect((int) x, (int) y, (int) width, (int) height);
		g2d.drawImage(terrain,(int)x,(int)y,null);
	}*/
}
