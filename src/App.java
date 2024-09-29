import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.Timer;

import pages.Frame;
import utils.PlaySounds;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        // // จำนวนอุกาบาต
        System.out.print("Input Num : ");
        int num = scan.nextInt();

        // //เวลาความเร็ว
        // System.out.print("Input Sec : ");
        // float sec = scan.nextFloat();

        Frame frame = new Frame(num, 1);
        frame.setVisible(true);

        new PlaySounds("background.wav");

    }
}
