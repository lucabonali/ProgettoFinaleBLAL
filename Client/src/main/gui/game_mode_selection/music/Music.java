package main.gui.game_mode_selection.music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Andrea
 * @author Luca
 */
public class Music {
    public void play(String resource) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File claps = new File(resource);
        Clip clip = AudioSystem.getClip();
        AudioInputStream audio = AudioSystem.getAudioInputStream(claps);
        clip.open(audio);
        clip.start();

    }
}
