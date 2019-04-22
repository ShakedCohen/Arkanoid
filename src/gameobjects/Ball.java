package gameobjects;

import biuoop.DrawSurface;
import collision.Collidable;
import collision.CollisionInfo;
import collision.HitListener;
import collision.HitNotifier;
import game.GameEnvironment;
import game.GameLevel;
import geometry.Line;
import geometry.Point;
import sprite.Sprite;

import java.awt.Color;
import java.util.ArrayList;

/**
 * gameobjects.Ball class.
 * enables actions with balls (gui)
 */
public class Ball implements Sprite, HitNotifier {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private GameEnvironment ballGameEnv;
    private ArrayList<HitListener> hitListeners = new ArrayList<HitListener>();

    // constructors ahead

    /**
     * constructor.
     * default walls: right:400 bot:400 left:0 top:0
     *
     * @param center the center of the ball
     * @param r      the radius of the ball
     * @param color  the color of the ball
     */
    public Ball(geometry.Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
    }


    /**
     * constructor.
     * default walls: right:400 bot:400 left:0 top:0
     *
     * @param x     the x middle
     * @param y     the y middle
     * @param r     the radius
     * @param color the color
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.center = new geometry.Point(x, y);
        this.radius = r;
        this.color = color;
    }

    //with default wall limits

    /**
     * constructor.
     * default walls: right:gui width bot:gui gui height left:0 top:0
     *
     * @param center      the center of the ball
     * @param r           the radius
     * @param color       the color
     * @param ballGameEnv ballGameEnv environment
     */
    public Ball(geometry.Point center, int r, java.awt.Color color, GameEnvironment ballGameEnv) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.ballGameEnv = ballGameEnv;
    }

    /**
     * constructor.
     * default walls: right:gui width bot:gui gui height left:0 top:0
     *
     * @param x           the x middle
     * @param y           the y middle
     * @param r           the radius
     * @param color       the color
     * @param ballGameEnv ballGameEnv environment
     */
    public Ball(double x, double y, int r, java.awt.Color color, GameEnvironment ballGameEnv) {
        this.center = new geometry.Point(x, y);
        this.radius = r;
        this.color = color;
        this.ballGameEnv = ballGameEnv;
    }

    // accessors

    /**
     * Gets x.
     *
     * @return the center x val.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Gets y.
     *
     * @return the center y val.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Gets center.
     *
     * @return the center point.
     */
    public geometry.Point getCenter() {
        return this.center;
    }

    /**
     * Gets velocity.
     *
     * @return the velocity of the ball.
     */
    public Velocity getVelocity() {
        if (this.velocity == null) {
            throw new RuntimeException("no velocity declared");
        }
        return this.velocity;
    }

    /**
     * setter to the velocity.
     *
     * @param v the velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Gets radius.
     *
     * @return the radius.
     */
    public int getRadius() {
        return this.radius;
    }

    /**
     * Gets size.
     *
     * @return the area of the ball.
     */
    public int getSize() {
        return (int) (Math.PI * Math.pow(this.radius, 2));
    }

    /**
     * Gets color.
     *
     * @return the color.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface the draw surface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.black);
        surface.drawCircle(this.getX(), this.getY(), this.radius);
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
    }

    /**
     * setter to the velocity.
     *
     * @param dx dx val of the velocity
     * @param dy dy val of the velocity
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    //velocity based on ball size

    /**
     * default setter to the velocity.
     * gives a ball a default speed based on his radius (bigger balls are slower)
     * the formula is dx=50 / this.radius, dy= 50 / this.radius + 2
     * all balls with bigger radius than 50 are set to dx=1 dy=1
     */
    public void setVelocity() {
        //proportion between radius and speed(bigger radius results in slower velocity)
        //balls bigger than 50 are fixed slow velocity
        if (this.radius >= 50) {
            this.velocity = new Velocity(1, 1);
            return;
        }
        this.velocity = new Velocity(50 / this.radius, 50 / this.radius + 2);
    }

    /**
     * ball timePassed method.
     * find the next trajectory, check if it collides with a collidable
     * if collides, notify the object and change velocity
     * if not, advance the center to the end of the trajectory
     * fixed the problem that a ball gets inside a paddle.
     * @param dt the dt
     */
    public void timePassed(double dt) {
        Velocity relativeVelocity = new Velocity(this.velocity.getDx() * dt, this.velocity.getDy() * dt);
        // the new thoretical center

        //geometry.Point newCenter = this.getVelocity().applyToPoint(this.center);
        geometry.Point newCenter = relativeVelocity.applyToPoint(this.center);

        //find the trajectory
        Line trajectory = new Line(this.center, newCenter);
        Velocity newVelocity;
        // if the trajectory hasnt a collision
        if (this.ballGameEnv.getClosestCollision(trajectory) == null) {
            //this.center = this.getVelocity().applyToPoint(this.center);
            this.center = relativeVelocity.applyToPoint(this.center);
            return;
        }
        // find the closest collisionPoint and collisionObject
        CollisionInfo closestCol = this.ballGameEnv.getClosestCollision(trajectory);
        geometry.Point collisionPoint = closestCol.collisionPoint();
        Collidable object = closestCol.collisionObject();
        // if ball center(before hit) is inside a collidable object and this abject is a paddle
        if (object.getCollisionRectangle().insideRectangle(this.center) && object instanceof Paddle) {
            // place the ball on top of the paddle
            this.center = new geometry.Point(this.center.getX(),
                    this.center.getY() - object.getCollisionRectangle().getHeight());
        } else {
            // move the ball just slightly before the hit point
            this.center.setX(collisionPoint.getX() - 0.01 * this.velocity.getDx());
            this.center.setY(collisionPoint.getY() - 0.01 * this.velocity.getDy());
        }
        // notify the hit object that a collision occurred and update the velocity
        this.velocity = object.hit(this, collisionPoint, this.velocity);
    }

    /**
     * add the ball to the game.
     *
     * @param gameLevelObj the game
     */
    public void addToGame(GameLevel gameLevelObj) {
        gameLevelObj.addSprite(this);
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
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl listener to remove
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Remove from game.
     *
     * @param gameLevel the game level
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }

}

