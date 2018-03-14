package gameoflife;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;

public class Window extends JFrame {

    private final JPanel[] spaces = {new JPanel(), new JPanel()};

    public void setWindow(Grid grid, Dashboard dashboard, ButtonPanel buttonPanel, Color backgroundColor) {
        setTitle("Game of Life");
        setLayout(new BorderLayout());
        setBackground(backgroundColor);
        for (JPanel s : spaces) {
            s.setBackground(backgroundColor);
            s.setPreferredSize(new Dimension(20, 20));
        }
        add(grid, "Center");
        add(dashboard, "North");
        add(buttonPanel, "West");
        add(spaces[0], "East");
        add(spaces[1], "South");
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void setGrid(Grid grid) {
        add(grid, "Center");
        refresh();
    }
    
    public void refresh() {
        revalidate();
        repaint();
    }

    public void displayRules() {
        String rules = "\nFor spaces that are 'populated':"
                + "\n   - Each cell with one or no neighbors dies, as if by solitude."
                + "\n   - Each cell with four or more neighbors dies, as if by overpopulation."
                + "\n   - Each cell with two or three neighbors survives."
                + "\n\nFor spaces that are 'unpopulated':"
                + "\n   - Each cell with three neighbors becomes populated.";
        JOptionPane.showMessageDialog(null, rules, "Rules", 1);
    }
}
