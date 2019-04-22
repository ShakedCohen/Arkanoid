package sprite;

import biuoop.DrawSurface;

/**
 * sprite.Sprite interface.
 * defines wat a sprite class should implement
 * a sprite is an object that can be drawn to screen
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d the drawsurface
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    void timePassed(double dt);
}