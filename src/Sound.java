
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class Sound {
BasicController playerController;
    public Sound() {
        BasicPlayer myMusicPlayer=new BasicPlayer();
 playerController =(BasicController)myMusicPlayer;
String filePath="C:\\Users\\1\\Downloads\\Vehicle - OGA - Siberian Intro.wav";
File file=new File(filePath);
try
{ 
    playerController.open(file);
    playerController.play();

}catch(Exception ex){}

    }
    public void pause(){
    try {
        playerController.pause();
    } catch (BasicPlayerException ex) {
        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    public void resume(){
    try {
        playerController.resume();
    } catch (BasicPlayerException ex) {
        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

  

}
