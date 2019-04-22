package gameobjects;

import biuoop.DrawSurface;
import collision.HitListener;
import collision.HitNotifier;
import geometry.Point;

import java.awt.Color;
import java.util.HashMap;

/**
 * The type Death block.
 */
public class DeathBlock extends Block implements HitNotifier {
    /**
     * constructor for block.
     *
     * @param shape1     rectangle
     * @param color1     his color
     * @param hitPoints1 his hitpoints
     * @param listener   the listener
     */
    public DeathBlock(geometry.Rectangle shape1, Color color1, int hitPoints1, HitListener listener) {
        super(shape1, color1, hitPoints1, new HashMap<>());
        this.addHitListener(listener);
    }

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit
     * (based on the force the object inflicted on us).
     *
     * @param hitter the hitter ball
     * @param collisionPoint  the col point
     * @param currentVelocity the cur velocity
     * @return the new velocity
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //super.getHitListeners().;
        return super.hit(hitter, collisionPoint, currentVelocity);

    }

    /**
     * draws the block to screen.
     *
     * @param surf the drawsurface
     */
    @Override
    public void drawOn(DrawSurface surf) {

    }
}
