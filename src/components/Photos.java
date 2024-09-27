package components;

import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Photos extends JPanel implements MouseMotionListener, MouseListener {

  // Image
  private Image show;
  private ImageIcon bomb;

  // Other
  private int photoWidth = 50;
  private int photoHeight = 50;
  private int num = 0;
  private boolean isBomb = false;

  public Photos() {
    this.setOpaque(false);

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
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  public int getImageSize(String _case) {
    if (_case.equals("width")) {
      return this.photoWidth;

    }
    return this.photoHeight;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.setColor(Color.red);

    if ((num < 2) && !this.isBomb) {
      g.drawImage(this.show, 0, 0, 50, 50, this);

    } else {
      g.drawImage(bomb.getImage(), 0, 0, 50, 50, this);

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
      isBomb = true;
      repaint();

      Timer timer = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          setVisible(false);
          repaint();
        }
      });

      timer.setRepeats(false);
      timer.start();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseDragged(MouseEvent e) {
  }

  @Override
  public void mouseMoved(MouseEvent e) {
  }
}
