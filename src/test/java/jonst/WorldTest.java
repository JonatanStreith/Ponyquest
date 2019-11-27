package jonst;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WorldTest {


    @Test
    public void ConstructorTest() {

        World earth = new World("src/main/java/jonst/Assets/DefaultWorld");


assertNotNull(earth);

    }
}
