package distance;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DistanceCalculatorTest {

    @Test
    public void testDistance0(){
        Point p1 = new Point(0, 0);
        Point p2 = new Point(-1, 0);
        Assert.assertEquals(p1.distance(p2), 1.0, "Wrong distance");
    }

    @Test
    public void testDistance1(){
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        Assert.assertEquals(p1.distance(p2), 2.0, "Wrong distance");
    }

    @Test
    public void testDistance2(){
        Assert.assertEquals(new Point(0, 0).distance(new Point(0, 0)), 0.0, "Wrong distance");
    }
}
