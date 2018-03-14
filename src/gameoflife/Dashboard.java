package gameoflife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Dashboard extends JPanel{
    
    private final JPanel labelPanel;
    private final JPanel boxPanel;
    private final JLabel[] labels = {new JLabel("Generation"),new JLabel("Size"), new JLabel("Theme"), new JLabel("Speed"), new JLabel("Presets")};
    private final JLabel generationLabel;
    private final JComboBox<String> themeBox;
    private final JComboBox<String> sizeBox;
    private final JComboBox<String> speedBox;
    private JComboBox<String> presetBox;
    private final Color backgroundColor;

    public Dashboard(String[] themes, String[] sizes, String[] speeds, String[] presets, Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.labelPanel = new JPanel();
        this.boxPanel = new JPanel();
        this.themeBox = new JComboBox(themes);
        this.speedBox = new JComboBox(speeds);
        this.sizeBox = new JComboBox(sizes);
        this.presetBox = new JComboBox(presets);
        this.generationLabel = new JLabel("0");
    }
    
    public void setDashboard(ActionListener al) {
        setLayout(new GridLayout(2, 1));
        setBackground(backgroundColor);
        setPreferredSize(new Dimension(0, 80));
        setBorder(BorderFactory.createEmptyBorder(10, 0, 12, 20));
        labelPanel.setLayout(new GridLayout(1, 4));
        labelPanel.setBackground(backgroundColor);
        boxPanel.setLayout(new GridLayout(1, 4));
        boxPanel.setBackground(backgroundColor);
        sizeBox.addActionListener(al);
        speedBox.addActionListener(al);
        themeBox.addActionListener(al);
        presetBox.addActionListener(al);
        for (JLabel label : labels) {
            label.setFont(new Font("SansSerif", 1, 14));
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(0);
            labelPanel.add(label);
        }
        generationLabel.setHorizontalAlignment(0);
        generationLabel.setFont(new Font("SansSerif", 1, 20));
        generationLabel.setForeground(Color.WHITE);
        boxPanel.add(generationLabel);
        boxPanel.add(sizeBox);
        boxPanel.add(themeBox);
        boxPanel.add(speedBox);
        boxPanel.add(presetBox);
        add(labelPanel);
        add(boxPanel);
    }
    
    public JComboBox<String> getThemeBox() {
        return themeBox;
    }

    public JComboBox<String> getSizeBox() {
        return sizeBox;
    }

    public JComboBox<String> getSpeedBox() {
        return speedBox;
    }

    public JComboBox<String> getPresetBox() {
        return presetBox;
    }
    
    public void setGeneration(int generationCounter) {
        generationLabel.setText(String.valueOf(generationCounter));
    }

    public void setPresetBox(String[] values, ActionListener al) {
        boxPanel.remove(presetBox);
        presetBox = new JComboBox(values);
        presetBox.addActionListener(al);
        boxPanel.add(presetBox, 3);
        revalidate();
    }
    
}