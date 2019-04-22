package game;

import collision.Collidable;
import collision.CollisionInfo;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * game.GameEnvironment class.
 * defines methods for a game environment
 */
public class GameEnvironment {
    //list of objects that we can collide with
    private List<Collidable> collidabels = new ArrayList<Collidable>();

    /**
     * constructor (only allocates a list of collidables).
     */
    public GameEnvironment() {
    }


    /**
     * add the given collidable to the environment.
     *
     * @param c a colldibale to add
     */
    public void addCollidable(Collidable c) {
        this.collidabels.add(c);
    }

    /**
     * Remove collidable.
     *
     * @param c the c
     */
    public void removeCollidable(Collidable c) {
        this.collidabels.remove(c);
    }


    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidabels
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     *
     * @param trajectory the course of the ball
     * @return info about the collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        ArrayList<Point> allIntersOnTrajectory = new ArrayList<Point>();
        //find all inters on trajcetory
        for (Collidable c : this.collidabels) {
            Point curClosestInter = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (curClosestInter == null) {
                continue;
            }
            allIntersOnTrajectory.add(curClosestInter);
        }
        //if there are no inters
        if (allIntersOnTrajectory.isEmpty()) {
            return null;
        }
        //find the closest collision
        Point curClosestCollision = new Point(allIntersOnTrajectory.get(0).getX(), allIntersOnTrajectory.get(0).getY());
        for (Point p : allIntersOnTrajectory) {
            double curDist = p.distance(trajectory.start());
            if (curDist <= curClosestCollision.distance(trajectory.start())) {
                curClosestCollision = p;
            }
        }
        double pX = curClosestCollision.getX();
        double pY = curClosestCollision.getY();
        Collidable collidedWith = null;
        //check on what rectangle the collision point is placed
        for (Collidable c : this.collidabels) {
            Rectangle rec = c.getCollisionRectangle();
            if (rec.getBottomLine().isOnLine(pX, pY) || rec.getTopLine().isOnLine(pX, pY)
                    || rec.getRightLine().isOnLine(pX, pY) || rec.getLeftLine().isOnLine(pX, pY)) {
                collidedWith = c;
                break;
            }
        }
        //collision but the object is not found
        if (collidedWith == null) {
            return null;
            //throw new RuntimeException("found a collision but unable to find the object we collided with");
        }
        return new CollisionInfo(curClosestCollision, collidedWith);
    }

}