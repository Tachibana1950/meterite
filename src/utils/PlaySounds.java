package utils;

import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

interface PlaySoundProps {
    public int soundLength(String path);
}

public class PlaySounds implements PlaySoundProps {

    public PlaySounds() {
    }

    public PlaySounds(String path) {
        playSound(path, -25.0f); // Default volume
    }

    public PlaySounds(String path, float volume) {
        playSound(path, volume);
    }

    private void playSound(String path, float volume) {
        new Thread(() -> {
            try (InputStream audioSrc = getClass().getClassLoader().getResourceAsStream("resource/audio/" + path)) {
                if (audioSrc == null) {
                    System.out.println("Audio file not found!");
                    return;
                }

                AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioSrc);
                AudioFormat format = inputStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);

                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(inputStream);

                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(volume);

                clip.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public int soundLength(String path) {
        try (InputStream audioSrc = getClass().getClassLoader().getResourceAsStream("resource/audio/" + path)) {
            if (audioSrc == null) {
                System.out.println("Audio file not found!");
                return 0;
            }

            AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioSrc);
            AudioFormat format = inputStream.getFormat();
            long frames = inputStream.getFrameLength();
            float frameRate = format.getFrameRate();

            // Calculate duration in seconds
            return (int) Math.floor(frames / frameRate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
