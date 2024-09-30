package components;

import java.awt.Frame;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;

import pages.GamePanel;
import utils.useRandom;

public class MyThread extends Thread {
  private Photos pt;
  private Frame frame;
  private GamePanel gamePanel;

  // Speed
  private double MIN_SPEED = 1.1;
  private double PROGRESSIVE_SPEED = 0.25;

  private double getCurrentSpeed = MIN_SPEED;
  private double dx = MIN_SPEED, dy = MIN_SPEED;

  // Position at
  private int x, y;

  public MyThread(Frame getFrame, Photos getPt, GamePanel getGamePanel, int speed) {
    this.pt = getPt;
    this.frame = getFrame;
    this.gamePanel = getGamePanel;
    this.getCurrentSpeed = speed;

    this.randomDefaultSpeed();
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
    currentSpeed += new useRandom().randomSpeed();

    if (Math.abs(currentSpeed) < 1) {
      currentSpeed = 1;
    }
    if (Math.abs(currentSpeed) > 5) {
      currentSpeed = 4.5;
    }

    // System.out.println("Current Speed: " + currentSpeed);

    return currentSpeed + this.PROGRESSIVE_SPEED;
  }

  @Override
  public void run() {
    super.run();

    this.x = this.pt.getX();
    this.y = this.pt.getY();

    while (!this.pt.getIsBomb()) {
      try {
        movePhoto();
        checkOutOfFrame();
        checkCollisions();

        this.pt.setBounds(
            this.x,
            this.y,
            this.pt.getWidth(),
            this.pt.getHeight());

        this.pt.repaint();

        Thread.sleep(10);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    this.interrupt();
  }

  private void movePhoto() {
    x += dx + this.PROGRESSIVE_SPEED;
    y += dy + this.PROGRESSIVE_SPEED;
  }

  private void checkOutOfFrame() {
    int imageWidth = this.pt.getImageSize("width");
    int imageHeight = this.pt.getImageSize("height");

    int borderX = this.frame.getWidth() - imageWidth;
    int borderY = this.frame.getHeight() - imageHeight;

    boolean outOfBorderX = false;
    boolean outOfBorderY = false;

    if (x < 0) {
      dx = Math.abs(dx);
      x = 0;
      outOfBorderX = true;
    } else if (x > borderX) {
      dx = -Math.abs(dx);
      x = borderX;
      outOfBorderX = true;
    }

    if (y < 0) {
      dy = Math.abs(dy);
      y = 0;
      outOfBorderY = true;
    } else if (y > borderY) {
      dy = -Math.abs(dy);
      y = borderY;
      outOfBorderY = true;
    }

    if (outOfBorderX || outOfBorderY) {
      this.dx = zeroCorrection(initialRandomizeSpeed(this.dx));
      this.dy = zeroCorrection(initialRandomizeSpeed(this.dy));

      // if (outOfBorderX) {
      // this.dx = zeroCorrection(initialRandomizeSpeed(this.dx));

      // } else {
      // this.dy = zeroCorrection(initialRandomizeSpeed(this.dy));

      // }

      // movement
      if (outOfBorderX && outOfBorderY) {
        double useSpeed = zeroCorrection(Math.random() > 0.5 ? this.getCurrentSpeed : -this.getCurrentSpeed);
        this.dx = useSpeed;
        this.dy = useSpeed;
      }
    }
  }

  private double zeroCorrection(double speed) {
    if (Math.abs(speed) < 1) {
      speed = (speed < 0) ? -1.5 : 1.5;
    }

    return speed;
  }

  private void checkCollisions() {
    List<Photos> photosContainerRef = this.gamePanel.getPhotos();
    Iterator<Photos> iteratorContainerOfPhotos = photosContainerRef.iterator();

    while (iteratorContainerOfPhotos.hasNext()) {
      Photos target = iteratorContainerOfPhotos.next();

      if (target == null || target == this.pt) {
        return;

      }

      if (!target.getIsBomb()) {
        Rectangle ref = this.pt.getBounds();
        Rectangle targetRef = target.getBounds();

        targetRef.grow(5, 5);

        if (ref.intersects(targetRef)) {
          handleCollision(target);

          break;
        }
      }
    }
  }

  private void handleCollision(Photos target) {
    Rectangle ref = this.pt.getBounds();
    Rectangle targetRef = target.getBounds();

    int refX = (ref.width + targetRef.width);
    int refY = (ref.height + targetRef.height);

    int overAnX = (refX / 2) - Math.abs(this.pt.getX() - target.getX());
    int overAnY = (refY / 2) - Math.abs(this.pt.getY() - target.getY());

    System.out.println(String.format("ref X: %d\nref Y: %d\nOver X: %d\nOver Y: %d\n**********", refX, refY, overAnX, overAnY));

    if (overAnX < overAnY) {
      horizonTrigger(target, overAnX);
    } else {
      verticalTrigger(target, overAnY);
    }

    updateSpeed(target);
  }

  private void horizonTrigger(Photos target, int overAnX) {
    if (this.pt.getX() > target.getX()) {
      this.x += overAnX;
      target.setLocation(target.getX() - overAnX, target.getY());
      this.dx = (Math.abs(this.dx) + this.PROGRESSIVE_SPEED);
    } else {
      this.x -= overAnX;
      target.setLocation(target.getX() + overAnX, target.getY());
      this.dx = -(Math.abs(this.dx) + this.PROGRESSIVE_SPEED);
    }
  }

  private void verticalTrigger(Photos target, int overAnY) {
    if (this.pt.getY() > target.getY()) {
      this.y += overAnY;
      target.setLocation(target.getX(), target.getY() - overAnY);
      this.dy = (Math.abs(this.dy) + this.PROGRESSIVE_SPEED);
    } else {
      this.y -= overAnY;
      target.setLocation(target.getX(), target.getY() + overAnY);
      this.dy = -(Math.abs(this.dy) + this.PROGRESSIVE_SPEED);
    }
  }

  private void updateSpeed(Photos target) {
    double angle = Math.atan2(this.pt.getY() - target.getY(), this.pt.getX() - target.getX());
    this.dx = zeroCorrection((Math.cos(angle) * initialRandomizeSpeed(getCurrentSpeed)));
    this.dy = zeroCorrection((Math.sin(angle) * initialRandomizeSpeed(getCurrentSpeed)));

    System.out.println(
        String.format("----- Update Speed -----\n= dx > %f\n= dy > %f\n------------------------------\n", this.dx,
            this.dy));
  }
}
