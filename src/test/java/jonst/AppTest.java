package jonst;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.HashMap;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        HashMap<String, String> test = new HashMap<>();

        test.put("London", "England");

        System.out.println(test.get("London"));
        System.out.println(test.get("Paris"));

        //assertTrue( true );
    }



}
