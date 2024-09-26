import java.util.Scanner;

import pages.Frame;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        
        // // จำนวนอุกาบาต
        // System.out.print("Input Num : ");
        // int num = scan.nextInt();

        // //เวลาความเร็ว
        // System.out.print("Input Sec : ");
        // float sec = scan.nextFloat();

        Frame frame = new Frame(3, 1);
        frame.setVisible(true);

    }
}
