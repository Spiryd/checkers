import org.checkers.model.Color;
import org.checkers.model.Piece;
import org.junit.Test;

public class ModelTest {
    @Test
    public void test(){
        Piece piece1 = new Piece(2, 1, Color.RED);
        Piece piece2 = new Piece(3, 7, Color.WHITE);
        System.out.println(piece1.getPosX());
        System.out.println(piece1.getPosY());
        piece2.promote();
        System.out.println(piece2.getState());
        System.out.println(piece2.getColor());
        piece2.setCoords(9, 6);
    }
}
