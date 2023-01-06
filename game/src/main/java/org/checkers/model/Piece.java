package org.checkers.model;


/**
 * Representation of a piece in checkers essentially the model
 */
public class Piece {
    private int posX;
    private int posY;
    private Color color;
    private Rank state;

    /**
     * Instantiates a new Piece.
     *
     * @param posX  the pos x
     * @param posY  the pos y
     * @param color the color
     */
    public Piece(int posX, int posY, Color color){
        this.posX = posX;
        this.posY = posY;
        this.color = color;
        this.state = Rank.MAN;
    }

    /**
     * Set coords.
     *
     * @param posX the pos x
     * @param posY the pos y
     */
    public void setCoords(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Promote to King
     */
    public void promote(){
        this.state = Rank.KING;
    }

    /**
     * Get rank.
     *
     * @return the rank
     */
    public Rank getState(){
        return this.state;
    }

    /**
     * Get pos x int.
     *
     * @return pos x int
     */
    public int getPosX(){
        return this.posX;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Get pos y int.
     *
     * @return pos y int
     */
    public int getPosY(){
        return this.posY;
    }
}
