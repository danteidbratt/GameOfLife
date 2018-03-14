package gameoflife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

public class Grid extends JPanel {

    private Theme theme;
    private Size size;
    private final Size maxSize;
    private final Cell[][] cells;
    private Timer timer;
    private final Color backgroundColor;
    private Preset preset;
    private final PresetListener presetListener;
    private List<Cell> cellList;
    private int height;
    private int width;

    public Grid(Size maxSize, Size size, Timer timer, Theme theme, Color backgroundColor) {
        this.theme = theme;
        this.backgroundColor = backgroundColor;
        this.maxSize = maxSize;
        this.size = size;
        this.timer = timer;
        this.cells = new Cell[maxSize.getHeight() + 2][maxSize.getWidth() + 2];
        this.preset = null;
        this.presetListener = new PresetListener();
    }

    public void setGrid(float ratio, int height) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell();
            }
        }
        for (int i = 1; i < cells.length - 1; i++) {
            for (int j = 1; j < cells[i].length - 1; j++) {
                List<Cell> neighbours = new ArrayList<>();
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (!(k == i && l == j)) {
                            neighbours.add(cells[k][l]);
                        }
                    }
                }
                cells[i][j].setCell(theme, neighbours);
            }
        }
        cellList = getCellList();
        setPreferredSize(new Dimension((int) (height * ratio), height));
        setBackground(backgroundColor);
        updateGrid();
    }

    public void updateGrid() {
        removeAll();
        setLayout(new GridLayout(size.getHeight(), size.getWidth()));
        setCells();
        refresh();
    }

    private void setCells() {
        int verticalMargin = (maxSize.getHeight() - size.getHeight()) / 2;
        int horizontalMargin = (maxSize.getWidth() - size.getWidth()) / 2;
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                cell.setVisible(false);
            }
        }
        for (int i = verticalMargin + 1; i < (maxSize.getHeight() - verticalMargin) + 1; i++) {
            for (int j = horizontalMargin + 1; j < (maxSize.getWidth() - horizontalMargin) + 1; j++) {
                cells[i][j].setVisible(true);
                add(cells[i][j]);
            }
        }
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void generateGeneration() {
        Cell.resetChangeCounter();
        cellList.stream().filter(x -> (x.isAlive() && x.aliveNeighbours() != 2 && x.aliveNeighbours() != 3)
                || (!x.isAlive() && x.aliveNeighbours() == 3)).collect(Collectors.toList()).stream().forEach(x -> x.toggleState());
        repaint();
    }

    private List<Cell> getCellList() {
        List<Cell> visibleCells = new ArrayList<>();
        for (int i = 1; i < cells.length - 1; i++) {
            for (int j = 1; j < cells[i].length - 1; j++) {
                visibleCells.add(cells[i][j]);
            }
        }
        return visibleCells;
    }

    public void clear() {
        cellList.forEach(x -> x.reset());
        repaint();
    }

    private void refresh() {
        revalidate();
        repaint();
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
        cellList.forEach(x -> x.setTheme(theme));
        repaint();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean isEmpty() {
        boolean empty = true;
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.isAlive()) {
                    empty = false;
                    break;
                }
            }
            if (!empty) {
                break;
            }
        }
        return empty;
    }

    public Preset getPreset() {
        return preset;
    }

    public void setPreset(Preset preset) {
        this.preset = preset;
    }

    public void setPresetListener() {
        cellList.forEach(x -> x.removeListener());
        for (int i = 1; i < (cells.length - preset.getVerticalSize()) - 1; i++) {
            for (int j = 1; j < (cells[i].length - preset.getHorizontalSize()) - 1; j++) {
                cells[i][j].addExternalListener(presetListener);
            }
        }
    }

    public void removePresetListener() {
        cellList.forEach(x -> {
            x.removeExternalListener(presetListener);
            x.addListener();
        });
    }

    private class PresetListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (preset != null) {
                placePreset(e);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (preset != null) {
                displayPreset(e);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (preset != null) {
                hidePreset(e);
            }
        }
    }

    private void displayPreset(MouseEvent e) {
        for (int i = 1; i < cells.length - 1; i++) {
            for (int j = 1; j < cells[i].length - 1; j++) {
                if (e.getSource() == cells[i][j]) {
                    for (int[] coordinate : preset.getCoordinates()) {
                        cells[i + coordinate[0]][j + coordinate[1]].setBackground(theme.getAlive());
                    }
                }
            }
        }
        repaint();
    }

    private void hidePreset(MouseEvent e) {
        for (int i = 1; i < cells.length - 1; i++) {
            for (int j = 1; j < cells[i].length - 1; j++) {
                if (e.getSource() == cells[i][j]) {
                    for (int[] coordinate : preset.getCoordinates()) {
                        cells[i + coordinate[0]][j + coordinate[1]].setColor();
                    }
                }
            }
        }
        repaint();
    }

    private void placePreset(MouseEvent e) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (e.getSource() == cells[i][j]) {
                    for (int[] coordinate : preset.getCoordinates()) {
                        cells[i + coordinate[0]][j + coordinate[1]].revive();
                    }
                }
            }
            repaint();
        }
        removePresetListener();
        preset = null;
    }
}
