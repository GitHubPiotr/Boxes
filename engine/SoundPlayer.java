package boxes.engine;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {
    
    private final String path;
    private Clip clip;
    private AudioInputStream ais = null;
    private Line line = null;
    private boolean volume;
    private FloatControl gainControl;
    
    SoundPlayer(String path) {
        this.path = path;
    }

    public void play(boolean isMusic) {
        URL url = getClass().getResource(path);
        Line.Info linfo = new Line.Info(Clip.class);

        try {
            line = AudioSystem.getLine(linfo);
            clip = (Clip)line;
            ais = AudioSystem.getAudioInputStream(url);
            clip.open(ais);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-35.0f);
            clip.start();
            if(isMusic) clip.loop(clip.LOOP_CONTINUOUSLY);

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            ex.getMessage();
        }
    }

    public void setVolume() {
        if(!volume) {
            volume = true;
            gainControl.setValue(-1000);
        }
        else {
            volume = false;
            gainControl.setValue(-35);
        }
        try { Thread.sleep(150); } catch (InterruptedException ex) { ex.getMessage(); }
    }
}
