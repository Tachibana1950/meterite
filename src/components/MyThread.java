package components;

import java.awt.Frame;

public class MyThread extends Thread {

  // Atribute
  private Photos pt;
  private Frame frame;

  // Control Path
  int dx = 1, dy = 1;

  public MyThread(
      Frame getFrame,
      Photos getPt) {
    this.pt = getPt;
    this.frame = getFrame;

  }

  @Override
  public void run() {
    super.run();

    int x = this.pt.getX();
    int y = this.pt.getY();

    int borderX = this.frame.getWidth() - this.pt.getImageSize("width");
    int borderY = this.frame.getHeight() - this.pt.getImageSize("heiht");

    while (true) {

      try {
        x = x + dx;
        y = y + dy;

        System.out.println("X: " + x);
        System.out.println("Y: " + y);
        // System.out.println("Border X: " + borderX);
        System.out.println("===============");

        if (x + (int) (this.pt.getImageSize("height") / 2) - 7 > borderX || x < 0) {
          System.out.println("Out Of Frame X: " + x);
          // x = x + -dx;
          dx = -dx;
          // System.exit(-1);


        }

        if (y + this.pt.getImageSize("height") > borderY + (int) (this.pt.getImageSize("height") / 2) - 7 || y < 0) {
          System.out.println("Out Of Frame Y: " + y);
          // y = -dy;
          dy = -dy;
          // System.exit(-1);

        }

        this.pt.setBounds(x, y, this.pt.getWidth(), this.pt.getHeight());
        this.pt.repaint();
        // this.frame.repaint();

        // System.out.println(x);
        // System.out.println(y);

        Thread.sleep(10);
      } catch (Exception e) {
        // TODO: handle exception
      }

    }

  }

}