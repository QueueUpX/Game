package Framework;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Main.Camera;
import Main.Game;
import Main.Handler;
import Menu.Options;
import Objects.Bullet;
import Objects.Player;

public class MouseInput extends MouseAdapter {
	private Handler handler;
	private GameObject tempObject = null;
	private Camera cam;

	public MouseInput(Handler handler, Camera cam) {
		this.handler = handler;
		this.cam = cam;
	}

	public void findPlayer() {
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ObjectId.Player) {
				tempObject = handler.object.get(i);
				break;
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		if (Game.gameState == State.Game) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				int mx = e.getX();
				int my = e.getY();

				if (tempObject != null) {
					Bullet tempBullet = new Bullet(tempObject.x + 16, tempObject.y + 16, 8, 8, handler,
							ObjectId.Bullet);
					handler.addObject(tempBullet);
					if(Options.effect>0) {
						Game.effect.playOnceSound("./Audio/laser.wav",0);
					}

					float angle = (float) Math.atan2(my - tempObject.y - 16 + cam.getY(),
							mx - tempObject.x - 16 + cam.getX());
					float bulletVel = Player.travelSpeed;

					tempBullet.velX = (float) ((bulletVel) * Math.cos(angle));
					tempBullet.velY = (float) ((bulletVel) * Math.sin(angle));
				} else {
					findPlayer();
				}
			}
		}
	}
}
