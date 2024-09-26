package components;

import java.awt.Frame;
// import java.awt.Rectangle;

import javax.swing.JPanel;

import pages.GamePanel;

public class MyThread extends Thread {

  // Atribute
  private Photos pt;
  private Frame frame;
  private GamePanel gamePanel;
  // private Photos[] photo;

  // Movement Flag
  private boolean isKnocking = false;

  // Control Path
  int dx = 1, dy = 1;

  public MyThread(Frame getFrame, Photos getPt, GamePanel getGamePanel) {
    this.pt = getPt;
    this.frame = getFrame;
    this.gamePanel = getGamePanel;

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
        if (!isKnocking) {
          x = x + dx;
          y = y + dy;

        } else {
          x = x - dx;
          y = y - dy;

        }

        for (Photos getRef : this.gamePanel.getphoto()) {
          if ((this.pt != getRef) && getRef.getBounds().intersects(this.pt.getBounds())) {
            System.out.println("Hi");
            dx = -dx;
            dy = -dy;

            this.isKnocking = !this.isKnocking;

            break;

          } else {
            System.out.println();

          }

        }

        // System.out.println("walk");
        // if (this.pt.getBounds().intersects(this.photo[1].getBounds())) {
        // System.out.println("Boom");
        // break;
        // }

        // System.out.println("X: " + x);
        // System.out.println("Y: " + y);
        // System.out.println("Border X: " + borderX);
        // System.out.println("===============");

        if (x + (int) (this.pt.getImageSize("height") / 2) - 7 > borderX || x < 0) {
          // System.out.println("Out Of Frame X: " + x);
          // x = x + -dx;
          dx = -dx;
          // System.exit(-1);

        }

        if (y + this.pt.getImageSize("height") > borderY + (int) (this.pt.getImageSize("height") / 2) - 7 || y < 0) {
          // System.out.println("Out Of Frame Y: " + y);
          // y = -dy;
          dy = -dy;
          // System.exit(-1);

        }

        this.pt.setBounds(x, y, this.pt.getWidth(), this.pt.getHeight());
        this.pt.repaint();

        Thread.sleep(5);
      } catch (Exception e) {
        // TODO: handle exception
      }

    }

  }

}