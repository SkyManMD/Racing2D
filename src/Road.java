import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class Road extends JPanel implements ActionListener, Runnable{ //ActionListener - cere implementarea obligatroie a functiei actionPerformed(ActionEvent e);
    Timer mainTimer = new Timer(20, this);  //la fiecare 20ms timer-ul va indeplini functia actionPerform a obiectului nostru (this);

    Thread enemyGenerator = new Thread(this); //proces paralel care va genera concurentii; (Runnable cere implementarea obligatorie a metodei run();)
    Thread audioThread = new Thread(new AudoThread());

    Image img = new ImageIcon(getClass().getClassLoader().getResource("res/road2.png")).getImage(); //Image img = new ImageIcon("res/road2.png").getImage(); //getClass().getClassLoader().getResource - pentru a functiona cind facem jar arhiva;

    Player player = new Player();
    List<Enemy> enemyList = new ArrayList<Enemy>(); //lista de obiecte de tip Enemy (concurentii);

    private  final int IMAGE_WIDTH;
    private  final int IMAGE_HEIGHT;
    private final  int WIN_DISTANCE = 40000;


    public Road() {
        mainTimer.start();
        enemyGenerator.start(); //pornim Thread-ul;
        audioThread.start(); //pornim Thread-ul care reda muzica;
        addKeyListener(new MyKeyAdapter()); //ascultam de la tastatura eventul creat;
        setFocusable(true); //punem fucus-ul pe drum pentru a putea prelucra apasarile de buton;

        IMAGE_HEIGHT = img.getHeight(null);
        IMAGE_WIDTH = img.getWidth(null);
    }


    public void testCarCollision() {
        Iterator<Enemy> i = enemyList.iterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (player.getRectangle().intersects(e.getRectangle())) { //verificam daca dreptunchiurile formate de player si concurent se intersecteaza;
                JOptionPane.showMessageDialog(null, "You Lose :( ");
                System.exit(1);
                //i.remove();
            }
        }
    }

    public void testWin() {
        if (player.distance > WIN_DISTANCE) {
            JOptionPane.showMessageDialog(null, "Congrulation! You Win!");
            System.exit(1);
        }
    }

    @Override
    public void paint(Graphics g) { //metoda paint se cheama automat la initializarea obiectului, dar se poate chema si manual;
        //g = (Graphics2D) g; //deoarece numai Graphics2D poate desena;
        ((Graphics2D) g).drawImage(img, player.backgroundLayer1, 0, null); //incepe de la x=0;
        ((Graphics2D) g).drawImage(img, player.backgroundLayer2, 0, null); //incepe de la x=lungimea imaginii;
        ((Graphics2D) g).drawImage(player.img, player.pozX, player.pozY, null);

        ((Graphics2D) g).setColor(Color.YELLOW);
        ((Graphics2D) g).setFont(new Font("Segoe UI", Font.ITALIC, 20));
        ((Graphics2D) g).drawString("Speed : " + (player.speed * 3) + " km/h", 10, 25); //scrim viteza (transformata aprozimativ din px in km),
        ((Graphics2D) g).drawString("Distance : " + (player.distance / 15) + " m", 10, 50); //afisam distanta parcurasa;

        Iterator<Enemy> i = enemyList.iterator();
        while (i.hasNext()) { //atit timp cit este urmatorul element;
            Enemy e = i.next();
            if (e.pozX > IMAGE_WIDTH + 200 || e.pozX < -(IMAGE_WIDTH + 200)) { //stergerea concurentului daca a iesit din coordonatele care se vad pe ecran;
                i.remove();
            } else {
                e.move();
                ((Graphics2D) g).drawImage(e.img, e.pozX, e.pozY, null);
            }
        }
    }


    private class MyKeyAdapter extends KeyAdapter { //KeyAdapter este clasa abstracta, astfel nu e nevoie de a realiza toate metodele din ea ca in cazul daca am folosi KeyListener;
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { //functia se va chema la fiecare 20ms facind ca obiectul sa se miste, dupa asta redeseneaza tot;
        player.move();
        repaint(); //face update la paint();
        testCarCollision();
        testWin();
    }

    @Override
    public void run() { //aceasta metoda se va indeplini in Threadul creat;
        generateEnemy();
    }

    private void generateEnemy() {
        Random rand = new Random();
        while (true) {  //masinile se genereaza la infinit;
            try {
                enemyGenerator.sleep(rand.nextInt(3500)); //pauza 3.5 sec. (generare masina la n secude);
                enemyList.add(new Enemy(IMAGE_WIDTH + 100, rand.nextInt(IMAGE_HEIGHT / 2), rand.nextInt(60), this)); //generare concurent cu pozitii si viteaza aleatoare;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
