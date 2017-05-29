package main.gui.music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Andrea
 * @author Luca
 */
public class Music {

    private static final String PATH = "Client/src/main/gui/music/";
    private File claps;
    private Clip clip;
    private AudioInputStream audio;


    public String getPath(){
        return PATH;
    }

   public void play(String resource) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
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
