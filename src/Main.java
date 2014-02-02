
import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Racing 2D");
        frame.setSize(700, 460);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("res/Racing-icon.png").getImage());
        frame.setLayout(new BorderLayout());
        //frame.setUndecorated(true);

        //frame.add(new JLabel("KM/H"));
        frame.add(new Road());

        frame.setVisible(true);
    }
}
