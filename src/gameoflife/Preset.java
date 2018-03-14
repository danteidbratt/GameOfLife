package gameoflife;

import java.io.Serializable;
import java.util.*;

public class Preset implements Serializable {
    
    private final String name;
    private List<int[]> coordinates;
    private int horizontalSize;
    private int verticalSize;
    private static final long serialVersionUID = 1234L;

    public Preset(String name) {
        this.name = name;
        this.coordinates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<int[]> getCoordinates() {
        return coordinates;
    }
    
    public void setCoordinates(Cell[][] cells) {
        int firstHorizontal = 0;
        int lastHorizontal = 0;
        int firstVertical = 0;
        int lastVertical = 0;
        
        for (int i = 0; i < cells[0].length; i++) {
            for (int j = 0; j < cells.length; j++) {
                if(cells[j][i].isAlive()){
                    firstHorizontal = i;
                    break;
                }
            }
            if(firstHorizontal != 0) {
                break;
            }
        }
        
        for (int i = cells[0].length - 1; i > -1; i--) {
            for (int j = cells.length - 1; j > -1; j--) {
                if(cells[j][i].isAlive()) {
                    lastHorizontal = i;
                    break;
                }
            }
            if(lastHorizontal != 0) {
                break;
            }
        }
        
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if(cells[i][j].isAlive()){
                    firstVertical = i;
                    break;
                }
            }
            if(firstVertical != 0) {
                break;
            }
        }
        
        for (int i = cells.length - 1; i > -1; i--) {
            for (int j = cells[0].length - 1; j > -1; j--) {
                if(cells[i][j].isAlive()) {
                    lastVertical = i;
                    break;
                }
            }
            if(lastVertical != 0) {
                break;
            }
        }
        
        horizontalSize = lastHorizontal - firstHorizontal;
        verticalSize = lastVertical - firstVertical;
        
        for (int i = firstVertical; i <= lastVertical; i++) {
            for (int j = firstHorizontal; j <= lastHorizontal; j++) {
                if(cells[i][j].isAlive()) {
                    int[] temp = {i-firstVertical, j-firstHorizontal};
                    coordinates.add(temp);
                }
            }
        }
    }

    public int getHorizontalSize() {
        return horizontalSize;
    }

    public int getVerticalSize() {
        return verticalSize;
    }
    
}