import javax.swing.*;


public class FullPanel extends JPanel {

    static int SCREEN_WIDTH = 600;
    static int SCREEN_HEIGHT = 600;

    static int UNIT_SIZE = 25;

    ScorePanel scorePanel;

    FullPanel(){
        String[] mapScale = {"Small", "Medium", "Large"};

        int scaleSelected = JOptionPane.showOptionDialog(null, "Choose the map scale", "Size Matters", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, mapScale, 1);

        if (scaleSelected == 0) {
            SCREEN_WIDTH = 400;
            SCREEN_HEIGHT = 400;
        } else if (scaleSelected == 1) {
            SCREEN_WIDTH = 600;
            SCREEN_HEIGHT = 600;
        } else {
            SCREEN_WIDTH = 1100;
            SCREEN_HEIGHT = 1100;
            UNIT_SIZE = 50;
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(scorePanel = new ScorePanel());
        add(new GamePanel(scorePanel));

        setVisible(true);

    }
}
