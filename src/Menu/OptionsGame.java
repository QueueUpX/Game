package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import Audio.MusicPlayer;
import Framework.State;
import Main.Game;

public class OptionsGame extends Options {

	public OptionsGame(Game game) {
		super(game);
	}

	@Override
	protected void init() {
		options.add(new Button("Resume", 280 + 0 * 80, new Font("Arial", Font.PLAIN, 32),
				new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		if (music > 0) {
			options.add(new Button("Music OFF", 280 + 1 * 80, new Font("Arial", Font.PLAIN, 32),
					new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		} else {
			options.add(new Button("Music ON", 280 + 1 * 80, new Font("Arial", Font.PLAIN, 32),
					new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		}
		if (effect < 0) {
			options.add(new Button("Sound effects OFF", 280 + 2 * 80, new Font("Arial", Font.PLAIN, 32),
					new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		} else {
			options.add(new Button("Sound effects ON", 280 + 2 * 80, new Font("Arial", Font.PLAIN, 32),
					new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		}
		options.add(new Button("Main Menu", 280 + 3 * 80, new Font("Arial", Font.PLAIN, 32),
				new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
	}

	@Override
	public void tick() {
		for (int i = 0; i < options.size(); i++) {
			if (options.get(i).getBounds().intersects(new Rectangle(x, y, 1, 1))) {
				currentSelection = i;
				if (clicked) {
					select();
					if (i == 1) {
						music *= -1;

					} else if (i == 2) {
						effect *= -1;

					}
				}
			}
		}
		clicked = false;
		if (music > 0) {
			options.set(1, new Button("Music OFF", 280 + 1 * 80, new Font("Arial", Font.PLAIN, 32),
					new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		} else {
			options.set(1, new Button("Music ON", 280 + 1 * 80, new Font("Arial", Font.PLAIN, 32),
					new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		}
		if (effect < 0) {
			options.set(2, new Button("Sound effects OFF", 280 + 2 * 80, new Font("Arial", Font.PLAIN, 32),
					new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		} else {
			options.set(2, new Button("Sound effects ON", 280 + 2 * 80, new Font("Arial", Font.PLAIN, 32),
					new Font("Arial", Font.BOLD, 38), Color.white, Color.yellow));
		}
	}

	@Override
	protected void select() {
		switch (currentSelection) {
		case 0:
			Game.gameState = State.Game;
			break;
		case 1:
			if (music < 0) {
				Game.background.stop();
			} else if (music > 0) {
				Game.background = new MusicPlayer("inGameMusic");
				Game.background.start();
			}
			break;
		case 2:

			break;
		case 3:
			Game.gameState = State.Menu;
			if (music < 0) {
				Game.background.stop();
				Game.background = new MusicPlayer("MenuMusic");
				Game.background.start();
			} else if (music > 0) {
				Game.background.stop();
			}
			break;
		}
	}
}
