package pages;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import components.MyThread;
import components.Photos;
import utils.useRandom;

interface GamePanelProps {
  public Photos[] getPhotos();
  public MyThread[] getThreads();

}

public class GamePanel extends JPanel implements GamePanelProps {
  private int getNum;
  private int parentWidth;
  private int parentHeight;
  private Frame getframe;

  // Object Photos
  private MyThread threads;
  private MyThread[] threadContainers;
  private Photos[] photo;

  public GamePanel(Frame frame, int num, int width, int height) {
    this.getNum = num;
    this.parentWidth = width;
    this.parentHeight = height;
    this.getframe = frame;

    // Set array size
    this.photo = new Photos[getNum];
    this.threadContainers = new MyThread[getNum];

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

      pt.setBounds(x, y, pt.getImageSize("width"), pt.getImageSize("height"));

      photo[i] = pt;

      if (photo[i] == null) {
        continue;
      }

      this.threads = new MyThread(this.getframe, pt, this, (new useRandom().randomSpeed()));
      this.threadContainers[i] = threads;

      threads.start();

      add(pt);
    }

    this.revalidate();
    this.repaint();

    // threads.start();
  }

  public Photos[] getPhotos() {
    return this.photo;
  }

  public MyThread[] getThreads() {
    return this.threadContainers;
  }



}
