package indicators;

import biuoop.DrawSurface;
import game.Constants;
import gameobjects.Counter;
import geometry.Point;
import geometry.Rectangle;
import sprite.RectColorBackground;
import sprite.Sprite;

import java.awt.Color;

/**
 * The type score indicator.
 */
public class ScoreIndicator implements Sprite {
    private Counter curScore;

    /**
     * Instantiates a new score indicator.
     *
     * @param curScore the cur score
     */
    public ScoreIndicator(Counter curScore) {
        this.curScore = curScore;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the drawsurface
     */
    @Override
    public void drawOn(DrawSurface d) {
        final int panelHeight = Constants.SCORE_PANEL_HEIGHT;

        //score panel
        Rectangle rect = new Rectangle(new Point(0, 0), d.getWidth(), panelHeight);
        rect.drawRect(d, new RectColorBackground(Color.white));
        rect.drawRectEdges(d, Color.black);

        //score
        d.setColor(Color.black);
        int x = (int) (d.getWidth() * 0.45);
        int y = (int) (panelHeight * 0.75);
        String s = "score: " + this.curScore.getValue();
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
