package components;

import java.awt.Frame;
import java.awt.Rectangle;

import pages.GamePanel;
import utils.useRandom;

public class MyThread extends Thread {

  // Attributes
  private Photos pt;
  private Frame frame;
  private GamePanel gamePanel;

  // Speed
  private double getCurrentSpeed = 1.1;

  // Control Path
  double dx = 1.1, dy = 1.1;

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

  private double initialRandomizeSpeed(double currentSpeed) {
    currentSpeed += randomSpeedTrigger();

    // Min speed 1
    if (Math.abs(currentSpeed) < 1) {
      currentSpeed = 1;
    }

    // Limit speed 5
    if (Math.abs(currentSpeed) > 5) {
      currentSpeed = 5;
    }

    return currentSpeed + .25;
  }

  private void checkExceptTarget() {
    for (Photos target : this.gamePanel.getPhotos()) {

      if (target != null) {
        if (target.getStatus()) {
          return;

        }

        if (target != this.pt) {
          Rectangle ref = this.pt.getBounds();
          Rectangle targetRef = target.getBounds();

          // Object Radiant
          // ref.grow(5, 5);
          targetRef.grow(5, 5);

          if (ref.intersects(targetRef)) {
            // คำณวนแนวอุกาบาตที่ทับกัน
            int overAnX = ((ref.width + targetRef.width) / 2)
                - Math.abs(this.pt.getX() - target.getX());

            int overAnY = ((ref.height + targetRef.height) / 2)
                - Math.abs(this.pt.getY() - target.getY());

            // Horizontal
            if (overAnX < overAnY) {
              if (this.pt.getX() > target.getX()) {
                // Move right
                this.pt.setLocation(this.pt.getX() + overAnX, this.pt.getY());
                target.setLocation(target.getX() - overAnX, target.getY());
                this.dx = Math.abs(this.dx);

              } else {
                // Move left
                this.pt.setLocation(this.pt.getX() - overAnX, this.pt.getY());
                target.setLocation(target.getX() + overAnX, target.getY());
                this.dx = -Math.abs(this.dx);

              }

            }
            // Vertical
            else {
              if (this.pt.getY() > target.getY()) {
                // !Move down
                this.pt.setLocation(this.pt.getX(), this.pt.getY() + overAnY);
                target.setLocation(target.getX(), target.getY() - overAnY);
                this.dy = Math.abs(this.dy);

              } else {
                // !Move up
                this.pt.setLocation(this.pt.getX(), this.pt.getY() - overAnY);
                target.setLocation(target.getX(), target.getY() + overAnY);
                this.dy = -Math.abs(this.dy);

              }
            }

            // Update speed
            double angle = Math.atan2(this.pt.getY() - target.getY(), this.pt.getX() - target.getX());
            this.dx = (Math.cos(angle) * initialRandomizeSpeed(getCurrentSpeed));
            this.dy = (Math.sin(angle) * initialRandomizeSpeed(getCurrentSpeed));

            // this.dx += (Math.random() * 3);
            // this.dy += (Math.random() * 3);

            System.out.println(
                String.format("Update Speed:\n= dx > %f\n= dy> %f\n------------------------------\n", this.dx,
                    this.dy));
            break;
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

    int imageWidth = this.pt.getImageSize("width");
    int imageHeight = this.pt.getImageSize("height");

    int borderX = this.frame.getWidth() - imageWidth;
    int borderY = this.frame.getHeight() - imageHeight;

    while (!this.pt.getStatus()) {
      try {
        x += dx;
        y += dy;

        // System.out.println(x);
        // System.out.println(borderX + 7);
        // System.out.println("====================\n");

        // Border Check X
        if (x < 0) {
          dx = Math.abs(dx);
          x = 0; // Left
          // checkExceptTarget();

        }

        if (x > borderX) {
          dx = -Math.abs(dx);
          x = borderX; // Right

        }

        // Border Check Y
        if (y < 0) {
          dy = Math.abs(dy);
          y = 0; // Top
          // checkExceptTarget();

        }

        if (y > borderY) {
          dy = -Math.abs(dy);
          y = borderY; // Bottom

        }

        checkExceptTarget();

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
