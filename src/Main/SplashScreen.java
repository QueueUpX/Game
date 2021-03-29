package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class SplashScreen extends JWindow {
	private BorderLayout borderLayout;
	private JLabel imgLabel;
	private JPanel southPanel;
	private FlowLayout southFlow;
	private JProgressBar progressBar;
	private ImageIcon imgIcon;
	private Thread thread;

	public SplashScreen(ImageIcon imgIcon) {
		this.imgIcon = imgIcon;
		borderLayout = new BorderLayout();
		imgLabel = new JLabel();
		southPanel = new JPanel();
		southFlow = new FlowLayout();
		progressBar = new JProgressBar();
		try {
			init();
		} catch (Exception e) {

		}
	}
	
	public void start() {
		thread=new Thread();
		thread.start();
	}
	
	public void stop() {
		try {
			setVisible(false);
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void init() throws Exception {
		imgLabel.setIcon(imgIcon);
		getContentPane().setLayout(borderLayout);
		southPanel.setLayout(southFlow);
		// southPanel.setPreferredSize(new Dimension(512,512));
		southPanel.setBackground(Color.black);
		getContentPane().add(imgLabel, BorderLayout.CENTER);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		progressBar.setPreferredSize(new Dimension(256,32));
		southPanel.add(progressBar, null);
		pack();
	}

	public void setMaxProgress(int maxProgress) {
		progressBar.setMaximum(maxProgress);
	}

	public void setProgress(int progress) {
		float percentage = ((float)progress / (float)progressBar.getMaximum()) * 100;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				progressBar.setValue(progress);
				progressBar.setString("Loading " + percentage + "%");
				if(progressBar.getPercentComplete()==1.0) {
					stop();
					//System.out.println("I should stop");
				}
			}

		});
	}
	
	public double getProgress() {
		return progressBar.getPercentComplete();
	}
}
