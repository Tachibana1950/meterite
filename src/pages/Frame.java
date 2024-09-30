package pages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import components.MyThread;
import utils.PlaySounds;

public class Frame extends JFrame implements MouseListener {
  // ! Attribute
  private int getNum = 1;
  // private float getSpeed = 0f;

  // Screen
  private int width = 800;
  private int height = 800;

  // Thread
  MyThread threads;
  MyThread threadContainer[];

  public Frame(int getNumFormFrame, float getSppedFromFrame) {
    setSize(new Dimension(this.width, this.height));
    setPreferredSize(new Dimension(this.width, this.height));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("Meteorite Created By Tachibana1650 & TheeraphatStudent");

    ImageIcon icon = new ImageIcon(Frame.class.getClassLoader().getResource("resource/picture/icon.png"));
    setIconImage(icon.getImage());

    this.getContentPane().setBackground(Color.decode("#111111"));

    this.getNum = getNumFormFrame;
    // this.getSpeed = getSppedFromFrame;

    this.add(new GamePanel(this, getNum, this.width, this.height));

    // Add event
    addMouseListener(this);

  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {
    new PlaySounds("laser-gun.wav");
  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
