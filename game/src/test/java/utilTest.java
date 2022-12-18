import org.checkers.util.Translator;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;


public class utilTest {
    @Test
    public void translatorTest(){
        Translator translator = new Translator();
        String message = "1;2;3;4;";
        int[] expectedArray = {1,2,3,4};
        assertArrayEquals(expectedArray, translator.translateCoords(message));
    }
}
