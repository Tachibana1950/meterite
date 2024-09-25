package pages;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import components.MyThread;
import components.Photos;

public class GamePanel extends JPanel {
  private int getNum;
  private int parentWidth;
  private int parentHeight;
  private Frame getframe;
  private MyThread threads;

  public GamePanel(Frame frame, int num, int width, int height) {
    this.getNum = num;
    this.parentWidth = width;
    this.parentHeight = height;
    this.getframe = frame;

    setLayout(null);
    setOpaque(false);
    setSize(new Dimension(width, height));

    // Container
    // 1. สร้าง pt ตามจำนวนที่ต้องการโดยใช้ Loop
    // 2. สร้าง Panel เก็บ pt
    // 3. Add Panel เข้าสุ่ Frame

    // !!!
    for (int i = 0; i < this.getNum; i++) {
      Photos pt = new Photos();

      int x = (int) (Math.random() * (this.parentWidth - pt.getImageSize("width")));
      int y = (int) (Math.random() * (this.parentHeight - pt.getImageSize("height")));

      // System.out.println("X Bounds: " + x);
      // System.out.println("Y Bounds: " + y);
      // System.out.println();

      // Set position
      pt.setBounds(x, y, pt.getImageSize("width"), pt.getImageSize("height"));

      this.threads = new MyThread(this.getframe, pt);
      threads.start();

      add(pt);
    }

    this.revalidate();
    this.repaint();

    // threads.start();
  }
}
