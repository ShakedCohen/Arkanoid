package animation;

import biuoop.DrawSurface;


/**
 * The interface Animation.
 */
public interface Animation {

    /**
     * draw one frame of he animation.
     *
     * @param d  the drawsurface
     * @param dt the dt
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * checks the stop condition for the animation.
     *
     * @return true if the animation should run, else false
     */
    boolean shouldStop();
}