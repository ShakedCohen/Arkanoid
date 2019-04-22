package sprite;

import biuoop.DrawSurface;
import game.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * sprite.SpriteCollection class.
 * defines a collection of sprites - abjects that can be drawn to screen
 * implements funcs on a collection of sprites
 */
public class SpriteCollection {
    private List<Sprite> sprites = new ArrayList<Sprite>();

    /**
     * add a sprite to the collection.
     *
     * @param s a sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Remove sprite.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        for (int i = 0; i < this.sprites.size(); i++) {
            this.sprites.get(i).timePassed(Constants.DT_VAL);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d the drawsurface to draw on
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}