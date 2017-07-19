package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthias on 17.07.2017.
 */
public class Node extends GenericTree<Double,Double> {

    public Node(Double doubleA, Double doubleB) {
        super(doubleA, doubleB);
    }

    @Override
    public List<Double> findAllValues() {
        List<Double> allValues = new ArrayList<>();
        allValues.add(getA());
        allValues.add(getB());

        return allValues;
    }

    @Override
    public void prune(){
        pruneMode.nodePrune(this);
    }

    @Override
    public void quickPrune() {
        pruneMode.nodeQuickPrune(this);
    }

    @Override
    public void setPruneMode(PruneMode pruneMode) {
        this.pruneMode = pruneMode;
    }
}
