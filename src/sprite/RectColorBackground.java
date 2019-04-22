package sprite;

import biuoop.DrawSurface;
import geometry.Rectangle;

import java.awt.Color;

/**
 * The type Rect color background.
 */
public class RectColorBackground implements RectBackground {
    private Color color;

    /**
     * Instantiates a new Rect color background.
     *
     * @param color the color
     */
    public RectColorBackground(Color color) {
        this.color = color;
    }

    @Override
    public void drawOnRect(DrawSurface surf, Rectangle rect) {
        surf.setColor(this.color);
        int x1 = (int) rect.getUpperLeft().getX();
        int y1 = (int) rect.getUpperLeft().getY();
        int height1 = (int) rect.getHeight();
        int width1 = (int) rect.getWidth();
        surf.fillRectangle(x1, y1, width1, height1);
    }
}
