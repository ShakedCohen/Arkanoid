package geometry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


/**
 * geometry.Line class.
 * every line has start and end point (end is the one with a greater x value)
 */
public class Line {
    private Point start;
    private Point end;

    /**
     * constructor. makes sure end is the one with a greater x value.
     *
     * @param start first point
     * @param end   second point
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * constructor. makes sure end is the one with a greater x value.
     *
     * @param x1 first x val
     * @param y1 first y val
     * @param x2 second x val
     * @param y2 second y val
     */
    public Line(double x1, double y1, double x2, double y2) {
        Point curStart = new Point(x1, y1);
        Point curEnd = new Point(x2, y2);
        if (curStart.getX() <= curEnd.getX()) {
            this.start = curStart;
            this.end = curEnd;
        } else {
            this.start = curEnd;
            this.end = curStart;
        }
    }

    /**
     * @return the length of the line.
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * @return the middle point of the line.
     */
    public Point middle() {
        double midX = (this.start.getX() + this.end.getX()) / 2;
        double midY = (this.start.getY() + this.end.getY()) / 2;
        Point mid = new Point(midX, midY);
        return mid;
    }

    /**
     * @return the start point of the line (the one with the lower x val).
     */
    public Point start() {
        return this.start;
    }

    /**
     * @return end point of the line(the one with the greater x val).
     */
    public Point end() {
        return this.end;
    }

    /**
     * method that checks if there is an intersection between 2 points.
     *
     * @param other other line to check intersection with
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        if (this.intersectionWith(other) == null) {
            return false;
        }
        return true;
    }

    /**
     * finds intersection point between 2 lines.
     *
     * @param other second line
     * @return the intersection point if the lines intersect, null otherwise.
     */
    public Point intersectionWith(Line other) {
        Line line1 = this;
        Line line2 = other;
        //find the slope
        Double m1 = line1.lineSlope();
        Double m2 = line2.lineSlope();
        //if the lines are parallel/intersect at many points
        if (m1 == m2) {
            return null;
        }
        //if line1 is vertical
        if (m1 == null) {
            return line1.verticalInter(line2);
        } else if (m2 == null) {
            //if line2 is vertical
            return line2.verticalInter(line1);
        }
        //find coordinates
        double x1 = line1.start.getX();
        double x3 = line2.start.getX();
        double y1 = line1.start.getY();
        double y3 = line2.start.getY();
        //the mathematical formula to find the x intersection (if the lines were infinite)
        double xInter = (m1 * x1 - y1 - m2 * x3 + y3) / (m1 - m2);
        //check if the x inter is in the lines' range
        if ((!line1.isInXRange(xInter)) || (!line2.isInXRange(xInter))) {
            return null;
        }
        //find the y inter
        double yInter = m1 * (xInter - x1) + y1;
        Point intersection = new Point(xInter, yInter);
        intersection.setX(fixDeviation(intersection.getX(), 7));
        intersection.setY(fixDeviation(intersection.getY(), 7));
        return intersection;
    }

    //

    /**
     * equals -- return true is the lines are equal, false otherwise.
     *
     * @param other other line
     * @return true is the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        //remember! start is always the lower x val
        return (this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start));
    }

    /**
     * @return the slope of the line (can be null).
     */
    public Double lineSlope() {
        return this.start.calcSlope(this.end);
    }

    /**
     * checks if the given x in in the x range of the line.
     *
     * @param x given x val
     * @return true if in x range, false otherwise
     */
    public boolean isInXRange(double x) {
        //return ((x >= this.start.getX()) && (x <= this.end.getX()));
        return (x >= Math.min(this.start.getX(), this.end.getX())
                && (x <= Math.max(this.end.getX(), this.start.getX())));
    }

    /**
     * checks if the given y is in the range of the line.
     *
     * @param y the y val
     * @return true if in y range, false otherwise
     */
    public boolean isInYRange(double y) {
        return (y >= Math.min(this.start.getY(), this.end.getY()))
                && (y <= Math.max(this.start.getY(), this.end.getY()));
    }

