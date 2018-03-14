package gameoflife;

public class Size {
    
    private final String name;
    private final int width;
    private final int height;

    public Size(String name, int height, float ratio) {
        this.name = name;
        this.height = height;
        this.width = (int) (height * ratio);
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}