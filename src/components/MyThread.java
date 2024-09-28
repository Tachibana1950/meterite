package components;

import java.awt.Frame;

import pages.GamePanel;
import utils.useRandom;

public class MyThread extends Thread {

  // Attributes
  private Photos pt;
  private Frame frame;
  private GamePanel gamePanel;

  // Speed
  private int getCurrentSpeed = 1;

  // Control Path
  int dx = 1, dy = 1;

  public MyThread(Frame getFrame, Photos getPt, GamePanel getGamePanel, int speed) {
    this.pt = getPt;
    this.frame = getFrame;
    this.gamePanel = getGamePanel;
    this.getCurrentSpeed = speed;

    this.randomDefaultSpeed();

  }

  // Speed Management
  private int randomSpeedTrigger() {
    return new useRandom().randomSpeed();

  }

  private void randomDefaultSpeed() {
    double flag = Math.random();

    if (flag > 0.5) {
      this.dx = this.getCurrentSpeed;

    } else {
      this.dy = this.getCurrentSpeed;

    }

  }

  private int initialRandomizeSpeed(int currentSpeed) {
    // System.out.println("Current Speed: " + currentSpeed);

    currentSpeed += randomSpeedTrigger();

    // Min speed 1
    if (Math.abs(currentSpeed) < 1) {
      currentSpeed = 1;
    }

    // Limit speed 5
    if (Math.abs(currentSpeed) > 5) {
      currentSpeed = 5;
    }

    return currentSpeed;
  }

  private void checkExpecpt() {
    for (int i = 0; i < this.gamePanel.getphoto().length; i++) {
      Photos targer = this.gamePanel.getphoto()[i];
      if (targer != null && targer != this.pt) {
        if (targer.getBounds().intersects(this.pt.getBounds())) {
          this.dx = -dx;
          this.dy = -dy;

          targer.setDx(-targer.getDx());
          targer.setDy(-targer.getDy());

          try {
            Thread.sleep(200);

          } catch (Exception e) {
            e.printStackTrace();

          }
        }
      }
    }
  }

  @Override
  public void run() {
    super.run();

    int x = this.pt.getX();
    int y = this.pt.getY();

    int borderX = this.frame.getWidth() - this.pt.getImageSize("width");
    int borderY = this.frame.getHeight() - this.pt.getImageSize("height");

    while (true) {
      try {
        x += dx;
        y += dy;

        // Border Collision Check
        if (x + (this.pt.getImageSize("width") / 2) - 7 > borderX || x < 0) {
          dx = initialRandomizeSpeed(dx);
          dx = -dx;

          if (x < 0) {
            x = 0;
          } else if (x + (this.pt.getImageSize("width") / 2) - 7 > borderX) {
            x = borderX - (this.pt.getImageSize("width") / 2) - 7;
          }
        }

        if (y + this.pt.getImageSize("height") > borderY || y < 0) {
          dy = initialRandomizeSpeed(dy);
          dy = -dy;

          if (y < 0) {
            y = 0;
          } else if (y + this.pt.getImageSize("height") > borderY) {
            y = borderY - this.pt.getImageSize("height");
          }
        }

        // Check for collisions with other photos
        checkExpecpt();

        // Update position
        this.pt.setBounds(x, y, this.pt.getWidth(), this.pt.getHeight());
        this.pt.repaint();

        Thread.sleep(10);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
