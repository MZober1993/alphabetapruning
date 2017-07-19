package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModelTest {

    private Branch seminarExample;
    public static final Node LEFT_NODE = new Node(0.0, 3.0);

    @Before
    public void setUp() {
        seminarExample = new Branch(
                new Branch(LEFT_NODE, new Node(5.0, -8.0))
                , new Branch(new Node(-5.0, 0.0), new Node(1.0, -2.0)));
        seminarExample.setPruneMode(PruneMode.ALPHA);
    }

    @Test
    public void constructionOfTreeIsValid() {
        Node leftNode = seminarExample.getLeftNode();
        assertEquals(LEFT_NODE, leftNode);
        assertEquals(PruneMode.ALPHA, leftNode.getPruneMode());
        assert 3 == seminarExample.calcDepth();
    }

    @Test
    public void pruneWorks() {
        seminarExample.prune();
        Double alphaA = seminarExample.getPruneValue();
        GenericTree b = seminarExample.getA();
        GenericTree c = seminarExample.getB();
        Node e = (Node) b.getB();
        Node g = (Node) c.getB();

        Double alphaB = b.getPruneValue();
        Double alphaC = c.getPruneValue();

        Double alphaD = ((Node ) b.getA()).getPruneValue();
        Double alphaE = e.getPruneValue();
        Double alphaF = ((Node ) c.getA()).getPruneValue();
        Double alphaG = g.getPruneValue();

        assert 3.0 == alphaA;
        assert 3.0 == alphaB;
        assert 0.0 == alphaC;

        assert 3.0 == alphaD;
        assert 5.0 == alphaE;
        assert 0.0 == alphaF;
        assert Double.MIN_VALUE == alphaG;

        Assert.assertTrue(e.isCut());
        Assert.assertTrue(g.isCut());
    }
}
