package gameoflife;

import java.awt.Color;

public class Theme {
    
    private final String name;
    private final Color alive;
    private final Color dead;
    private final Color border;

    public Theme(String name, Color alive, Color dead, Color border) {
        this.name = name;
        this.alive = alive;
        this.dead = dead;
        this.border = border;
    }

    public String getName() {
        return name;
    }
    
    public Color getAlive() {
        return alive;
    }

    public Color getDead() {
        return dead;
    }

    public Color getBorder() {
        return border;
    }
    
}