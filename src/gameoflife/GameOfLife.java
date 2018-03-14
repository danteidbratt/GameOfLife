package gameoflife;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class GameOfLife implements ActionListener {

    private Window window;
    private Grid grid;
    private Dashboard dashboard;
    private ButtonPanel buttonPanel;

    private final List<Theme> themes;
    private Theme currentTheme;
    private final List<Size> sizes;
    private Size currentSize;
    private final List<Speed> speeds;
    private Speed currentSpeed;
    private final Color backgroundColor;
    private final float ratio;
    private int generationCounter;
    
    private final PresetHandler presetHandler;
    private List<Preset> presets;
    
    public GameOfLife() {
        
        presetHandler = new PresetHandler();
        presets = new ArrayList<>();

        // ************************** Settings **************************
        
        ratio = 1;
        generationCounter = 0;
        
        themes = Arrays.asList(new Theme("Dark", Color.YELLOW, Color.DARK_GRAY, Color.BLACK),
                new Theme("Light", new Color(200, 10, 10), new Color(170, 170, 170), Color.BLACK),
                new Theme("Cool", new Color(0, 180, 0), new Color(100, 100, 100), Color.BLACK));
        sizes = Arrays.asList(new Size("Small", 21, ratio),
                new Size("Medium", 41, ratio),
                new Size("Large", 51, ratio),
                new Size("XL", 71, ratio),
                new Size("XXL", 91, ratio));
        speeds = Arrays.asList(new Speed("Slow", 2),
                new Speed("Medium", 5),
                new Speed("Fast", 10),
                new Speed("Extreme", 20));
        presets = presetHandler.getPresets();
        
        // ****************************************************************
        
        currentTheme = themes.get(0);
        currentSize = sizes.get(0);
        currentSpeed = speeds.get(0);
        backgroundColor = new Color(40, 40, 40);
    }

    private void start() {
        Size largestSize = sizes.stream().reduce((biggest, next) -> next.getHeight() > biggest.getHeight() ? next : biggest).get();
        grid = new Grid(largestSize, sizes.get(0), new Timer(currentSpeed.getGPS(), this), currentTheme, backgroundColor);
        grid.setGrid(ratio);
        dashboard = new Dashboard(themes.stream()
                .map(x -> x.getName())
                .collect(Collectors.toList())
                .toArray(new String[themes.size()]),
                sizes.stream().map(x -> x.getName()).collect(Collectors.toList()).toArray(new String[sizes.size()]),
                speeds.stream().map(x -> x.getName()).collect(Collectors.toList()).toArray(new String[speeds.size()]),
                presets.stream().map(x -> x.getName()).collect(Collectors.toList()).toArray(new String[presets.size()]),
                backgroundColor);
        dashboard.setDashboard(this);
        buttonPanel = new ButtonPanel(backgroundColor);
        buttonPanel.setButtonPanel(this);
        window = new Window();
        window.setWindow(grid, dashboard, buttonPanel, backgroundColor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonPanel.getNextButton()) {
            grid.generateGeneration();
            dashboard.setGeneration(++generationCounter);
        }

        if (e.getSource() == buttonPanel.getClearButton()) {
            buttonPanel.getStartButton().setText("Stop");
            buttonPanel.getStartButton().doClick();
            generationCounter = 0;
            dashboard.setGeneration(generationCounter);
            grid.clear();
        }

        if (e.getSource() == buttonPanel.getStartButton()) {
            if (buttonPanel.isRunning()) {
                grid.getTimer().stop();
            } else {
                grid.getTimer().start();
            }
            buttonPanel.toggleStartStopText();
            window.refresh();
        }

        if (e.getSource() == buttonPanel.getRulesButton()) {
            window.displayRules();
        }

        if (e.getSource() == grid.getTimer()) {
            grid.generateGeneration();
            dashboard.setGeneration(++generationCounter);
            if (Cell.getChangeCounter() == 0) {
                buttonPanel.getStartButton().doClick();
            }
        }

        if (e.getSource() == dashboard.getSizeBox()) {
            currentSize = sizes.stream()
                    .filter(s -> s.getName().equals(dashboard.getSizeBox().getSelectedItem().toString()))
                    .collect(Collectors.toList())
                    .get(0);
            grid.setSize(currentSize);
            grid.updateGrid();
        }

        if (e.getSource() == dashboard.getThemeBox()) {
            currentTheme = themes.stream()
                    .filter(x -> x.getName().equals(dashboard.getThemeBox().getSelectedItem().toString()))
                    .collect(Collectors.toList())
                    .get(0);
            grid.setTheme(currentTheme);
        }

        if (e.getSource() == dashboard.getSpeedBox()) {
            currentSpeed = speeds.stream()
                    .filter(s -> s.getName().equals(dashboard.getSpeedBox().getSelectedItem().toString()))
                    .collect(Collectors.toList())
                    .get(0);
            grid.setTimer(new Timer(currentSpeed.getGPS(), this));
            if (buttonPanel.isRunning()) {
                grid.getTimer().start();
            }
        }

        if (e.getSource() == buttonPanel.getSaveButton()) {
            if (!grid.isEmpty()) {
                String input = JOptionPane.showInputDialog("Preset name:");
                if (input != null && input.length() > 0) {
                    Preset temp = new Preset(input);
                    temp.setCoordinates(grid.getCells());
                    presetHandler.save(temp);
                    presets = presetHandler.getPresets();
                    dashboard.setPresetBox(presets.stream()
                            .map(x -> x.getName()).collect(Collectors.toList())
                            .toArray(new String[presets.size()]), this);
                } 
                else {
                    displayErrorMessage("Name required");
                }
            } else {
                displayErrorMessage("Grid is empty");
            }
        }
        
        if (e.getSource() == dashboard.getPresetBox()) {
            grid.setPreset(presets.stream()
                    .filter(x -> x.getName().equals(dashboard.getPresetBox().getSelectedItem().toString()))
                    .collect(Collectors.toList()).get(0));
            grid.setPresetListener();
        }

        if (e.getSource() == buttonPanel.getExitButton()) {
            System.exit(0);
        }
    }

    private void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Oops", 0);
    }

    public static void main(String[] args) {
        GameOfLife gol = new GameOfLife();
        gol.start();
    }

}
