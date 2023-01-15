import org.checkers.ui.Checker;
import org.checkers.ui.Kwadrat;
import org.junit.Test;
import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

/**
 * The type Ui test.
 */
public class UiTest {
    /**
     * Kwadrat test.
     */
    @Test
    public void KwadratTest(){
        Kwadrat kwadrat = new Kwadrat(1, 1, 10);
    }

    /**
     * Checker test.
     */
    @Test
    public void CheckerTest(){
        Checker checker = new Checker(1, 1, 10, 1, 1);
        assertEquals(checker.getRow(), 1);
        assertEquals(checker.getColumn(), 1);
        checker.Move(2, 2);
        assertEquals(checker.getRow(), 2);
        assertEquals(checker.getColumn(), 2);
    }
}
