package gameoflife;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

public class Cell extends JLabel {

    private static int changeCounter = 0;
    private boolean visible;
    private Theme theme;
    private boolean alive;
    private List<Cell> neighbours;
    private Listener listener;

    public Cell() {
        this.visible = false;
        this.alive = false;
    }

    public void setCell(Theme theme, List<Cell> neighbours) {
        this.theme = theme;
        this.neighbours = neighbours;
        setBackground(theme.getDead());
        setBorder(BorderFactory.createLineBorder(this.theme.getBorder(), 1));
        setOpaque(true);
        this.listener = new Listener();
        addMouseListener(listener);
    }

    public void die() {
        alive = false;
        setColor();
        Cell.changeCounter++;
    }
    
    public void revive() {
        alive = true;
        setColor();
        Cell.changeCounter++;
    }
    
    public void toggleState() {
        alive = !alive;
        setColor();
        Cell.changeCounter++;
    }

    public void setColor() {
        if (alive) {
            this.setBackground(theme.getAlive());
        } else {
            this.setBackground(theme.getDead());
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void reset() {
        alive = false;
        setColor();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int aliveNeighbours() {
        return neighbours.stream().filter(x -> x.isAlive()).collect(Collectors.toList()).size();
    }

    public static int getChangeCounter() {
        return changeCounter;
    }

    public static void resetChangeCounter() {
        Cell.changeCounter = 0;
    }
    
    public void removeListener() {
        removeMouseListener(listener);
    }
    
    public void addListener() {
        addMouseListener(listener);
    }

    private class Listener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent me) {
            toggleState();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            setBorder(BorderFactory.createLineBorder(theme.getBorder(), 3));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setBorder(BorderFactory.createLineBorder(theme.getBorder(), 1));
        }
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
        setColor();
    }
    
    public void addExternalListener (MouseAdapter ma) {
        addMouseListener(ma);
    }
    
    public void removeExternalListener (MouseAdapter ma) {
        removeMouseListener(ma);
    }
}
