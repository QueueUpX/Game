package Main;

import java.awt.Graphics;
import java.util.ArrayList;

import Framework.GameObject;
//import Framework.ObjectId;
//import Objects.Block;

public class Handler {
	// private GameObject tempObject;

	public ArrayList<GameObject> object = new ArrayList<GameObject>();

	public void tick() {
		for (int i = 0; i < object.size(); i++) {
			// tempObject = object.get(i);
			// tempObject.tick(object);

			object.get(i).tick(object);
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			// tempObject = object.get(i);
			// tempObject.render(g);

			object.get(i).render(g);
		}
	}

	public void addPlayer(GameObject object) {
		this.object.add(object);
	}
	
	public void addEnemy(GameObject object) {
		this.object.add(object);
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}

	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

	// Create test level
	/*
	 * public void createLevel() { for (int xx = 0; xx < Game.WIDTH + 30; xx += 30)
	 * { addObject(new Block(xx, Game.HEIGHT - 30, ObjectId.Block)); } for (int xx =
	 * 100; xx < Game.WIDTH + 30; xx += 30) { addObject(new Block(xx, Game.HEIGHT -
	 * 200, ObjectId.Block)); } for (int xx = 0; xx < Game.HEIGHT; xx += 30) {
	 * addObject(new Block(0, xx, ObjectId.Block)); } for (int xx = 0; xx <
	 * Game.HEIGHT; xx += 30) { addObject(new Block(Game.WIDTH - 30, xx,
	 * ObjectId.Block)); } }
	 */
}
