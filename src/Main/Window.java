package Main;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

//import Audio.MusicPlayer;

public class Window {
	Game game;
	//MusicPlayer background;
	
	public Window(int w, int h, String title, Game game) {
		this.game=game;
		this.game.setPreferredSize(new Dimension(w, h));
		this.game.setMaximumSize(new Dimension(w, h));
		this.game.setMinimumSize(new Dimension(w, h));

		JFrame frame = new JFrame(title);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});
		
		//background=new MusicPlayer("DiscordToday");
		
		start();
	}
	
	private synchronized void start() {
		game.start();
		//background.start();
	}
	
	private synchronized void stop() {
		game.stop();
		//background.stop();
	}
}
