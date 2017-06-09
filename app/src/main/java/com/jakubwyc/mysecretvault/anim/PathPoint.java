package com.jakubwyc.mysecretvault.anim;

/**
 * A class that holds information about a location and how the path should get to that
 * location from the previous path location (if any). Any PathPoint holds the information for
 * its location as well as the instructions on how to traverse the preceding interval from the
 * previous location.
 */
public class PathPoint {

    /**
     * The possible path operations that describe how to move from a preceding PathPoint to the
     * location described by this PathPoint.
     */
    public static final int MOVE = 0;
    public static final int LINE = 1;
    public static final int CURVE = 2;

    /**
     * The location of this PathPoint
     */
    public float x;
    public float y;

    /**
     * The first control point, if any, for a PathPoint of type CURVE
     */
    float control0X, control0Y;

    /**
     * The second control point, if any, for a PathPoint of type CURVE
     */
    float control1X, control1Y;

    /**
     * The motion described by the path to get from the previous PathPoint in an AnimatorPath
     * to the location of this PathPoint. This can be one of MOVE, LINE, or CURVE.
     */
    int operation;


    /**
     * Line/Move constructor
     */
    private PathPoint(int operation, float x, float y) {
        this.operation = operation;
        this.x = x;
        this.y = y;
    }

    /**
     * Curve constructor
     */
    private PathPoint(float c0X, float c0Y, float c1X, float c1Y, float x, float y) {
        control0X = c0X;
        control0Y = c0Y;
        control1X = c1X;
        control1Y = c1Y;
        this.x = x;
        this.y = y;
        operation = CURVE;
    }

    /**
     * Constructs and returns a PathPoint object that describes a line to the given xy location.
     */
    public static PathPoint lineTo(float x, float y) {
        return new PathPoint(LINE, x, y);
    }

    /**
     * Constructs and returns a PathPoint object that describes a cubic B�zier curve to the
     * given xy location with the control points at c0 and c1.
     */
    public static PathPoint curveTo(float c0X, float c0Y, float c1X, float c1Y, float x, float y) {
        return new PathPoint(c0X, c0Y, c1X, c1Y, x, y);
    }

    /**
     * Constructs and returns a PathPoint object that describes a discontinuous move to the given
     * xy location.
     */
    public static PathPoint moveTo(float x, float y) {
        return new PathPoint(MOVE, x, y);
    }
}