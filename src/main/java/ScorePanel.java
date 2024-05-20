import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScorePanel extends JPanel {

    private JLabel scoreLabel;

    ScorePanel() {
        this.setPreferredSize(new Dimension(FullPanel.SCREEN_WIDTH, 50));
        this.setBackground(Color.darkGray);
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.white);
        scoreLabel.setFont(new Font("Ink Free", Font.BOLD, 30));
        this.add(scoreLabel);
    }

    public void updateScore(int score){
        scoreLabel.setText("Score: " + score);
    }


}