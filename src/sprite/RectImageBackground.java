package sprite;

import biuoop.DrawSurface;
import geometry.Rectangle;

import java.awt.Image;

/**
 * The type Rect image background.
 */
public class RectImageBackground implements RectBackground {
    private Image image;

    /**
     * Instantiates a new Rect image background.
     *
     * @param image the image
     */
    public RectImageBackground(Image image) {
        this.image = image;
    }

    @Override
    public void drawOnRect(DrawSurface d, Rectangle rect) {
        d.drawImage((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(), this.image);
    }
}
