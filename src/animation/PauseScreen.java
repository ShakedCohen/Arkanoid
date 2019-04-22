package animation;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The type Pause screen.
 */
public class PauseScreen implements Animation {

    /**
     * Instantiates a new Pause screen.
     */
    public PauseScreen() {
    }


    /**
     * draws one frame.
     * @param d  the drawsurface
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.black);

        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);


    }

    /**
     * @return if animation should stop.
     */
    public boolean shouldStop() {
        return false;
    }
}