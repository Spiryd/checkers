package org.checkers.ui;

import javafx.scene.shape.Ellipse;

/**
 * The type Checker.
 */
public class Checker extends Ellipse {
     private int row;
     private int column;
     private double squareSize;


    /**
     * Instantiates a new Checker.
     *
     * @param i      the x coordinate on screen
     * @param j      the y coordinate on screen
     * @param Size   the size of the checker
     * @param row    the row of the board
     * @param column the column of the board
     */
    public Checker(double i, double j, double Size, int row, int column, double squareSize) {
        super(i, j, Size, Size);
        this.row = row;
        this.column = column;
        this.squareSize = squareSize;

    }

    /**
     * Moves the Checker on screen
     *
     * @param column the column
     * @param row    the row
     */
    public void Move(int column, int row) {
        setCenterX((squareSize/2) + column*squareSize);
        setCenterY((squareSize/2)  + row*squareSize);
        this.row = row;
        this.column = column;
    }

    /**
     * Gets row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets column.
     *
     * @return the column
     */
    public int getColumn() {
        return column;
    }
}
