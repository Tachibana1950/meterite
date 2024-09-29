package pages;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import javax.swing.JPanel;

import components.MyThread;
import components.Photos;
import utils.useRandom;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

interface GamePanelProps {
  public List<Photos> getPhotos();

  public List<MyThread> getThreads();
}

public class GamePanel extends JPanel implements GamePanelProps {
  private int getNum;
  private int parentWidth;
  private int parentHeight;
  private Frame getframe;

  // Container
  private List<MyThread> threadContainers;
  private List<Photos> photos;

  public GamePanel(Frame frame, int num, int width, int height) {
    this.getNum = num;
    this.parentWidth = width;
    this.parentHeight = height;
    this.getframe = frame;

    this.photos = new ArrayList<>();
    this.threadContainers = new ArrayList<>();

    setLayout(null);
    setOpaque(false);
    setSize(new Dimension(width, height));

    for (int i = 0; i < this.getNum; i++) {
      Photos pt = new Photos(this);

      int x = (int) (Math.random() * (this.parentWidth - pt.getImageSize("width")));
      int y = (int) (Math.random() * (this.parentHeight - pt.getImageSize("height")));

      pt.setBounds(x, y, pt.getImageSize("width"), pt.getImageSize("height"));

      photos.add(pt);

      MyThread thread = new MyThread(this.getframe, pt, this, (new useRandom().randomSpeed()));
      threadContainers.add(thread);

      thread.start();

      add(pt);
    }

    this.revalidate();
    this.repaint();
  }

  public List<Photos> getPhotos() {
    return this.photos;
  }

  public List<MyThread> getThreads() {
    return this.threadContainers;
  }

  public void removePhoto(Photos photo) {
    photos.remove(photo);
    remove(photo);
    repaint();
  }
}