package Main;

import javax.swing.ImageIcon;

public class SplashScreenDriver{
	private SplashScreen screen;
	
	public SplashScreenDriver(ImageIcon imageIcon) {
		screen=new SplashScreen(imageIcon);
		screen.setLocationRelativeTo(null);
		screen.setMaxProgress(2047);
		screen.setVisible(true);
		/*for(int i=0;i<10000;i++) {
			for(int j=0;j<11999;j++) {
				String x="fdsfsd"+(i+j);
			}
			screen.setProgress(i);
		}*/
		screen.start();
		if(screen.getProgress()==1) {
			screen.setVisible(false);
		}
	}
	
	public SplashScreen getScreen() {
		return screen;
	}
}
