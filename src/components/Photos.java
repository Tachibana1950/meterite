package components;

import utils.PlaySounds;

import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import pages.GamePanel;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

interface PhotosProps {
  public double getDx();

  public double getDy();

  public void setDx(double _dx);

  public void setDy(double _dy);

  public int getImageSize(String _case);

  public boolean getStatus();

}

public class Photos extends JPanel implements MouseListener, PhotosProps {

  // Image
  private Image show;
  private ImageIcon bomb;

  // Other
  private int photoWidth = 50;
  private int photoHeight = 50;
  private int num = 0;
  private boolean isBomb = false;

  // Collision
  private double dx = 1.1;
  private double dy = 1.1;

  private GamePanel gamePanel;

  public Photos(GamePanel _panel) {
    this.setOpaque(false);
    this.setSize(new Dimension(photoWidth, photoHeight));
    this.setPreferredSize(new Dimension(photoWidth, photoHeight));

    this.gamePanel = _panel;

    // Meteorite picture
    int numpic = (int) (Math.random() * 10) + 1;
    try {
      InputStream is = Photos.class.getClassLoader().getResourceAsStream("resource/picture/" + numpic + ".png");
      this.show = ImageIO.read(is);

      this.bomb = new ImageIcon(Photos.class.getClassLoader().getResource("resource/picture/bomb.gif"));

    } catch (Exception e) {
      System.err.println("Failed to load images");
    }

    // Add event listeners
    if (!isBomb) {

      addMouseListener(this);
      return;

    }
  }

  public double getDx() {
    return this.dx;

  }

  public double getDy() {
    return this.dy;

  }

  public void setDx(double _dx) {
    this.dx = _dx;

  }

  public void setDy(double _dy) {
    this.dy = _dy;

  }

  public int getImageSize(String _case) {
    if (_case.equals("width")) {
      return this.photoWidth;

    }
    return this.photoHeight;
  }

  @Override
  public Rectangle getBounds() {
    return new Rectangle(getX(), getY(), photoWidth, photoHeight);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.setColor(Color.red);

    if ((num < 2) && !this.isBomb) {
      g.drawImage(this.show, 0, 0, this.photoWidth, this.photoHeight, this);

    } else {
      g.drawImage(bomb.getImage(), 0, 0, this.photoWidth, this.photoHeight, this);

    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    num++;

    if (num >= 2) {
      new PlaySounds("pling.wav", -15f);

      Timer timer = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          new PlaySounds("bomb.wav");

          isBomb = true;
          setVisible(false);

          gamePanel.removePhoto(Photos.this);
          removeMouseListener(Photos.this);
        }
      });

      timer.setRepeats(false);
      timer.start();

      return;
    }

    new PlaySounds("laser-gun.wav", -20.0f);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  public boolean getStatus() {
    return this.isBomb;

  }
}
