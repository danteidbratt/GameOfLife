package gameoflife;

public class Speed {
    
    private final String name;
    private final double GPS;

    public Speed(String name, int GPS) {
        this.name = name;
        this.GPS = GPS;
    }

    public String getName() {
        return name;
    }

    public int getGPS() {
        return (int) (1000/GPS);
    }

}