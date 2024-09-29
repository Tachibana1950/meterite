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
  double dx = 1, dy = 1;

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

  private void checkCollisions() {
    for (Photos target : this.gamePanel.getPhotos()) {
      if (target != null && target != this.pt) {
        if (this.pt.getBounds().intersects(target.getBounds())) {
          // Calculate the overlap distance
          int overlapX = ((this.pt.getBounds().width + target.getBounds().width) / 2)
              - Math.abs(this.pt.getX() - target.getX());
          int overlapY = ((this.pt.getBounds().height + target.getBounds().height) / 2)
              - Math.abs(this.pt.getY() - target.getY());

          if (overlapX < overlapY) {
            // Horizontal separation
            if (this.pt.getX() > target.getX()) {
              this.pt.setLocation(this.pt.getX() + overlapX, this.pt.getY());
              target.setLocation(target.getX() - overlapX, target.getY());
              this.dx = Math.abs(this.dx);

            } else {
              this.pt.setLocation(this.pt.getX() - overlapX, this.pt.getY());
              target.setLocation(target.getX() + overlapX, target.getY());
              this.dx = -Math.abs(this.dx);

            }
          } else {
            // Vertical separation
            if (this.pt.getY() > target.getY()) {
              this.pt.setLocation(this.pt.getX(), this.pt.getY() + overlapY);
              target.setLocation(target.getX(), target.getY() - overlapY);
              this.dy = Math.abs(this.dy);

            } else {
              this.pt.setLocation(this.pt.getX(), this.pt.getY() - overlapY);
              target.setLocation(target.getX(), target.getY() + overlapY);
              this.dy = -Math.abs(this.dy);

            }
          }

          // Update velocities
          double angle = Math.atan2(this.pt.getY() - target.getY(), this.pt.getX() - target.getX());
          this.dx = Math.cos(angle) * getCurrentSpeed;
          this.dy = Math.sin(angle) * getCurrentSpeed;

          // Optional randomness
          this.dx += (Math.random() - 0.25);
          this.dy += (Math.random() - 0.25);

          // Ensure that targets also reverse direction
          target.setDx(-this.dx);
          target.setDy(-this.dy);

          splitBetweenObject(this.pt, target);
          break;
        }
      }
    }
  }

  private void splitBetweenObject(Photos a, Photos b) {
    double separationDistance = 5;
    double dx = b.getX() - a.getX();
    double dy = b.getY() - a.getY();

    double distance = Math.sqrt(dx * dx + dy * dy);

    int borderX = this.frame.getWidth() - this.pt.getImageSize("width");
    int borderY = this.frame.getHeight() - this.pt.getImageSize("height");

    if (a.getX() < 0) {
      a.setLocation(0, a.getY());
    } else if (a.getX() + a.getWidth() > borderX) {
      a.setLocation(borderX - a.getWidth(), a.getY());
    }

    if (a.getY() < 0) {
      a.setLocation(a.getX(), 0);
    } else if (a.getY() + a.getHeight() > borderY) {
      a.setLocation(a.getX(), borderY - a.getHeight());
    }

    if (distance < separationDistance) {
      // Move Object
      double moveX = (separationDistance - distance) * (dx / distance);
      double moveY = (separationDistance - distance) * (dy / distance);

      a.setLocation((int) (a.getX() - moveX), (int) (a.getY() - moveY));
      b.setLocation((int) (b.getX() + moveX), (int) (b.getY() + moveY));
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
            dx = -Math.abs(dx);
            x = 0;

          }

          if (x + (this.pt.getImageSize("width") / 2) - 7 > borderX) {
            int borderRadiantX = borderX - (this.pt.getImageSize("width") / 2) - 7;
            x = borderRadiantX;

          }

        }

        if (y + this.pt.getImageSize("height") > borderY || y < 0) {
          dy = initialRandomizeSpeed(dy);
          dy = -dy;

          if (y < 0) {
            dy = -Math.abs(dy);
            y = 0;

          }

          if (y + this.pt.getImageSize("height") > borderY) {
            int borderRadiantY = borderY - this.pt.getImageSize("height");
            y = borderRadiantY;

          }

        }

        // Check for collisions with other photos
        checkCollisions();

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