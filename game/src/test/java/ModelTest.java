import org.checkers.model.*;
import org.junit.Test;

/**
 * The type Model test.
 */
public class ModelTest {
    /**
     * Piece test.
     */
    @Test
    public void pieceTest(){
        Piece piece1 = new Piece(2, 1, Color.RED);
        Piece piece2 = new Piece(3, 7, Color.WHITE);
        System.out.println(piece1.getPosX());
        System.out.println(piece1.getPosY());
        piece2.promote();
        System.out.println(piece2.getState());
        System.out.println(piece2.getColor());
        piece2.setCoords(9, 6);
    }

    /**
     * Board test.
     */
    @Test
    public void BoardTest() {
        Board board = new Board(8);
        board.movePiece(2, 5, 1, 4);
    }

    /**
     * Board test 2.
     */
    @Test
    public void BoardTest2() {
        Board board = new Board(8);
        board.movePiece(5, 1, 4, 1);
        board.movePiece(3, 2, 2, 3);
        board.movePiece(1, 4,3,2);
        board.movePiece(6,5,7,4);
        board.movePiece(7,2,6,3);
        board.movePiece(4,5,5,4);
        board.movePiece(4,1,2,3);
        board.movePiece(2, 3, 1, 4);
        board.movePiece(5, 4, 4, 3);
        board.movePiece(6, 1, 7, 2);
        board.movePiece(4, 3, 3, 2);
        board.movePiece(5, 0, 6, 1);
        board.movePiece(3, 2, 4, 1);
        board.movePiece(5, 2, 4, 3);
        board.movePiece(4, 1, 5, 0);
        board.movePiece(6, 3, 5, 4);
        board.movePiece(5, 0, 2, 3);
        board.movePiece(4, 3, 3, 4);
        board.movePiece(2, 3, 0, 5);
    }



}
