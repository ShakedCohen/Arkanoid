package collision;

import geometry.Point;

/**
 * collsionInfo class.
 * defines what info a collision holds
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * constructor for the collisionInfo.
     *
     * @param collisionPoint1  the point
     * @param collisionObject1 the object the collided with
     */
    public CollisionInfo(Point collisionPoint1, Collidable collisionObject1) {
        this.collisionPoint = collisionPoint1;
        this.collisionObject = collisionObject1;
    }

    /**
     * gets the point at which the collision occurs.
     *
     * @return the point
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * gets the collidable object involved in the collision.
     *
     * @return the object
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}