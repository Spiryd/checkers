package org.checkers.model;

import java.util.ArrayList;

/**
 * Controls the model.
 */
public class Board {
    private final ArrayList<Piece> pieces;
    private final int maxCoord;

    /**
     * Instantiates a new Board.
     *
     * @param edgeLength the edge length of th board
     */
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

    /**
     * Moves the piece and returns the move signature
     *
     * @param initX the initial x position of the piece to be moved
     * @param initY the initial y position of the piece to be moved
     * @param newX  the x position for the piece to be moved to
     * @param newY  the y position for the piece to be moved to
     * @return either "-1"-illegal move, "0"-normal move, "1"-kill
     */
    public String movePiece(int initX, int initY, int newX, int newY){
        int pieceId = coordsToId(initX, initY);
        String signature;
        //identifies the piece
        if (pieceId == -1){
            debugPrint();
            System.out.println("no piece or is it");
            return "-1";
        }
        Piece movingPiece = pieces.get(pieceId);
        //checks for Man moves
        if (movingPiece.getState() == Rank.MAN){
            signature = checkManMove(movingPiece.getColor(), initX, initY, newX, newY);
        }else { //checks for King moves
            signature = checkKingMove(movingPiece.getColor(), initX, initY, newX, newY);
        }
        if (!signature.equals("-1")){
            pieceId = coordsToId(initX, initY);
            pieces.get(pieceId).setCoords(newX, newY);
        }
        debugPrint();
        String winner = checkForWin();
        if(!winner.equals("0")){
            return winner;
        }
        return signature;
    }

    /**
     * Returns the index of a piece given its coords
     *
     * @param x position on x axis
     * @param y position on y axis
     * @return index of the piece if not found return -1
     */
    private int coordsToId(int x, int y) {
        int pieceId = -1;
        for (int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            if(piece.getPosX() == x && piece.getPosY() == y){
                pieceId = i;
                break;
            }
        }
        System.out.println(pieceId);
        return pieceId;
    }

