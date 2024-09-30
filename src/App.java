import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import pages.Frame;
import utils.PlaySounds;

public class App {
    public static void main(String[] args) throws Exception {
        int num = 1;

        while (true) {
            JTextField inputField = new JTextField();

            Object[] message = {
                    "Input number of meteorites 🌠:",
                    inputField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Meteorite Input", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                System.out.println("Cancel Work!.");
                System.exit(-1);
                break;
            }

            String input = inputField.getText();

            try {
                num = Integer.parseInt(input);
                if (num <= 0) {
                    System.out.println("Please enter number > 0!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please input number only!");

            }
        }

        Frame frame = new Frame(num, 1);
        frame.setVisible(true);

        PlaySounds getSound = new PlaySounds();
        getSound.play("background.wav", -20.0f);

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
