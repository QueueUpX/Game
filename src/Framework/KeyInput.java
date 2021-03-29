package Framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Main.Game;

public class KeyInput extends KeyAdapter {

	private boolean keys[] = new boolean[3];

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			keys[0] = true;
		}
		if (key == KeyEvent.VK_A) {
			keys[1] = true;
		}
		if (key == KeyEvent.VK_D) {
			keys[2] = true;
		}

		if (key == KeyEvent.VK_ESCAPE) {
			if (Game.gameState == State.Game) {
				Game.gameState = State.OptionsGame;
			} else if (Game.gameState == State.OptionsGame) {
				Game.gameState = State.Game;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			keys[0] = false;
		}
		if (key == KeyEvent.VK_A) {
			keys[1] = false;
		}
		if (key == KeyEvent.VK_D) {
			keys[2] = false;
		}
	}

	public boolean[] getKeys() {
		return keys;
	}

	public void setKeys(boolean[] keys) {
		this.keys = keys;
	}
}
