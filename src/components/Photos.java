package components;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class Photos extends JPanel{

  private Image show;

  private int photoWidth = 50;
  private int photoHeight = 50;

  // private int x = this.getX();
  // private int y = this.getY();
  private int x = 0;
  private int y = 0;

  public Photos() {
    int numpic = (int)(Math.random()*10) + 1;
    this.setBackground(Color.BLACK);

    try {
      InputStream is = Photos.class.getClassLoader().getResourceAsStream("resource/picture/"+numpic+".png");
      this.show = ImageIO.read(is);

    } catch (Exception e) {
      System.err.println();
    }

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

    g.drawImage(this.show, 0, 0, 50, 50, this);
    g.drawRect(0, 0, 50, 50);

    // Log X
    g.drawString(String.valueOf(x), 0, -10);

    // Log Y
    g.setFont(new Font("Arial", Font.PLAIN, 12));
    g.drawString(String.valueOf(y), 25, -10);

  }

}
