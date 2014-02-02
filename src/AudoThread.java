
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;

public class AudoThread implements Runnable{ //Thread-ul care face reda muzica;
    @Override
    public void run() {
        try {
            Player audioPlayer = new Player(getClass().getClassLoader().getResourceAsStream("res/sound.mp3"));  //libraria jl1.0.1.rar;
            audioPlayer.play();
        } catch (JavaLayerException e) {
            JOptionPane.showMessageDialog(null, "error sound file");
            e.printStackTrace();
        }

    }
}
