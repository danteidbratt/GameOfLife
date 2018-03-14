package gameoflife;

import java.io.*;
import java.util.*;

public class PresetHandler {

    private List<String> fileNames;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;
    private List<Preset> presets;
    private FileOutputStream fileStream;
    private ObjectOutputStream objectStream;

    public List<Preset> getPresets() {
        presets = new ArrayList<>();
        fileNames = new ArrayList<>();
        for (File file : new File("presets").listFiles()) {
            if (file.getName().substring(file.getName().length() - 4).equals(".ser")) {
                fileNames.add(file.getName());
            }
        }
        fileNames.forEach((file) -> {
            try {
                fileInputStream = new FileInputStream("presets/" + file);
                objectInputStream = new ObjectInputStream(fileInputStream);
                presets.add((Preset) objectInputStream.readObject());
            } catch (FileNotFoundException ex) {
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        return presets;
    }

    public void save(Preset preset) {
        try {
            fileStream = new FileOutputStream("presets/" + preset.getName() + ".ser", true);
            objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(preset);
            objectStream.flush();
            objectStream.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
