package Objects;

import Framework.ObjectId;
import Main.Handler;

public abstract class Platform extends Block {
	protected float startX, startY;

	public Platform(float x, float y, float width, float height, ObjectId id, Handler handler) {
		super(x, y, width, height, id, handler);
		this.startX = x;
		this.startY = y;
	}
}
