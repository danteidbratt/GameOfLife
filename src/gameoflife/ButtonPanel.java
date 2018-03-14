package gameoflife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class ButtonPanel extends JPanel {

    private final JButton exitButton;
    private final JButton startButton;
    private final JButton nextButton;
    private final JButton clearButton;
    private final JButton rulesButton;
    private final JButton saveButton;
    private final Color backgroundColor;
    private final List<JButton> buttons;
    private final JPanel space;
    
    public ButtonPanel(Color backgroundColor) {
        this.exitButton = new JButton("Exit");
        this.nextButton = new JButton("Next");
        this.clearButton = new JButton("Clear");
        this.startButton = new JButton("Start");
        this.rulesButton = new JButton("Rules");
        this.saveButton = new JButton("Save");
        this.buttons = Arrays.asList(exitButton, nextButton, clearButton, startButton, rulesButton, saveButton);
        this.space = new JPanel();
        this.backgroundColor = backgroundColor;
    }
    
    public void setButtonPanel(ActionListener al) {
        setLayout(new GridLayout(6, 1, 20, 10));
        setPreferredSize(new Dimension(140, 0));
        setBackground(backgroundColor);
        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        for (JButton b : buttons) {
            b.addActionListener(al);
            b.setFont(new Font("SansSerif", 3, 16));
        }
        space.setBackground(backgroundColor);
        add(startButton);
        add(nextButton);
        add(clearButton);
        add(saveButton);
        add(rulesButton);
        add(exitButton);
    }
    
    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getRulesButton() {
        return rulesButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }
    
    public String getStartStopText() {
        return startButton.getText();
    }
    
    public void toggleStartStopText() {
        if (isRunning()) {
            startButton.setText("Start");
        }
        else {
            startButton.setText("Stop");
        }
    }
    
    public boolean isRunning() {
        return startButton.getText().equals("Stop");
    }

}
