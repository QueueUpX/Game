package Audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
//import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffect implements Runnable{
	private boolean running=false;
	private Thread thread;
	private Clip clip;
	//private int volume=0;
	
	public SoundEffect() {
		start();
	}
	
	private void start() {
		if(running) {
			return;
		}
		running=true;
		thread=new Thread();
		thread.start();
	}
	
	public void stop() {
		try {
			thread.join();
			running=false;
		}catch(Exception e) {
			
		}
	}
	
	public void playOnceSound(String fileName,int volume) {
		try {
			File soundFile=new File(fileName);
			AudioInputStream ais=AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format=ais.getFormat();
			DataLine.Info info=new DataLine.Info(Clip.class, format);
			clip=(Clip) AudioSystem.getLine(info);
			clip.open(ais);
			//FloatControl gainControl=(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			//gainControl.setValue(volume);
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

	@Override
	public void run() {
	}

}
