package org.checkers.model;

import java.util.ArrayList;

public class Board {
    private ArrayList<Piece> pieces;

    private int side = 8;

    public Board(){
        this.pieces.addAll(setUpBlack());
        this.pieces.addAll(setUpWhite());
    }
    private ArrayList<Piece> setUpWhite(){
        ArrayList<Piece> toBeSet = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            toBeSet.add(new Piece((2*i)+1, 0, Hue.WHITE));
            toBeSet.add(new Piece((2*i)+1, 2, Hue.WHITE));
        }
        for (int i = 0; i < 4; i++) {
            toBeSet.add(new Piece(2*i, 1, Hue.WHITE));
        }
        return toBeSet;
    }

    private ArrayList<Piece> setUpBlack(){
        ArrayList<Piece> toBeSet = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            toBeSet.add(new Piece(2*i, 7, Hue.BLACK));
            toBeSet.add(new Piece(2*i, 5, Hue.BLACK));
        }
        for (int i = 0; i < 4; i++) {
            toBeSet.add(new Piece((2*i)+1, 6, Hue.BLACK));
        }
        return toBeSet;
    }

    public void movePiece(int initX, int initY, int newX, int newY){
        int pieceId;
        //identifies the piece
        for (int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            if(piece.getPosX() == initX && piece.getPosY() == initY){
                pieceId = i;
                break;
            }
        }

        //checks move if in bounds
        if (!((newX < 0 || newX > 7) || (newY < 0 || newY > 7))) {

        }

    }


    private boolean checkIfNearBoarder(int x, int y){
        if(x == 0 || x == 7){
            return true;
        }
        return y == 0 || y == 7;
    }

}
