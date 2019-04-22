package gameobjects;

import biuoop.DrawSurface;
import geometry.Rectangle;
import sprite.RectBackground;
import sprite.RectColorBackground;

import java.awt.Color;
import java.util.Map;

/**
 * The type Frame block.
 */
public class FrameBlock extends Block {
    /**
     * Instantiates a new Frame block.
     *
     * @param shape1     the shape 1
     * @param color1     the color 1
     * @param hitPoints1 the hit points 1
     * @param m          the m
     */
    public FrameBlock(Rectangle shape1, Color color1, int hitPoints1, Map<Integer, RectBackground> m) {
        super(shape1, color1, Integer.MAX_VALUE, m);
    }

    /**
     * draws the block to screen.
     *
     * @param surf the drawsurface
     */
    @Override
    public void drawOn(DrawSurface surf) {
        super.getShape().drawRect(surf, new RectColorBackground(Color.lightGray));
    }
}
