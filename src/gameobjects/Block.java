package gameobjects;

import biuoop.DrawSurface;
import collision.Collidable;
import collision.HitListener;
import collision.HitNotifier;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import sprite.RectBackground;
import sprite.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * gameobjects.Block class.
 * collidable and sprite.Sprite
 * implements a block and methods that a block can use
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private Rectangle shape;
    private int hitPoints;
    private ArrayList<HitListener> hitListeners = new ArrayList<HitListener>();
    private Color stroke;
    private Map<Integer, RectBackground> fillForHP;


    /**
     * Instantiates a new Block.
     *
     * @param shape1     the shape 1
     * @param stroke1    the stroke 1
     * @param hitPoints1 the hit points 1
     * @param fillForHP1 the fill for hp 1
     */
    public Block(Rectangle shape1, Color stroke1, int hitPoints1,
//                 List<HitListener> listeners,
                 Map<Integer, RectBackground> fillForHP1) {
        this.shape = shape1;
        this.hitPoints = hitPoints1;
        //this.hitListeners.addAll(listeners);
        this.stroke = stroke1;
        this.fillForHP = fillForHP1;
    }

    /**
     * Gets hit listeners.
     *
     * @return the hit listeners
     */
    public ArrayList<HitListener> getHitListeners() {
        return hitListeners;
    }

    /**
     * Gets hit points.
     *
     * @return the hit points
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * @return Return the "collision shape" of the object.
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }


    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit
     * (based on the force the object inflicted on us).
     *
     * @param hitter          the hitter ball
     * @param collisionPoint  the col point
     * @param currentVelocity the cur velocity
     * @return the new velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // the newVelocity after collision - first set to our current velocity
        Velocity newVelocity = new Velocity(currentVelocity);
        // if hit the left corner
        if (collisionPoint.getX() == this.shape.getUpperLeft().getX()) {
            newVelocity.setDx(-currentVelocity.getDx());
        }
        // if hit the top corner
        if (collisionPoint.getY() == this.shape.getUpperLeft().getY()) {
            newVelocity.setDy(-currentVelocity.getDy());
        }
        // if hit the right corner
        if (collisionPoint.getX() == this.shape.getUpperLeft().getX() + this.shape.getWidth()) {
            newVelocity.setDx(-currentVelocity.getDx());
        }
        // if hit the bottom corner
        if (collisionPoint.getY() == this.shape.getUpperLeft().getY() + this.shape.getHeight()) {
            newVelocity.setDy(-currentVelocity.getDy());
        }

        // decrease hitPoints by one, since the object has been hit
        this.decHitPoints();
        this.notifyHit(hitter);
        return newVelocity;

    }

    /**
     * decreases the hit points (only if greater or equal 1).
     */
    private void decHitPoints() {
        //  if (this.hitPoints >= 1) {
        hitPoints--;
        // }
    }

    /**
     * draws the block to screen.
     *
     * @param surf the drawsurface
     */
    public void drawOn(DrawSurface surf) {
        //draws the rect
        try {
            RectBackground background = this.fillForHP.get(this.hitPoints);
            this.shape.drawRect(surf, background);
        } catch (Exception e) {
            this.shape.drawRect(surf, this.fillForHP.get(1));
        }
        if (this.stroke != null) {
            this.shape.drawRectEdges(surf, this.stroke);
        }
    }

    /**
     * passes time.
     * @param dt the dt
     */
    public void timePassed(double dt) {
    }

    /**
     * add block to gameLevel.
     *
     * @param gameLevel the gameLevel obj
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.addCollidable(this);
    }

    /**
     * Remove from game.
     *
     * @param gameLevel the game level
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl listener to add
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Gets fill for hp.
     *
     * @return the fill for hp
     */
    public Map<Integer, RectBackground> getFillForHP() {
        return fillForHP;
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl listener to remove
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * first dec hit points of block,then notify.
     *
     * @param hitter the hitter ball
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }


    /**
     * Gets shape.
     *
     * @return the shape
     */
    public Rectangle getShape() {
        return shape;
    }
}

