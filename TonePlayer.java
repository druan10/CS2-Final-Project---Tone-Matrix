/**
 * This Class is used to play sounds in a separate thread each time to prevent the program from freezing
 */
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class TonePlayer implements Runnable{
private File sound;


	public TonePlayer(File Sound){
		sound = Sound;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Loads and plays the wave file
		try{
			Clip clip =AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
			Thread.sleep(clip.getMicrosecondLength()/1000);
		}catch(Exception e){
			
		}

	}
}
