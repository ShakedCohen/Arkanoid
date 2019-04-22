package collision;

import gameobjects.Ball;
import gameobjects.Velocity;
import geometry.Point;
import geometry.Rectangle;

/**
 * collision.Collidable interface.
 * holding the methods that a collidable class must implement
 */
public interface Collidable {

    /**
     * Gets collision rectangle.
     *
     * @return Return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();


    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit
     * (based on the force the object inflicted on us).
     *
     * @param hitter          the hitter
     * @param collisionPoint  the col point
     * @param currentVelocity the cur velocity
     * @return new velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}