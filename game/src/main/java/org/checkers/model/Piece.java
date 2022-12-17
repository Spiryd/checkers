package org.checkers.model;


public class Piece {
    private int posX;
    private int posY;
    private Hue hue;
    private State state;

    public Piece(int posX, int posY, Hue hue){
        this.posX = posX;
        this.posY = posY;
        this.hue = hue;
        this.state = State.MAN;
    }

    public void setCoords(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void promote(){
        this.state = State.KING;
    }

    public State getState(){
        return this.state;
    }

    public int getPosX(){
        return this.posX;
    }

    public Hue getColor() {
        return this.hue;
    }

    public int getPosY(){
        return this.posY;
    }
}
