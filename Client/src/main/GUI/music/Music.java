package main.GUI.music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * @author Andrea
 * @author Luca
 */
public class Music {

    private static final String PATH = "Client/src/main/GUI/music/";
    private File claps;
    private Clip clip;
    private AudioInputStream audio;


    public String getPath(){
        return PATH;
    }

   public void play(String resource){
//        claps = new File(resource);
//        clip = AudioSystem.getClip();
//        audio = AudioSystem.getAudioInputStream(claps);
//        clip.open(audio);
//        clip.start();
    }

    public void stop(){
//        clip.stop();
    }


}
