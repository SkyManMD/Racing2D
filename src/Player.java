import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends JPanel {
    private static final int MAX_WIDTH_IMG = 653;
    private static final int MAX_SPEED = 50;
    private static final int MIN_SPEED = 0;
    private static final int MAX_TOP = 30;
    private static final int MAX_BOTTOM = 280;

    Image img = new ImageIcon(getClass().getClassLoader().getResource("res/car4.png")).getImage(); //se poate numai denumirea la imagine;
                              //getClass().getClassLoader().getResource - pentru a functiona cind facem fisier .jar (mapa res trebuie sa fie in interiorul la src)

    int speed = 0;
    int acceleration = 0;
    int distance = 0;

    int pozX = 0;
    int pozY = 20;
    int deltaY = 0;

    int backgroundLayer1 = 0; //drumul;
    int backgroundLayer2 = MAX_WIDTH_IMG; //latimea imaginii (pentru a face drumul mai lung);

    public void move() {  //la miscarea automobilului, distanta creste, drumul de misca proportional distantei;
        distance += speed;
        speed += acceleration;
        if (speed > MAX_SPEED) speed = MAX_SPEED;
        if (speed <= MIN_SPEED) speed = MIN_SPEED;

        pozY += deltaY;
        if (pozY <= MAX_TOP) pozY = MAX_TOP;
        if (pozY >= MAX_BOTTOM) pozY = MAX_BOTTOM;

        if (backgroundLayer2 - speed <= 0) { //schimbam bucatile de drum in ciclu;
            backgroundLayer1 = 0;
            backgroundLayer2 = MAX_WIDTH_IMG;
        } else {
        backgroundLayer1 -= speed;
        backgroundLayer2 -= speed;
        }
    }

    public Rectangle getRectangle() {
        return new Rectangle(pozX, pozY, img.getWidth(null), img.getHeight(null));
    }

    public void keyPressed(KeyEvent e) {
        //JOptionPane.showMessageDialog(null, "key press");
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {
            acceleration = 1;
        }
        if (key == KeyEvent.VK_LEFT) {
            acceleration = -2;
        }
        if (key == KeyEvent.VK_UP) {
            deltaY = -10;
            img = new ImageIcon(getClass().getClassLoader().getResource("res/car4_top.png")).getImage();
        }
        if (key == KeyEvent.VK_DOWN) {
            deltaY = 10;
            img = new ImageIcon(getClass().getClassLoader().getResource("res/car4_bottom.png")).getImage();
        }
    }

    public void keyReleased(KeyEvent e) {
        //JOptionPane.showConfirmDialog(null, "key released");
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT) {
            acceleration = 0;
        }
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            deltaY = 0;
            img = new ImageIcon(getClass().getClassLoader().getResource("res/car4.png")).getImage();
        }

    }

}
