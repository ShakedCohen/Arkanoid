package indicators;

import biuoop.DrawSurface;
import game.Constants;
import gameobjects.Counter;
import sprite.Sprite;

import java.awt.Color;

/**
 * The type Lives indicator.
 */
public class LivesIndicator implements Sprite {
    private Counter curLives;

    /**
     * Instantiates a new Lives indicator.
     *
     * @param curLives the cur lives
     */
    public LivesIndicator(Counter curLives) {
        this.curLives = curLives;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the drawsurface
     */
    @Override
    public void drawOn(DrawSurface d) {

        final int panelHeight = Constants.SCORE_PANEL_HEIGHT;

        d.setColor(Color.black);
        int x = (int) (d.getWidth() * 0.25);
        int y = (int) (panelHeight * 0.75);
        String s = "Lives: " + this.curLives.getValue();
        int size = 15;
        d.drawText(x, y, s, size);
    }

    /**
     * passes time.
     * @param dt the dt
     */
    @Override
    public void timePassed(double dt) {

    }
}
