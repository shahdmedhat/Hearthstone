package sounds;
// Java program to play an Audio 
// file using Clip Object 
import java.io.File; 
import java.io.IOException; 
  
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
  
public class Sound{
	
    Clip clip;       
    AudioInputStream audioInputStream; 
    
	public Sound(String filePath) throws UnsupportedAudioFileException,IOException, LineUnavailableException{  
        
        audioInputStream =  AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile()); 
        clip = AudioSystem.getClip(); 
          
        clip.open(audioInputStream); 
        clip.start();
        //clip.loop(Clip.LOOP_CONTINUOUSLY); 
    } 
	
	public void stop() {
		clip.stop();
		clip.close();
	}

	public Clip getClip() {
		return clip;
	}
	
	
}