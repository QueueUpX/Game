package Objects;

//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.util.ArrayList;

import Framework.GameObject;
import Framework.ObjectId;
import Main.Handler;

public class PlatformVertical extends Platform {
	private float maxY, minY;

	public PlatformVertical(float x, float y, float width, float height, float velY, float minY, float maxY,
			ObjectId id,Handler handler) {
		super(x, y, width, height, id,handler);
		this.velY = velY;

		this.minY = minY;
		this.maxY = maxY;
	}

	@Override
	public void tick(ArrayList<GameObject> object) {
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
		//g2d.setColor(Color.cyan);
		//g2d.fillRect((int) x, (int) y, (int) width, (int) height);
		g2d.drawImage(terrain,(int)x,(int)y,null);
	}*/
}
