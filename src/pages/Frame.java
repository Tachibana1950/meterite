package pages;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import components.MyThread;

public class Frame extends JFrame {
  // ! Attribute
  private int getNum = 1;
  // private float getSpeed = 0f;

  // Screen
  private int width = 500;
  private int height = 500;

  // Thread
  MyThread threads;
  MyThread threadContainer[];

  public Frame(int getNumFormFrame, float getSppedFromFrame) {
    setSize(new Dimension(this.width, this.height));
    setPreferredSize(new Dimension(this.width, this.height));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    this.getContentPane().setBackground(Color.decode("#111111"));

    this.getNum = getNumFormFrame;
    // this.getSpeed = getSppedFromFrame;

    this.add(new GamePanel(this, getNum, this.width, this.height));

  }
}
