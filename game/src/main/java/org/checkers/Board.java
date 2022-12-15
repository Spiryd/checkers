package org.checkers;

import java.util.ArrayList;

public class Board {
    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;

    private int side = 8;

    public Board(){
        blackPieces = setUpBlack();
        whitePieces = setUpWhite();
    }

    private ArrayList<Piece> setUpWhite(){
        ArrayList<Piece> toBeSet = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            toBeSet.add(new Piece((2*i)+1, 0));
            toBeSet.add(new Piece((2*i)+1, 2));
        }
        for (int i = 0; i < 4; i++) {
            toBeSet.add(new Piece(2*i, 1));
        }
        return toBeSet;
    }

    private ArrayList<Piece> setUpBlack(){
        ArrayList<Piece> toBeSet = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            toBeSet.add(new Piece(2*i, 7));
            toBeSet.add(new Piece(2*i, 5));
        }
        for (int i = 0; i < 4; i++) {
            toBeSet.add(new Piece((2*i)+1, 6));
        }
        return toBeSet;
    }

    public void movePiece(int initX, int initY, int newX, int newY){
        //checks if move is in bounds
        if (whitePieces.contains(new Piece(initX, initY)) || blackPieces.contains(new Piece(initX, initY))){
            if (!((newX < 0 || newX > 7) || (newY < 0 || newY > 7))) {

            }
        }
    }

}
