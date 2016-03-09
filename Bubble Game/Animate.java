import objectdraw.*;
import java.lang.InterruptedException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
/**
 * @author: Xiyun Liu
 * about: This program uses the objectdraw library to create an animation 
 *        of a Bubble Clicking Game. The bubbles appear in random size, random shape
 *        and random color. If you click on a specific bubble, it will disappear and 
 *        you will get score based on some calculation on the size of the bubble; 
 *        if you click on a blank area, two additional bubbles appear on the canvas.
 *        The game will end when there are more than 20 bublles on the screen. 
 *        After the game terminates, your score will be showed on terminal.
 *
 * compile: javac -cp objectdraw.jar:. Animate.java
 * run: java -cp objectdraw.jar:. Animate
 */

public class Animate extends WindowController {
  final static int SIZE = 500; //size of the window for animation.
  final static int LINUX_MENU_BAR = 50; // account for menu bar on
                                        // linux systems
  public static ArrayList<Thread> t = new ArrayList<Thread>();
  private static int score = 0;

  /**
   * Program control automatically jumps to this method after executing
   * startController().
   */
  public void begin() {
    int startSleep = 500;

    DrawingCanvas myCanvas = canvas; 
    myCanvas.setBackground

    //Example thread creation to draw a circle
    for (int i=0; i<5;i++) {
      Thread t1 = new DrawArc(canvas);
      catchSleep(50); 
      t.add(t1);//make the primary thread sleep
      t.get(t.size()-1).start();      
    }

    Thread t2 = new DrawArc(150, 0, canvas,2,0);
    catchSleep(startSleep); 
    t.add(t2);//make the primary thread sleep
    t.get(t.size()-1).start();

    Thread t3 = new DrawArc(0, 450, canvas,1,1);
    catchSleep(startSleep); 
    t.add(t3);//make the primary thread sleep
    t.get(t.size()-1).start();

    Thread t4 = new DrawArc(0, 300, canvas,2,1);
    catchSleep(startSleep); 
    t.add(t4);//make the primary thread sleep
    t.get(t.size()-1).start();
  }


  public void  onMousePress(Location point) {
    // System.out.println(point.toPoint());
    boolean click_right = false;
    for (int i=0; i<t.size(); i++) {
      // check if the user click on the bubble
      if (((DrawArc)t.get(i)).shape == 0) { 
        Location fa_location = ((DrawArc)t.get(i)).fa.getLocation();
        int left_coner_x = (int)fa_location.getX();
        int left_coner_y = (int)fa_location.getY();

        int mouse_x = (int)point.getX();
        int mouse_y = (int)point.getY();

        if (mouse_x>=left_coner_x && mouse_x<=left_coner_x+45 
            && mouse_y>=left_coner_y && mouse_y<=left_coner_y+45) {
           ((DrawArc)t.get(i)).fa.removeFromCanvas();
           click_right = true;
           score = score + (50-((DrawArc)t.get(i)).pattern_size);
           t.remove(i);
        }
      }
      else {
        Location fb_location = ((DrawArc)t.get(i)).fb.getLocation();
        int left_coner_x = (int)fb_location.getX();
        int left_coner_y = (int)fb_location.getY();

        int mouse_x = (int)point.getX();
        int mouse_y = (int)point.getY();

        if (mouse_x>=left_coner_x && mouse_x<=left_coner_x+45 
            && mouse_y>=left_coner_y && mouse_y<=left_coner_y+45) {
           ((DrawArc)t.get(i)).fb.removeFromCanvas();
           click_right = true;
           score = score + (50-((DrawArc)t.get(i)).pattern_size);
           t.remove(i);
        }        
      }

    }

    if (click_right == false){

      Thread tt = new DrawArc(canvas);
      t.add(tt);//make the primary thread sleep
      t.get(t.size()-1).start();

      Thread tt2 = new DrawArc(canvas);
      t.add(tt2);//make the primary thread sleep
      t.get(t.size()-1).start();
    }
    if (t.size()>=30) {
      System.out.println("Your total score is: "+ score);
      System.exit(0);
    }
  }

  /**
   * Has the program pause for time miliseconds so animations can move
   * at a speed you'd like
   * @param time the time in miliseconds you want to sleep.
   */
  public static void catchSleep(int time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {}
  }



  public static void main(String[] args) {
    // start the animation. 
    new Animate().startController(SIZE,SIZE+LINUX_MENU_BAR);

    
  }

}
