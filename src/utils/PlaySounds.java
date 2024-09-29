package utils;

import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class PlaySounds {
    public PlaySounds(String path) {
        try (InputStream audioSrc = getClass().getClassLoader().getResourceAsStream("resource/audio/" + path)) {
            if (audioSrc == null) {
                System.out.println("Audio file not found!");
                return;
            }

            AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioSrc);
            AudioFormat format = inputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            // System.out.println("Info: " + info);

            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(inputStream);

            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // -80.0f -> 6.0f
            float volume = -25.0f;
            volumeControl.setValue(volume);

            clip.start();

            // Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public PlaySounds(String path, float volume) {
        try (InputStream audioSrc = getClass().getClassLoader().getResourceAsStream("resource/audio/" + path)) {
            if (audioSrc == null) {
                System.out.println("Audio file not found!");
                return;
            }

            AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioSrc);
            AudioFormat format = inputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            // System.out.println("Info: " + info);

            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(inputStream);

            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // -80.0f -> 6.0f
            volumeControl.setValue(volume);

            clip.start();

            // Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
