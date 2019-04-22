package animation;

import biuoop.DrawSurface;
import gameobjects.Counter;

import java.awt.Color;

/**
 * The type End screen.
 */
public class EndScreen implements Animation {

    private Counter score;
    private Counter numberOfLives;


    /**
     * Instantiates a new End screen.
     *
     * @param score         the score
     * @param numberOfLives the number of lives
     */
    public EndScreen(Counter score, Counter numberOfLives) {
        this.score = score;
        this.numberOfLives = numberOfLives;
    }


    /**
     * draws one frame.
     * @param d  the drawsurface
     * @param dt the dt
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.black);

        if (this.numberOfLives.getValue() > 0) {
            d.drawText(10, d.getHeight() / 2, "You Win! Your score is " + this.score.getValue(), 32);
        } else {
            d.drawText(10, d.getHeight() / 2, "Game Over. Your score is " + this.score.getValue(), 32);
        }


    }

    /**
     * checks the stop condition for the animation.
     *
     * @return true if the animation should run, else false
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}
