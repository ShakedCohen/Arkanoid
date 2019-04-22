package menu;

import animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {

    private String title;
    private KeyboardSensor sensor;
    private AnimationRunner runner;
    private List<MenuSelection> selectionOptions = new ArrayList<MenuSelection>();


    private T chosen = null;
    private boolean stop;


    /**
     * Instantiates a new Menu animation.
     *
     * @param title  the title
     * @param runner the runner
     * @param sensor the sensor
     */
    public MenuAnimation(String title, AnimationRunner runner, KeyboardSensor sensor) {
        this.title = title;
        this.sensor = sensor;
        this.runner = runner;
        this.stop = false;
    }

    /**
     * adds a selection.
     * @param keyToWaitFor key
     * @param lineToPrint line
     * @param whatToReturn return value
     */
    public void addSelection(String keyToWaitFor, String lineToPrint, T whatToReturn) {
        this.selectionOptions.add(new MenuSelection<T>(keyToWaitFor, lineToPrint, whatToReturn));
    }


    @Override
    public T getStatus() {
        return this.chosen;
    }

    /**
     * Add sub menu.
     *
     * @param key     the key
     * @param message the message
     * @param subMenu the sub menu
     */
    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        Task<Void> submenuTask = new Task<Void>() {
            @Override
            public Void run() {
                runner.run(subMenu);
                // wait for user selection
                Task<Void> task = (Task<Void>) subMenu.getStatus();
                task.run();
                ((MenuAnimation<Task<Void>>) subMenu).setRunner();
                return null;
            }
        };
        addSelection(key, message, (T) submenuTask);
    }

    /**
     * Sets runner.
     */
    public void setRunner() {
        this.stop = false;
    }


    /**
     * draw one frame of he animation.
     *
     * @param d  the drawsurface
     * @param dt the dt
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        //draw the background
        //draw the options

        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.BLACK);
        d.drawText(51, 50, this.title, 32);

        d.setColor(Color.YELLOW);
        d.drawText(50, 50, this.title, 32);


        for (int i = 0; i < this.selectionOptions.size(); ++i) {
            int optionX = 100;
            int optionY = 120 + i * 40;
            String optionText = "(" + (String) this.selectionOptions.get(i).getKeyToWaitFor() + ") "
                    + (String) this.selectionOptions.get(i).getLineToPrint();
            d.setColor(Color.BLACK);
            d.drawText(optionX + 1, optionY, optionText, 24);
            d.drawText(optionX - 1, optionY, optionText, 24);
            d.drawText(optionX, optionY + 1, optionText, 24);
            d.drawText(optionX, optionY - 1, optionText, 24);
            d.setColor(Color.GREEN);
            d.drawText(optionX, optionY, optionText, 24);
        }


        for (MenuSelection<T> option : this.selectionOptions) {
            if (this.sensor.isPressed(option.getKeyToWaitFor())) {
                this.chosen = option.getWhatToReturn();
                this.stop = true;
            }

        }


    }

    /**
     * checks the stop condition for the animation.
     *
     * @return true if the animation should run, else false
     */
    @Override
    public boolean shouldStop() {
        //set the stop for the next run
        if (this.stop) {
            this.stop = false;
            return true;
        }
        return false;
    }


}
