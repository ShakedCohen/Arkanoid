package sprite;

import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * The interface Rect background.
 */
public interface RectBackground {

    /**
     * Draw on rect.
     *
     * @param d    the d
     * @param rect the rect
     */
    void drawOnRect(DrawSurface d, Rectangle rect);
}
