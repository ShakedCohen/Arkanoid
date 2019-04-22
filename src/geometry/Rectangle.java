package geometry;

import biuoop.DrawSurface;
import sprite.RectBackground;

import java.awt.Color;
import java.util.ArrayList;

/**
 * rectangle class. defines a rectangle and his functions.
 */
public class Rectangle {

    private Point upperLeft;
    private double width;
    private double height;

    /**
     * consructor - Create a new rectangle with location and width/height.
     *
     * @param upperLeft1 the upperleft point
     * @param width1     width of rect
     * @param height1    height of rect
     */
    public Rectangle(Point upperLeft1, double width1, double height1) {
        this.upperLeft = upperLeft1;
        this.width = width1;
        this.height = height1;
    }

    /**
     * Gets width.
     *
     * @return the width and height of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * set width.
     *
     * @param width1 the width
     */
    public void setWidth(double width1) {
        this.width = width1;
    }

    /**
     * Gets height.
     *
     * @return hte height.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * set height.
     *
     * @param height1 new height
     */
    public void setHeight(double height1) {
        this.height = height1;
    }

    /**
     * Gets upper left.
     *
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * sets a new upper left.
     *
     * @param upperLeft1 the new point
     */
    public void setUpperLeft(Point upperLeft1) {
        this.upperLeft = upperLeft1;
    }

    /**
     * returns the top line of the rect.
     *
     * @return top line
     */
    public Line getTopLine() {
        Point start = this.upperLeft;
        Point end = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        return new Line(start, end);
    }

    /**
     * gets the bottom line of the rect.
     *
     * @return bottom line
     */
    public Line getBottomLine() {
        Point start = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        Point end = new Point(this.upperLeft.getX() + this.getWidth(), this.upperLeft.getY() + this.height);
        return new Line(start, end);
    }

    /**
     * gets the left line opf the rect.
     *
     * @return left line
     */
    public Line getLeftLine() {
        Point start = this.upperLeft;
        Point end = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        return new Line(start, end);
    }

    /**
     * gets the right line of the rect.
     *
     * @return the right line
     */
    public Line getRightLine() {
        Point start = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        Point end = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
        return new Line(start, end);
    }

    //

    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     *
     * @param line the line
     * @return list of intersection points
     */
    public java.util.List intersectionPoints(Line line) {
        ArrayList<Point> interPoints = new ArrayList<>();

        if (line.isIntersecting(this.getLeftLine())) {
            interPoints.add(line.intersectionWith(this.getLeftLine()));
        }
        if (line.isIntersecting(this.getRightLine())) {
            interPoints.add(line.intersectionWith((this.getRightLine())));
        }
        if (line.isIntersecting(this.getTopLine())) {
            interPoints.add(line.intersectionWith(this.getTopLine()));
        }
        if (line.isIntersecting(this.getBottomLine())) {
            interPoints.add(line.intersectionWith(this.getBottomLine()));
        }
        return interPoints;
    }

    /**
     * draws a rectangle on screen.
     *
     * @param surf       the drawsurface
     * @param background the background
     */
    public void drawRect(DrawSurface surf, RectBackground background) {
        background.drawOnRect(surf, this);
    }

    /**
     * Draw rect edges.
     *
     * @param surf  the surf
     * @param color the color
     */
    public void drawRectEdges(DrawSurface surf, Color color) {
        surf.setColor(color);
        int x1 = (int) this.getUpperLeft().getX();
        int y1 = (int) this.getUpperLeft().getY();
        int height1 = (int) this.getHeight();
        int width1 = (int) this.getWidth();
        surf.drawRectangle(x1, y1, width1, height1);
    }


    /**
     * checks if a given point is inside the rect.
     *
     * @param point the point
     * @return true if inside rect, false otherwise
     */
    public Boolean insideRectangle(Point point) {
        if (this.upperLeft.getX() <= point.getX()
                && (this.upperLeft.getX() + this.width >= point.getX())
                && (this.upperLeft.getY() <= point.getY())
                && (this.upperLeft.getY() + this.height >= point.getY())) {
            return true;
        }
        return false;
    }
}