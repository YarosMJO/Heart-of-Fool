package Audio;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;

public class AudioPlayer {
    private Clip clip;
    private int amountOfFrames;

    public AudioPlayer(String s) {
        try {

            InputStream audioSrc = getClass().getResourceAsStream(s);
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    true
            );

            AudioInputStream dais = AudioSystem.getAudioInputStream(
                    decodeFormat
                    , audioStream);
            clip = AudioSystem.getClip();
            clip.open(dais);

        } catch (LineUnavailableException e) {
            System.out.println("1");
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("2");
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            System.out.println("3");
            e.printStackTrace();
        }
    }

        public void play() {
        if (clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();

    }

    public void pause() {
        setAmount(clip.getFramePosition());
        stop();

    }

    public void resume() {
        clip.setFramePosition(amountOfFrames);
        clip.start();

    }

    public void stop() {
        if (clip.isRunning()) clip.stop();
    }

    public void close() {
        clip.stop();
        clip.close();
    }

    public int getAmount() {
        return amountOfFrames;
    }

    public void setAmount(int amountOfFrames) {
        this.amountOfFrames = amountOfFrames;
    }

}
