package animation;

import biuoop.DrawSurface;
import sprite.SpriteCollection;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

/**
 * The animation.CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) seconds, before
 * it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {

    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private boolean stop;
    private Long timeAtPreviousTimeUpdate = null;
    private int curDisplayTime;


    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
        this.curDisplayTime = countFrom;
    }

    /**
     * draws one frame.
     * @param d  the drawsurface
     * @param dt the dt
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        if (timeAtPreviousTimeUpdate == null) {
            this.timeAtPreviousTimeUpdate = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()); // timing
        }
        long curTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        if (curTime - timeAtPreviousTimeUpdate >= 1) {
            curDisplayTime--;
            this.timeAtPreviousTimeUpdate = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        }
        if (countFrom - curDisplayTime >= numOfSeconds + 1) {
            stop = true;
            curDisplayTime++;
        }
        //background
        this.gameScreen.drawAllOn(d);

        //display timer
        d.setColor(Color.red);
        int x = (d.getWidth() / 2);
        int y = (d.getHeight() / 2);
        String s = "" + (curDisplayTime);
        int size = 50;
        d.drawText(x, y, s, size);
    }

    /**
     * checks the stop condition for the animation.
     *
     * @return true if the animation should run, else false
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
