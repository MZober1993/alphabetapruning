package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthias on 17.07.2017.
 */
public class Branch extends GenericTree<GenericTree, GenericTree> {

    public Branch(GenericTree treeA, GenericTree treeB) {
        super(treeA, treeB);
    }

    @Override
    public void prune() {
        pruneMode.branchPrune(this);
    }

    @Override
    public void quickPrune() {
        pruneMode.branchQuickPrune(this);
    }

    @Override
    public void setPruneMode(PruneMode pruneMode) {
        this.pruneMode = pruneMode;
        if (pruneMode.equals(PruneMode.ALPHA)) {
            this.getA().setPruneMode(PruneMode.BETA);
            this.getB().setPruneMode(PruneMode.BETA);
        } else if (pruneMode.equals(PruneMode.BETA)) {
            this.getA().setPruneMode(PruneMode.ALPHA);
            this.getB().setPruneMode(PruneMode.ALPHA);
        }
    }

    @Override
    public List<Double> findAllValues() {
        List<Double> allValues = new ArrayList<>();
        allValues.addAll(getA().findAllValues());
        allValues.addAll(getB().findAllValues());

        return allValues;
    }
}
