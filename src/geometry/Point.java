package geometry;

import java.util.Random;

/**
 * class geometry.Point. A point has x and y value(doubles)
 * every point has useful methods
 * a static method that generates random point for a ball
 */
public class Point {
    //x value member
    private double x;
    //y value member
    private double y;

    /**
     * sets y val.
     * @param yVal y val
     */
    public void setY(double yVal) {
        this.y = yVal;
    }

    /**
     * sets x val.
     * @param xVal x val
     */
    public void setX(double xVal) {
        this.x = xVal;
    }

    /**
     * constructor.
     * @param xVal x val
     * @param yVal y val
     */
    public Point(double xVal, double yVal) {
        this.x = xVal;
        this.y = yVal;
    }

    /**
     * calc distance.
     * @param other other point to calc distance to
     * @return distance between our object and other
     */
    public double distance(Point other) {
        double x1 = this.x;
        double y1 = this.y;
        double x2 = other.x;
        double y2 = other.y;
        //calc the distance
        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }



    /**
     * equals -- return true is the points are equal, false otherwise.
     * @param other other point to check if equal
     * @return return true is the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        if ((this.getX() == other.getX()) && (this.getY() == other.getY())) {
            return true;
        }
        return false;
    }

    /**
     * @return the x val.
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return the y val.
     */
    public double getY() {
        return this.y;
    }

    /**
     * calculates slope between 2 points. return Double object(can be null).
     * @param other other point
     * @return the slope (null if the line is vertical)
     */
    public Double calcSlope(Point other) {
        Double slope;
        double x1 = this.getX();
        double y1 = this.getY();
        double x2 = other.getX();
        double y2 = other.getY();
        //vertical line
        if (x2 - x1 == 0) {
            slope = null;
            return slope;
        }
        //calc slope
        slope = ((y2 - y1) / (x2 - x1));
        return slope;
    }

    /**
     * generates random point in a given wall limits and radius of the ball.
     * you can ignore the radius part by putting 0 in the r param
     * @param rightWall the right wall
     * @param leftWall the left wall
     * @param topWall the top wall
     * @param bottomWall the bottom wall
     * @param r the radius of the ball
     * @return random point in limits
     */
    public static Point generateRandomPointForBall(int rightWall, int leftWall, int topWall, int bottomWall, int r) {
        Random rand = new Random();
        //value between the rightWall(or botWall)+r and leftWall(or topWall)-r
        int x = Math.max(leftWall + r, rand.nextInt(rightWall - r));
        int y = Math.max(topWall + r, rand.nextInt(bottomWall - r));
        return new Point(x, y);
    }
}