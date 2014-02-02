import javax.swing.*;
import java.awt.*;

public class Enemy {

    Image img = new ImageIcon(getClass().getClassLoader().getResource("res/car3.png")).getImage();
    Road road;

    int pozX;
    int pozY;
    int speed;

    public Enemy(int pozX, int poxY, int speed, Road road) {
        this.pozX = pozX;
        this.pozY = poxY;
        this.speed = speed;
        this.road = road;
    }

    public void move () {
        pozX = pozX - road.player.speed + speed;
    }

    public Rectangle getRectangle() {
        return new Rectangle(pozX, pozY, img.getWidth(null), img.getHeight(null));
    }

}
