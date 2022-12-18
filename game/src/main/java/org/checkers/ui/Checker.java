package org.checkers.ui;

import javafx.scene.shape.Ellipse;

public class Checker extends Ellipse {
    int row;
    int column;
    public Checker(double i, double j, double Size, int row, int column) {
        super(i, j, Size, Size);
        this.row = row;
        this.column = column;
    }
    public void Move(int row, int column) {
        setCenterX(62.0 + column*125.0);
        setCenterY(62.0 + row*125.0);
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
