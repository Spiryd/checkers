package org.checkers.model;

import java.util.ArrayList;

public class Board {
    private ArrayList<Piece> pieces;
    private int maxCoord;

    public Board(int edgeLength){
        this.maxCoord = edgeLength - 1;
        this.pieces = new ArrayList<>();
        this.pieces.addAll(setUpPieces());
    }

    private ArrayList<Piece> setUpPieces(){
        ArrayList<Piece> toBeSet = new ArrayList<>();
        int rowsOfOnePieceColor = (maxCoord-1)/2;
        for(int i = 0; i <= maxCoord; i++){
            for(int j = 0; j <= maxCoord; j++){
                if((i+j) % 2 != 0){
                    if(i < rowsOfOnePieceColor){
                        toBeSet.add(new Piece(j, i, Color.RED));
                    }
                    else if(i > (rowsOfOnePieceColor + 1)){
                        toBeSet.add(new Piece(j, i, Color.WHITE));
                    }
                }
            }
        }
        return toBeSet;
    }

    public String movePiece(int initX, int initY, int newX, int newY){
        int pieceId = coordsToId(initX, initY);
        String signature;
        //identifies the piece
        if (pieceId == -1){
            return "-1";
        }
        Piece movingPiece = pieces.get(pieceId);
        //checks move if in bounds
        if (((newX < 0 || newX > maxCoord) || (newY < 0 || newY > maxCoord))) {
            return "-1";
        }
        //checks for Man moves
        if (movingPiece.getState() == Rank.MAN){
            signature = checkManMove(movingPiece.getColor(), initX, initY, newX, newY);
            if (!signature.equals("-1")){
                pieces.get(pieceId).setCoords(newX, newY);
                return signature;
            }
            else {
                return signature;
            }
        }else { //checks for King moves
            signature = checkKingMove(movingPiece.getColor(), initX, initY, newX, newY);
            if (!signature.equals("-1")){
                pieces.get(pieceId).setCoords(newX, newY);
                return signature;
            }
            else {
                return signature;
            }
        }
    }
    private int coordsToId(int x, int y) {
        int pieceId = -1;
        for (int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            if(piece.getPosX() == x && piece.getPosY() == y){
                pieceId = i;
                break;
            }
        }
        return pieceId;
    }

    private boolean checkForPiece(int x, int y){
        for (Piece piece : pieces){
            if(piece.getPosX() == x && piece.getPosY() == y){
                return true;
            }
        }
        return false;
    }

    private String checkManMove(Color color ,int initX, int initY, int newX, int newY){
        if (checkForPiece(newX, newY)){
            return "-1";
        }
        if(color == Color.WHITE){
            if((initY - 1) == newY && ((initX + 1) == newX || (initX - 1) == newX)){
                if(newY == 0){pieces.get(coordsToId(initX, initY)).promote();}
                return "0";
            } else if ((initY - 2) == newY && ((initX + 2) == newX || (initX - 2) == newX) && checkForPiece((newX + initX)/2, (newY + initY)/2)) {
                if(newY == 0){pieces.get(coordsToId(initX, initY)).promote();}
                killPiece(coordsToId((newX + initX)/2, (newY + initY)/2));
                return "1";
            }else {
                return "-1";
            }
        }
        else {
            if((initY + 1) == newY && ((initX + 1) == newX || (initX - 1) == newX)){
                if(newY == maxCoord){pieces.get(coordsToId(initX, initY)).promote();}
                return "0";
            } else if ((initY + 2) == newY && ((initX + 2) == newX || (initX - 2) == newX) && checkForPiece((newX + initX)/2, (newY + initY)/2)) {
                if(newY == maxCoord){pieces.get(coordsToId(initX, initY)).promote();}
                killPiece(coordsToId((newX + initX)/2, (newY + initY)/2));
                return "1";
            }else {
                return "-1";
            }
        }
    }

    private String checkKingMove(Color color, int initX, int initY, int newX, int newY){
        int possibleTrajectory1 = initY - initX;// y = x + trajectory1
        int possibleTrajectory2 = initY + initX;// y = -x + trajectory2
        if (checkForPiece(newX, newY)){
            return "-1";
        }
        if (newY == newX + possibleTrajectory1 || newY == -newX + possibleTrajectory2){
            ArrayList<Piece> leapedPieces = new ArrayList<>();
            int xDirection = Integer.compare((newX - initX), 0);
            int yDirection = Integer.compare((newY - initY), 0);
            int trackerX = initX;
            int trackerY = initY;
            for (int i = 1; i < Math.abs(newX - initX); i++) {
                trackerX += xDirection*i;
                trackerY += yDirection*i;
                if(checkForPiece(trackerX, trackerY)){
                    leapedPieces.add(pieces.get(coordsToId(trackerX, trackerY)));
                }
            }
            if(leapedPieces.size() == 0){
                return "0";
            }
            else if (leapedPieces.size() > 1) {
                return "-1";
            }
            else if (leapedPieces.get(0).getColor() == color) {
                return "-1";
            }
            else {
                killPiece(coordsToId(leapedPieces.get(0).getPosX(), leapedPieces.get(0).getPosY()));
                return "1";
            }
        }
        else {
            return "-1";
        }
    }

    private void killPiece(int id){
        pieces.remove(id);
    }
}