    /**
     * checks if the x,y is on a given line segment.
     *
     * @param x x val
     * @param y y val
     * @return true if on line, false otherwise
     */
    public boolean isOnLine(double x, double y) {
        if (!this.isInXRange(x)) {
            return false;
        }
        if (this.getYValue(x) == null) {
            if (this.isInYRange(y)) {
                return true;
            }
            return false;
        }
        if (y != this.getYValue(x)) {
            return false;
        }
        return true;
    }

    /**
     * given x val, calc the y val on this line.
     *
     * @param xValue x val
     * @return the y val in point, null if the line is vertical
     * @throws RuntimeException if the x value is not on the line
     */
    public Double getYValue(double xValue) throws RuntimeException {
        if (!this.isInXRange(xValue)) {
            throw new RuntimeException("cant getYValue for x value that is not on the line");
        }
        Double slope = this.lineSlope();
        if (slope == null) {
            return null;
        }
        return slope * (xValue - this.start().getX()) + this.start().getY();
    }

    /**
     * calc intersection point of this line(a vertical line) and other line(cant be vertical).
     *
     * @param other other line to calc inter with
     * @return the inter point, null if there is no intersection
     */
    public Point verticalInter(Line other) {
        double verticalX = this.start.getX();
        //check if in range
        if (!other.isInXRange(verticalX)) {
            return null;
        }
        //calc y val
        double interY = other.getYValue(verticalX);
        //chekc if the point is in y range
        if (!this.isInYRange(interY)) {
            return null;
        }
        Point inter = new Point(verticalX, interY);
        //round the answer (to fix calculation deviation)
        inter.setX(fixDeviation(inter.getX(), 7));
        inter.setY(fixDeviation(inter.getY(), 7));
        return inter;

    }

    /**
     * finds the max Y point on line (can be start or end).
     *
     * @return the point with the max Y val
     */
    public Point maxYPointOnLine() {

        if (this.start().getY() > this.end().getY()) {
            return this.start();
        }
        return this.end();
    }

    /**
     * finds the min Y point on line (can be start or end).
     *
     * @return the point with the min Y val
     */
    public Point minYPointOnLine() {

        if (this.start().getY() < this.end().getY()) {
            return this.start();
        }
        return this.end();
    }



    /**
     *  If this line does not intersect with the rectangle, return null.
     *  Otherwise, return the closest intersection point to the
     *  start of the line.
     * @param rect the rectangle
     * @return the closest intersection point to the start of the line
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> interPoints = rect.intersectionPoints(this);
        //no intersections
        if (interPoints.isEmpty()) {
            return null;
        }
        //1 intersection
        if (interPoints.size() == 1) {
            return interPoints.get(0);
        }
        //more than 1 intersection
        Point closestInter = interPoints.get(0);
        for (int i = 0; i < interPoints.size(); i++) {
            //find shortest distance
            if (interPoints.get(i).distance(this.start) <= closestInter.distance(this.start)) {
                closestInter = interPoints.get(i);
            }
        }
        return closestInter;
    }

    /**
     * rounds an answer (to fix calculation deviation).
     * @param value the value to round
     * @param places how many places to round
     * @return the new rounded value
     */
    private double fixDeviation(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        //use the bigDecimal class
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * method that finds the region of a point on a line. (regions between 1-5).
     * horizantal ONLY!
     * @param colPoint the point
     * @return the region (1-5) or null if point is not on the line
     */
    public Integer findRegion(Point colPoint) {
        //if point is not on line (our use -if collided in paddle but not on top)
        if (!this.isOnLine(colPoint.getX(), colPoint.getY())) {
            return null;
        }
        //find width of a region
        double fifthOfPaddleSize = (this.start().distance(this.end())) / 5;
        //set the start region to be the first one
        double curStartX = Math.min(this.start.getX(), this.end.getX());
        double colPointX = colPoint.getX();
        //find the region
        for (int i = 1; i <= 5; i++) {
            if (colPointX >= curStartX && colPointX <= curStartX + fifthOfPaddleSize) {
                return i;
            }
            curStartX += fifthOfPaddleSize;
        }
        //shouldnt get here
        throw new RuntimeException("x cant be both on line and not on line(sort of) -check your mistake");
    }
}




