package Framework;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class GameObject {
	protected float x, y, velX = 0, velY = 0;
	protected ObjectId id;
	protected float width, height;

	public GameObject(float x, float y, float width, float height, ObjectId id) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public abstract void tick(ArrayList<GameObject> object);

	public abstract void render(Graphics g);

	public abstract Rectangle getBounds();

	public float getX() {
		return x;
	}

	public float getVelX() {
		return velX;
	}

	public float getY() {
		return y;
	}

	public float getVelY() {
		return velY;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public ObjectId getId() {
		return id;
	}
}
