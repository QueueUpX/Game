package Objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import Framework.GameObject;
import Framework.ObjectId;

public class EndGame extends GameObject {

	public EndGame(float x, float y, float width, float height, ObjectId id) {
		super(x, y, width, height, id);
	}

	@Override
	public void tick(ArrayList<GameObject> object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}

}
