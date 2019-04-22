package gameobjects;

import geometry.Point;

/**
 * gameobjects.Velocity class.
 * gameobjects.Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    private double dx;
    private double dy;


    /**
     * constructor.
     *
     * @param dx promotion in x
     * @param dy promotion in y
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * constructor for velocity.
     *
     * @param v the velocity
     */
    public Velocity(Velocity v) {
        this.dx = v.getDx();
        this.dy = v.getDy();
    }
    //getters and setters

    /**
     * creates a new velocity based on angle and speed (angle 0 up).
     *
     * @param angle the angle
     * @param speed the total speed
     * @return the new generated velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        //to radians
        double radiansAngle = Math.toRadians(angle);
        //based on trigonometric calc
        double dx = (Math.sin(radiansAngle) * speed);
        double dy = (Math.cos(radiansAngle) * -speed);
        return new Velocity(dx, dy);
    }

    /**
     * Gets dx.
     *
     * @return return this dx.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Sets dx.
     *
     * @param dxVal new dx.
     */
    public void setDx(double dxVal) {
        this.dx = dxVal;
    }

    /**
     * Gets dy.
     *
     * @return return the dy.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Sets dy.
     *
     * @param dyVal new dy.
     */
    public void setDy(double dyVal) {
        this.dy = dyVal;
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p the point to apply to
     * @return the point with applied velocity
     */
    public Point applyToPoint(Point p) {
        Point applied = new Point(p.getX() + this.dx, p.getY() + this.dy);
        return applied;
    }

    /**
     * finds the size of a velocity Vector.
     *
     * @return the size
     */
    public double getVelocitySize() {
        return Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
    }
}