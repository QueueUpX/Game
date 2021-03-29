package Main;

import Framework.GameObject;
import Framework.ObjectId;

public class Camera {
	private float x, y;
	private Handler handler;
	private GameObject tempObject = null;

	public Camera(float x, float y, Handler handler) {
		this.x = x;
		this.y = y;
		this.handler = handler;

		findPlayer();
	}

	public void findPlayer() {
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ObjectId.Player) {
				tempObject = handler.object.get(i);
				break;
			}
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void tick() {
		if (tempObject != null) {
			x = tempObject.getX() - Game.WIDTH / 2;
			y = tempObject.getY() - Game.HEIGHT / 2;
		} else {
			findPlayer();
		}
	}
}