    /**
     * Checks if a piece exists
     *
     * @param x position on x axis
     * @param y position on y axis
     * @return ture if piece exists false otherwise
     */
    private boolean checkForPiece(int x, int y){
        for (Piece piece : this.pieces){
            if(piece.getPosX() == x && piece.getPosY() == y){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if man move is legal the move signature
     *
     * @param color color of the piece to be checked
     * @param initX initial x position of the piece
     * @param initY initial y position of the piece
     * @param newX x position for the piece to be moved to
     * @param newY x position for the piece to be moved to
     * @return either "-1"-illegal move, "0"-normal move, "1"-kill
     */
    private String checkManMove(Color color ,int initX, int initY, int newX, int newY){
        //checks move if in bounds
        if (checkIfInBounds(newX, newY)) {
            debugPrint();
            return "-1";
        }
        if (checkForPiece(newX, newY)){
            System.out.println("piece already here");
            return "-1";
        }
        if(color == Color.WHITE){
            if((initY - 1) == newY && ((initX + 1) == newX || (initX - 1) == newX)){
                if(newY == 0){
                    pieces.get(coordsToId(initX, initY)).promote();
                }
                return "0";
            } else if ((initY - 2) == newY && ((initX + 2) == newX || (initX - 2) == newX) && checkForPiece((newX + initX)/2, (newY + initY)/2)) {
                if(newY == 0){
                    pieces.get(coordsToId(initX, initY)).promote();
                }
                killPiece(coordsToId((newX + initX)/2, (newY + initY)/2));
                //System.out.println("killing");
                return "1";
            }else {
                //System.out.println("something else");
                return "-1";
            }
        }
        else {
            if((initY + 1) == newY && ((initX + 1) == newX || (initX - 1) == newX)){
                if(newY == maxCoord){
                    pieces.get(coordsToId(initX, initY)).promote();
                }
                return "0";
            } else if ((initY + 2) == newY && ((initX + 2) == newX || (initX - 2) == newX) && checkForPiece((newX + initX)/2, (newY + initY)/2)) {
                if(newY == maxCoord){
                    pieces.get(coordsToId(initX, initY)).promote();
                }
                killPiece(coordsToId((newX + initX)/2, (newY + initY)/2));
                //System.out.println("killing");
                return "1";
            }else {
                //System.out.println("something else");
                return "-1";
            }
        }
    }

    /**
     * Checks if King move is legal the move signature
     *
     * @param color color of the piece to be checked
     * @param initX initial x position of the piece
     * @param initY initial y position of the piece
     * @param newX x position for the piece to be moved to
     * @param newY x position for the piece to be moved to
     * @return either "-1"-illegal move, "0"-normal move, "1"-kill
     */
    private String checkKingMove(Color color, int initX, int initY, int newX, int newY){
        //checks move if in bounds
        if (checkIfInBounds(newX, newY)) {
            debugPrint();
            return "-1";
        }
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

    /**
     * Kills the given piece
     *
     * @param id index of the piece to be killed
     */
    private void killPiece(int id) {
        System.out.print("killing ");
        System.out.println(id);
        pieces.remove(id);
    }

    /**
     * Prints board for debugging
     */
    private void debugPrint(){
        ArrayList<Integer> row = new ArrayList<>();
        StringBuilder printBoard = new StringBuilder();
        for (int i = 0; i <= maxCoord; i++) {
            for(Piece piece : this.pieces){
                if(piece.getPosY() == i){
                    row.add(piece.getPosX());
                }
            }
            for (int j = 0; j <= maxCoord; j++){
               if(row.contains(j)){
                   printBoard.append("x");
               }
               else {
                   printBoard.append("0");
               }
            }
            row.clear();
            printBoard.append("\n");
        }
        System.out.println(printBoard);
    }

    /**
     * Checks for the winning condition
     *
     * @return either "0" - no one wins, "w1" - white wins, "w2" - red wins
     */
    private String checkForWin(){
        int whiteCounter = 0;
        for (Piece piece: this.pieces){
            if(piece.getColor() == Color.WHITE){
                whiteCounter++;
            }
        }
        if(whiteCounter == 0){
            return "w1";
        }
        else if ( pieces.size() == whiteCounter ){
            return "w2";
        }
        ArrayList<String> possibleWhiteMoves = new ArrayList<>();
        ArrayList<String> possibleRedMoves = new ArrayList<>();
        for (Piece piece: this.pieces){
            int x = piece.getPosX();
            int y = piece.getPosY();
            if(piece.getColor() == Color.WHITE){
                if(piece.getState() == Rank.MAN){
                    possibleWhiteMoves.add(checkManMove(Color.WHITE, x, y, x+1, y-1));
                    possibleWhiteMoves.add(checkManMove(Color.WHITE, x, y, x-1, y-1));
                }else {
                    for (int i = 1; i <= maxCoord; i++) {
                        if(!(checkIfInBounds(x+i, y+i) || checkIfInBounds(x+i, y-i) || checkIfInBounds(x-i, y+i) || checkIfInBounds(x-i, y-i))){
                            break;
                        }
                        if (checkIfInBounds(x+i, y+i)){
                            possibleWhiteMoves.add(checkKingMove(Color.WHITE, x, y, x+i, y+i));
                        }
                        if (checkIfInBounds(x+i, y-i)){
                            possibleWhiteMoves.add(checkKingMove(Color.WHITE, x, y, x+i, y-i));
                        }
                        if (checkIfInBounds(x-i, y+i)){
                            possibleWhiteMoves.add(checkKingMove(Color.WHITE, x, y, x-i, y+i));
                        }
                        if (checkIfInBounds(x-i, y-i)){
                            possibleWhiteMoves.add(checkKingMove(Color.WHITE, x, y, x-i, y-i));
                        }
                    }
                }
            }
            else {
                if(piece.getState() == Rank.MAN){
                    possibleRedMoves.add(checkManMove(Color.RED, x, y, x+1, y+1));
                    possibleRedMoves.add(checkManMove(Color.RED, x, y, x-1, y+1));
                }else {
                    for (int i = 1; i <= maxCoord; i++) {
                        if(!(checkIfInBounds(x+i, y+i) || checkIfInBounds(x+i, y-i) || checkIfInBounds(x-i, y+i) || checkIfInBounds(x-i, y-i))){
                            break;
                        }
                        if (checkIfInBounds(x+i, y+i)){
                            possibleRedMoves.add(checkKingMove(Color.RED, x, y, x+i, y+i));
                        }
                        if (checkIfInBounds(x+i, y-i)){
                            possibleRedMoves.add(checkKingMove(Color.RED, x, y, x+i, y-i));
                        }
                        if (checkIfInBounds(x-i, y+i)){
                            possibleRedMoves.add(checkKingMove(Color.RED, x, y, x-i, y+i));
                        }
                        if (checkIfInBounds(x-i, y-i)){
                            possibleRedMoves.add(checkKingMove(Color.RED, x, y, x-i, y-i));
                        }
                    }
                }
            }
        }
        if(!(possibleWhiteMoves.contains("0")||possibleWhiteMoves.contains("1"))){
            return "w2";
        } else if (!(possibleRedMoves.contains("0")||possibleRedMoves.contains("1"))) {
            return "w1";
        }
        return  "0";
    }
    private boolean checkIfInBounds(int x, int y){
        return (((x < 0 || x > maxCoord) || (y < 0 || y > maxCoord)));
    }
}
