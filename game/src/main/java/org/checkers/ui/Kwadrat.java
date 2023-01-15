package org.checkers.ui;

import javafx.scene.shape.Rectangle;

/**
 * Square to build a boards
 */
public class Kwadrat extends Rectangle {
    /**
     * Instantiates a new Kwadrat.
     *
     * @param i    the x coordinate on board
     * @param j    the y coordinate on board
     * @param Size the size of the Kwadrat
     */
//   boolean checker_on = false;
    public Kwadrat(double i, double j, double Size) {
        super(i, j, Size, Size);
    }
}
