package Audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer implements Runnable{
	private String musicFiles;
	private Clip clip;
	private int volume=0;
	
	Thread thread;
	
	public MusicPlayer(String files) {
		musicFiles="./Audio/"+files+".wav";
	}
	
	private void playSound(String fileName) {
		try {
			File soundFile=new File(fileName);
			AudioInputStream ais=AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format=ais.getFormat();
			DataLine.Info info=new DataLine.Info(Clip.class, format);
			clip=(Clip) AudioSystem.getLine(info);
			clip.open(ais);
			FloatControl gainControl=(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume);
			//clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void playOnceSound(String fileName) {
		try {
			File soundFile=new File(fileName);
			AudioInputStream ais=AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format=ais.getFormat();
			DataLine.Info info=new DataLine.Info(Clip.class, format);
			clip=(Clip) AudioSystem.getLine(info);
			clip.open(ais);
			FloatControl gainControl=(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-30);
			//clip.start();
			//clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		thread=new Thread(this);
		thread.start();
		//System.out.println("Music thread started!");
	}
	
	public void stop() {
		clip.stop();
		try {
			thread.join();
			
			//System.out.println("Music thread stopped!");
		} catch (InterruptedException e) {
			//e.printStackTrace();
			//System.out.println("Music Thread failed to stop!");
		} catch(NullPointerException e) {
			//e.printStackTrace();
			//System.exit(0);
		}
	}
	
	public void pause() {
		clip.close();
	}
	
	public void resume() {
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	@Override
	public void run() {
		playSound(musicFiles);
	}
}
