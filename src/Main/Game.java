package Main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Audio.MusicPlayer;
import Audio.SoundEffect;
import Framework.GameObject;
import Framework.KeyInput;
import Framework.MouseInput;
import Framework.ObjectId;
import Framework.State;
import Menu.End;
import Menu.Fonts;
import Menu.Menu;
import Menu.Options;
import Menu.OptionsGame;
import Menu.Perks;
import Menu.Upgrade;
import Objects.Block;
import Objects.EndGame;
import Objects.Enemy;
import Objects.PlatformHorVer;
import Objects.PlatformHorizontal;
import Objects.PlatformVertical;
import Objects.Player;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {
	private boolean running = false;
	public static int WIDTH, HEIGHT;

	// public static int EnemyCount=0;
	private Thread thread;
	private Handler handler;
	private Camera cam;
	private Menu menu;
	private Options options;
	private Upgrade upgrade;
	private Restart restart;
	private Perks perks;
	private End end;
	private OptionsGame optionsGame;
	public static MusicPlayer background;
	public BufferedImage level = null;
	private BufferedImage backGround;
	public BufferedImageLoader loader = new BufferedImageLoader();
	private KeyInput input;
	private MouseInput minput;
	private SplashScreenDriver screen;
	public static State gameState = State.Menu;
	private int lastFrame = 0;
	public static SoundEffect effect;
	public static ArrayList<GameObject> map;

	private void init() {
		WIDTH = getWidth();
		HEIGHT = getHeight();

		level = loader.loadImage("/LEVEL1.png"); // loading level
		backGround = loader.loadImage("/InGameBG.png");
		handler = new Handler();

		menu = new Menu(this);
		upgrade = new Upgrade(this);
		perks = new Perks(this);
		options = new Options(this);
		optionsGame = new OptionsGame(this);
		end = new End(this, handler);
		restart = new Restart(this, handler);
		cam = new Camera(0, 0, handler);
		minput = new MouseInput(handler, cam);
		input = new KeyInput();
		minput.findPlayer();
		this.addKeyListener(input);
		this.addMouseListener(minput);

		LoadImageLevel(level);
		effect = new SoundEffect();

		// handler.addObject(new Player(32, 32, handler, ObjectId.Player));
		// handler.createLevel();
	}

	public void start() {
		if (running) {
			return;
		}

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		if (!running) {
			return;
		}

		running = false;
		try {
			thread.join();
			effect.stop();
			background.stop();
			// System.out.println("Thread in game stopped!");
		} catch (InterruptedException e) {
			e.printStackTrace();
			// System.out.println("Thread failed to stop!");
		} catch (NullPointerException e) {
			// System.out.println("Thread already stopped!");
		}
	}

	@Override
	public void run() {
		// System.out.println("Thread in game started!");

		this.requestFocus();
		init();

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		// int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				// updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames + " TICKS: " + updates);
				lastFrame = frames;
				frames = 0;
				// updates = 0;
			}
		}
	}

	private void tick() {
		if (gameState == State.Menu) {
			menu.tick();
		} else if (gameState == State.Game) {
			handler.tick();
			cam.tick();
		} else if (gameState == State.Options) {
			options.tick();
		} else if (gameState == State.OptionsGame) {
			optionsGame.tick();
		} else if (gameState == State.Upgrade) {
			upgrade.tick();
		} else if (gameState == State.Perks) {
			perks.tick();
		} else if (gameState == State.End) {
			end.tick();
		} else if (gameState == State.Restart) {
			restart.tick();
		}
	}

	private void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3); // number of frames rendered after the first render
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;

		// g2d.setColor(Color.black);
		// g2d.fillRect(0, 0, getWidth(), getHeight());
		//////////////////////////////////

		if (gameState == State.Menu) {
			// g2d.drawImage(menuBackground,0,0,null);
			menu.render(g2d);
		} else if (gameState == State.Game) {
			g2d.drawImage(backGround, 0, 0, null);
			g2d.translate(-cam.getX(), -cam.getY()); // begin cam
			handler.render(g2d);
			g2d.translate(cam.getX(), cam.getY()); // end cam
			Fonts.drawString(g2d, new Font("Arial", Font.PLAIN, 24), Color.cyan, "Enemies=" + Enemy.EnemyCount,
					Game.WIDTH - 280, 24);
		} else if (gameState == State.Options) {
			options.render(g2d);
		} else if (gameState == State.OptionsGame) {
			optionsGame.render(g2d);
		} else if (gameState == State.Upgrade) {
			upgrade.render(g2d);
		} else if (gameState == State.Perks) {
			perks.render(g2d);
		} else if (gameState == State.End) {
			end.render(g2d);
		} else if (gameState == State.Restart) {
			restart.render(g2d);
		}

		Fonts.drawString(g2d, new Font("Arial", Font.PLAIN, 24), Color.cyan, "FPS=" + lastFrame, Game.WIDTH - 110, 24);
		//////////////////////////////////
		g.dispose();
		bs.show();
	}

	private boolean LoadImageLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		int pixel = 0;
		int fail = 0;
		screen = new SplashScreenDriver(new ImageIcon("./level/loadingScreen.png"));

		// System.out.println("Width=" + w + " Height=" + h);

		for (int xx = 0; xx < h; xx++) {
			for (int yy = 0; yy < w; yy++) {
				try {
					pixel = image.getRGB(xx, yy);
				} catch (Exception e) {
					fail++;
				}
				int red = (pixel >> 16) & 0xff, green = (pixel >> 8) & 0xff, blue = (pixel) & 0xff;
				if (red == 255 && green == 255 & blue == 255) { // white block
					handler.addObject(new Block(xx * 32, yy * 32, 32, 32, ObjectId.Block, handler));
				}
				if (red == 0 && green == 0 & blue == 255) {
					handler.addPlayer(new Player(xx * 32, yy * 32, 50, 100, handler, ObjectId.Player, input));
				}
				if (red == 195 && green == 195 & blue == 195) {
					handler.addObject(new PlatformHorizontal(xx * 32, yy * 32, 32, 32, 1f, -80, 80,
							ObjectId.PlatformHorizontal, handler));
				}
				if (red == 100 && green == 100 & blue == 100) {
					handler.addObject(new PlatformHorVer(xx * 32, yy * 32, 32, 32, 1f, -1f, -80, 80, -80, 80,
							ObjectId.PlatformHorVer, handler));
				}
				if (red == 88 && green == 88 & blue == 88) {
					handler.addObject(new PlatformVertical(xx * 32, yy * 32, 32, 32, 1f, -1000, 160,
							ObjectId.PlatformVertical, handler));
				}
				if (red == 255 && green == 0 & blue == 0) {
					handler.addEnemy(new Enemy(xx * 32, yy * 32, 64, 64, handler, ObjectId.Enemy));
				}
				if (red == 185 && green == 122 & blue == 86) {
					handler.addObject(new EndGame(xx * 32, yy * 32, 64, 64, ObjectId.EndGame));

				}
			}
			screen.getScreen().setProgress(xx);
			// System.out.println(xx);
		}
		map=new ArrayList<GameObject>(handler.object);
		background = new MusicPlayer("MenuMusic");
		background.start();
		System.out.println(fail);
		return true;
	}

	public static void main(String args[]) {
		// new SplashScreenDriver(new ImageIcon("./level/testPhoto.jpeg"));
		new Window(1600, 900, "MilkyWayExplorer_Alpha", new Game());
	}
}
