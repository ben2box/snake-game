import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {


    static final int GAME_UNITS = (FullPanel.SCREEN_WIDTH * FullPanel.SCREEN_HEIGHT) / FullPanel.UNIT_SIZE;

    static int DELAY = 75;

    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];

    int bodyParts = 6;

    static int applesEaten;
    int appleX;
    int appleY;

    ImageIcon apple = new ImageIcon("X:\\bootcamp92\\exercises_training\\SnakeGame\\src\\main\\resources\\Graphics\\apple.png");

    char direction = 'R';
    boolean running = false;
    boolean moveComplete = false; //check to avoid double key press

    boolean hardMode = false; //flags for difficulty level
    boolean easyMode = false; //flags for difficulty level

    Timer timer;
    Random random;

    ScorePanel scorePanel; //pass it here and in constructor to allow score text update with each apple caught


    GamePanel(ScorePanel scorePanel) {

        this.scorePanel = scorePanel;
        random = new Random();
        this.setPreferredSize(new Dimension(FullPanel.SCREEN_WIDTH, FullPanel.SCREEN_HEIGHT));
        this.setBackground(new Color(50, 50, 50));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        difficultySelector(); //Asks for difficulty level before starting the game
        startGame();
    }

    //Method that changes base delay of timer -> changes speed of game and difficulty
    public void difficultySelector(){
        String[] difficultyOption = {"Easier", "Regular (Score x2)", "Harder (Score x3)"};

        int difficultySelected = JOptionPane.showOptionDialog(null, "How difficult do you want this to be?", "Skill too!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, difficultyOption, 1);
        if (difficultySelected == 0) {
            DELAY = 90;
            easyMode = true;
        } else if (difficultySelected == 1) {
            DELAY = 75;
        } else {
            DELAY = 60;
            hardMode = true;
        }
    }

    public void startGame() {
        spawnApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void restartGame() {
        bodyParts = 6;
        applesEaten = 0;
        direction = 'R';
        running = true;
        scorePanel.updateScore(applesEaten);
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 0;
            y[i] = 0;
        }
        spawnApple();
        timer.restart();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g; //this API allows me to resize the apple icon at will (in runtime)

        if (running) {
            //Draw Grid Lines for better view of what's happening
            /*
            for (int i = 0; i < FullPanel.SCREEN_WIDTH / FullPanel.UNIT_SIZE; i++) {
                g.drawLine(i * FullPanel.UNIT_SIZE, 0, i * FullPanel.UNIT_SIZE, FullPanel.SCREEN_HEIGHT);
                g.drawLine(0, i * FullPanel.UNIT_SIZE, FullPanel.SCREEN_WIDTH, i * FullPanel.UNIT_SIZE);
            }
            */

            //Paint Apple icon each time it spawns

            if (FullPanel.UNIT_SIZE == 25) {
                g2D.drawImage(apple.getImage(), appleX, appleY - 6, 25, 31, this);
            } else {
                g2D.drawImage(apple.getImage(), appleX, appleY - 12, 50, 62, this);
            }

            /*
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, FullPanel.UNIT_SIZE, FullPanel.UNIT_SIZE);
            */


            //Draw each body part, slightly different tone for head and body
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) { //Draw/Paint head of Snake

                   g.setColor(Color.green);
                   g.fillRect(x[i], y[i], FullPanel.UNIT_SIZE, FullPanel.UNIT_SIZE);

                } else {
                    //Draw/Paint body of Snake
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], FullPanel.UNIT_SIZE, FullPanel.UNIT_SIZE);

                }
            }

        }

        //Calls Game Over method if game not running
        else {
            gameOver(g);
        }
    }

    public void spawnApple() {
        appleX = random.nextInt(FullPanel.SCREEN_WIDTH / FullPanel.UNIT_SIZE) * FullPanel.UNIT_SIZE;
        appleY = random.nextInt(FullPanel.SCREEN_HEIGHT / FullPanel.UNIT_SIZE) * FullPanel.UNIT_SIZE;

    }

    public void move() {

        //Loops every body part to follow head
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U' -> y[0] = y[0] - FullPanel.UNIT_SIZE;
            case 'D' -> y[0] = y[0] + FullPanel.UNIT_SIZE;
            case 'L' -> x[0] = x[0] - FullPanel.UNIT_SIZE;
            case 'R' -> x[0] = x[0] + FullPanel.UNIT_SIZE;
        }

        moveComplete = true;


    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            scorePanel.updateScore(applesEaten);
            increaseDifficulty();
            spawnApple();
        }

    }

    public void increaseDifficulty() {
        //Difficulty Increase at certain score levels
        if (applesEaten == 5) {

            if (hardMode) {
                timer.setDelay(60);
            } else if (easyMode) {
                timer.setDelay(80);
            } else {
                timer.setDelay(65);
            }
        } else if (applesEaten == 10){
            if (hardMode) {
                timer.setDelay(50);
            } else if (easyMode) {
                timer.setDelay(75);
            } else {
                timer.setDelay(60);
            }
        } else if (applesEaten == 15) {

            if (hardMode) {
                timer.setDelay(45);
            } else if (easyMode) {
                timer.setDelay(65);
            } else {
                timer.setDelay(55);
            }

        } else if (applesEaten == 20) {
            if (hardMode) {
                timer.setDelay(40);
            } else if (easyMode) {
                timer.setDelay(60);
            } else {
                timer.setDelay(50);
            }
        } else if (applesEaten == 30) {
            if (hardMode) {
                timer.setDelay(30);
            } else if (easyMode) {
                timer.setDelay(50);
            } else {
                timer.setDelay(40);
            }
        } else if (applesEaten == 40) {
            if (hardMode) {
                timer.setDelay(25);
            } else if (easyMode) {
                timer.setDelay(45);
            } else {
                timer.setDelay(35);
            }
        }

    }

    public void checkCollision() {
        //checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                System.out.println("Death by Self-Destruction");
            }
        }
        //checks if head touches left border
        if (x[0] < 0) {
            running = false;
            System.out.println("Death by Left Border");
        }
        //checks if head touches right border
        if (x[0] >= FullPanel.SCREEN_WIDTH) {
            running = false;
            System.out.println("Death by Right Border");
        }
        //checks if head touches top border
        if (y[0] < 0) {
            running = false;
            System.out.println("Death by Top Border");
        }
        //checks if head touches bottom border
        if (y[0] >= FullPanel.SCREEN_HEIGHT) {
            running = false;
            System.out.println("Death by Bottom Border");
        }


    }

    public void gameOver(Graphics g) {

        if (!running) {
            timer.setDelay(DELAY); //Sets original DELAY when dead to avoid restarting on harder Difficulty
            timer.stop();
            System.out.println("TIMER STOPPED");
        }


        //Game Over Text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (FullPanel.SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, FullPanel.SCREEN_HEIGHT / 2);

        //Restart or Quit options Text
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Press ENTER to Restart", (FullPanel.SCREEN_WIDTH - metrics3.stringWidth("Press ENTER to Restart")) / 2, FullPanel.SCREEN_HEIGHT / 2 + 50);
        g.drawString("Press Q to Quit", (FullPanel.SCREEN_WIDTH - metrics3.stringWidth("Press Q to Quit")) / 2, FullPanel.SCREEN_HEIGHT / 2 + 100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R' && moveComplete) {
                        direction = 'L';
                        moveComplete = false;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L' && moveComplete) {
                        direction = 'R';
                        moveComplete = false;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D' && moveComplete) {
                        direction = 'U';
                        moveComplete = false;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U' && moveComplete) {
                        direction = 'D';
                        moveComplete = false;
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    if (!running) {
                        restartGame();
                    }
                    break;
                case KeyEvent.VK_Q:
                    System.exit(0);
                    break;

            }
        }

    }

}
