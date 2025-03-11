package Model;

import Model.Space;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class SpaceTest {

    private Space space;

    @Before
    public void setUp() {
        // Initialize before each test
    }

    @Test
    public void testSpaceConstructorWithValidName() {
        space = new Space.PropertySpace("Boardwalk", 39, "Blue", 400, 50,
                100, 200, 600, 1400, 1700,
                2000, 200, 200);
        assertEquals("Boardwalk", space.getName());
        space = new Space.UtilitySpace("Electric Company", 12, 150, 75);
        assertEquals("Electric Company", space.getName());
    }

    @Test
    public void testSpaceConstructorWithEmptyString() {
        space = new Space.GoSpace();
        assertEquals("Go", space.getName());

    }

   @Test
    public void testSpaceConstructorWithNullName() {
       space = new Space.GoSpace();
       assertNotNull(space.getName());
    }

}