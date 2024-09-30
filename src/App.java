import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import pages.Frame;
import utils.PlaySounds;

public class App {
    public static void main(String[] args) throws Exception {
        String input = JOptionPane.showInputDialog("Input mum of meteorite 🌠:");
        int num = Integer.parseInt(input);

        Frame frame = new Frame(num, 1);
        frame.setVisible(true);

        PlaySounds getSound = new PlaySounds();
        getSound.play("background.wav", -40.0f);

        int soundLength = getSound.soundLength("background.wav");
        Timer timer = new Timer(soundLength, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getSound.play("background.wav", -40.0f);
            }
        });

        timer.start();
    }
}
