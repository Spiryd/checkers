import org.checkers.util.Translator;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;


/**
 * The type Util test.
 */
public class utilTest {
    /**
     * Translator test.
     */
    @Test
    public void translatorTest(){
        Translator translator = new Translator();
        String message = "1;2;3;4;";
        int[] expectedArray = {1,2,3,4};
        assertArrayEquals(expectedArray, translator.translateCoords(message));
    }
}
