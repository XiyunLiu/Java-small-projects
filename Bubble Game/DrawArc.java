import objectdraw.*;
import java.awt.Color;
import java.util.Random;
/**
 * DrawArc draws a circle at a given location.
 * upon started on as a thread, e.g.
 * Thread arc = new DrawArc(<params>);
 * arc.start();
 * the circle moves diagonally down and back up the screen.
 */
public class DrawArc extends Thread{
  private int x, y;
  private DrawingCanvas canvas;
  FilledArc fa;
  FilledRect fb;
  private final int ARC_SIZE = 50;
  private int orientation; 
  int shape;//1: left-up to right-down, 2: right-up to left-down
  int pattern_size;

  /**
   * @param canvas - the canvas to draw the arc in. Should be
   * provided by objectdraw
   */
  public DrawArc(DrawingCanvas canvas) {

    Random rand = new Random();
    int n = rand.nextInt(450)+1;
    int choice = rand.nextInt(2);
    int orientation = rand.nextInt(2);

    if (choice == 1) {
      this.x = n;
      this.y = 0;
    }
    else {
      this.x = 0;
      this.y = n;     
    }

    this.canvas = canvas;
    int startAngle = 0;
    int stopAngle = 360;
    this.orientation = rand.nextInt(2);

    int shape = rand.nextInt(2); 
    int pattern_size = rand.nextInt(20)+25;
    this.shape = shape;   
    if (shape == 0) { //round
      fa = new FilledArc(this.x, this.y, ARC_SIZE, ARC_SIZE, startAngle, stopAngle, canvas);
      set_color(fa);
    }
    else {
      fb = new FilledRect(this.x,this.y,pattern_size,pattern_size,canvas);
      set_color(fb);
    }
  }

  /**
   * @param x - x starting location for the arc
   * @param y - y starting location for the arc
   * @param canvas - the canvas to draw the arc in. Should be provided by objectdraw
   * @param orientatiton - 0 or 1 indicates the orientation of the bubble
   * @param shape - 0 or 1 indicates the shape of the bubble. 0 is round, 1 is rectangular
   */
  public DrawArc(int x, int y, DrawingCanvas canvas, int orientation, int shape) {

    this.x = x;
    this.y = y;
    this.shape = shape;

    this.canvas = canvas;
    int startAngle = 0;
    int stopAngle = 360;
    this.orientation = orientation;

    Random rand = new Random();
    int pattern_size = rand.nextInt(20)+25;

    if (shape == 0) { //round
      fa = new FilledArc(this.x, this.y, ARC_SIZE, ARC_SIZE, startAngle, stopAngle, canvas);
      set_color(fa);
    }
    else {
      fb = new FilledRect(this.x,this.y,pattern_size,pattern_size,canvas);
      set_color(fb);
    }
  }
  /**
   * @param fa - round pattern
   */
  public void set_color (FilledArc fa) {
    Random rand = new Random();
    int r = rand.nextInt(256);
    int g = rand.nextInt(256);
    int b = rand.nextInt(256);
    int a = rand.nextInt(256);
    fa.setColor(new Color(r,g,b,a));
  }
  /**
   * @param fb - rectangular pattern
   */
  public void set_color (FilledRect fb) {
    Random rand = new Random();
    int r = rand.nextInt(256);
    int g = rand.nextInt(256);
    int b = rand.nextInt(256);
    int a = rand.nextInt(256);
    fb.setColor(new Color(r,g,b,a));
  }  

  /**
   * Executed when the thread starts and runs indefinitly, moving the arc
   * across the screen.
   */
  public void run() {
    int edge = 450;
    int downX=1, downY=1;
    int upX=-1, upY=-1;
    int moveX, moveY;
    Random rand = new Random();
    int speed = rand.nextInt(5)+5;

    if (orientation == 1) {
      moveX = downX;
      moveY = downY;
    }
    else {
      moveX = upX;
      moveY = downY;
    }
    if (this.shape == 0) {
      while(true) {
        // move arc down
        for(int i = 0; i < edge; i++ ) {
          if (fa.getX() > edge || fa.getX() < 0) {
            moveX = 0-moveX;
          }
          if (fa.getY() > edge || fa.getY() < 0) {
            moveY = 0-moveY;
          }
          fa.move(moveX, moveY);
          //make this thread sleep for 10ms to smooth animation
          Animate.catchSleep(speed);  
        }
      }
    }
  if (this.shape == 1) {
      while(true) {
        // move arc down
        for(int i = 0; i < edge; i++ ) {
          if (fb.getX() > edge || fb.getX() < 0) {
            moveX = 0-moveX;
          }
          if (fb.getY() > edge || fb.getY() < 0) {
            moveY = 0-moveY;
          }
          fb.move(moveX, moveY);
          //make this thread sleep for 10ms to smooth animation
          Animate.catchSleep(speed);  
        }
      }    
    } 
  }
}
